package com.atguigu.gulimall.search.controller;

import com.atguigu.gulimall.search.exception.BizCodeEnume;
import com.atguigu.gulimall.search.service.ProductSaveService;
import com.atguigu.gulimall.search.to.es.SkuEsModel;
import com.atguigu.gulimall.search.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yihan
 * @date 4/21/2023 - 1:01 AM
 */
@Slf4j
@RestController
@RequestMapping("/search/save")
public class ElasticSaveController {

    @Resource
    ProductSaveService productSaveService;
    @PostMapping("/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels){
        boolean b =false;
        try{
           b = productSaveService.productStatusUp(skuEsModels);
        }catch (Exception e){
            log.error("elasticsave 商品上架错误",e );
            return R.error(BizCodeEnume.PRODUCT_UP_EXCEPTION.getCode(),BizCodeEnume.PRODUCT_UP_EXCEPTION.getMsg());
        }
        if(!b){
            return R.ok();
        }else{
            return R.error(BizCodeEnume.PRODUCT_UP_EXCEPTION.getCode(),BizCodeEnume.PRODUCT_UP_EXCEPTION.getMsg());
        }
    }
}
