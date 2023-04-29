package com.atguigu.gulimall.ware.service.impl;

import com.atguigu.common.constant.WareConstant;
import com.atguigu.gulimall.ware.entity.PurchaseDetailEntity;
import com.atguigu.gulimall.ware.service.PurchaseDetailService;
import com.atguigu.gulimall.ware.service.WareSkuService;
import com.atguigu.gulimall.ware.vo.MergeVo;
import com.atguigu.gulimall.ware.vo.PurchaseDoneItemVo;
import com.atguigu.gulimall.ware.vo.PurchaseDoneVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.ware.dao.PurchaseDao;
import com.atguigu.gulimall.ware.entity.PurchaseEntity;
import com.atguigu.gulimall.ware.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Resource
    PurchaseDetailService purchaseDetailService;

    @Resource
    WareSkuService wareSkuService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageUnreceivePurchase(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>().eq("status",0).or().eq("status",1)
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void mergePurchase(MergeVo mergeVo) {
        List<Long> mergeVoItems = mergeVo.getItems();
        mergeVoItems.forEach((item)->{
            PurchaseDetailEntity entity = purchaseDetailService.getById(item);
            if(entity.getStatus()==WareConstant.PurchaseDetailStatusEnum.CREATE.getCode() || entity.getStatus()==WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode()){
                Long purchasedId = mergeVo.getPurchasedId();
                if(purchasedId==null){
                    PurchaseEntity purchaseEntity = new PurchaseEntity();
                    purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.CREATE.getCode());
                    purchaseEntity.setCreateTime(new Date());
                    purchaseEntity.setUpdateTime(new Date());
                    this.save(purchaseEntity);
                    purchasedId = purchaseEntity.getId();
                }
                List<Long> items = mergeVo.getItems();
                Long finalPurchasedId = purchasedId;
                List<PurchaseDetailEntity> collect = items.stream().map((item1) -> {
                    PurchaseDetailEntity detailEntity = new PurchaseDetailEntity();
                    detailEntity.setId(item1);
                    detailEntity.setPurchaseId(finalPurchasedId);
                    detailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
                    return detailEntity;
                }).collect(Collectors.toList());

                purchaseDetailService.updateBatchById(collect);

                PurchaseEntity purchaseEntity = new PurchaseEntity();
                purchaseEntity.setUpdateTime(new Date());
                purchaseEntity.setId(purchasedId);
                this.updateById(purchaseEntity);
            }
        });

    }

    @Override
    public void received(List<Long> ids) {
        List<PurchaseEntity> collect = ids.stream().map((id) -> {
            PurchaseEntity purchaseEntity = this.getById(id);
            return purchaseEntity;
        }).filter((item)->{
            return item.getStatus()==WareConstant.PurchaseStatusEnum.CREATE.getCode() ||
                    item.getStatus()==WareConstant.PurchaseStatusEnum.ASSIGNED.getCode();
        }).map((item)->{
            item.setStatus(WareConstant.PurchaseStatusEnum.RECEIVE.getCode());
            item.setUpdateTime(new Date());
            return item;
        }).collect(Collectors.toList());

        this.updateBatchById(collect);

        collect.forEach((item)->{
            List<PurchaseDetailEntity> entities= purchaseDetailService.listDetailByPurchaseId(item.getId());
            List<PurchaseDetailEntity> detailEntities = entities.stream().map((entity) -> {
                PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
                purchaseDetailEntity.setId(entity.getId());
                purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.BUYING.getCode());
                return purchaseDetailEntity;
            }).collect(Collectors.toList());
            purchaseDetailService.updateBatchById(detailEntities);
        });
    }

    @Transactional
    @Override
    public void done(PurchaseDoneVo purchaseDoneVo) {
        Long id = purchaseDoneVo.getId();
        Boolean flag = true;
        List<PurchaseDoneItemVo> items = purchaseDoneVo.getItems();
        List<PurchaseDetailEntity> updates = new ArrayList<>();
        for (PurchaseDoneItemVo item : items) {
            PurchaseDetailEntity detailEntity = new PurchaseDetailEntity();
            if(item.getStatus()==WareConstant.PurchaseDetailStatusEnum.FAILED.getCode()){
                flag=false;
                detailEntity.setStatus(item.getStatus());
            }else{
                detailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.FINISH.getCode());
                PurchaseDetailEntity entity = purchaseDetailService.getById(item.getItemId());
                wareSkuService.addStock(entity.getSkuId(),entity.getWareId(),entity.getSkuNum());
            }
            detailEntity.setId(item.getItemId());
            updates.add(detailEntity);
        }
        purchaseDetailService.updateBatchById(updates);

        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(id);
//        if(flag) {
//            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.FINISH.getCode());
//        }else{
//            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.HASERROR.getCode());
//        }
        purchaseEntity.setStatus(flag?WareConstant.PurchaseStatusEnum.FINISH.getCode():WareConstant.PurchaseStatusEnum.HASERROR.getCode());
        this.updateById(purchaseEntity);
    }

}