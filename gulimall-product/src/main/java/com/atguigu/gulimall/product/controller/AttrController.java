package com.atguigu.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.atguigu.gulimall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.service.AttrService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;

import javax.annotation.Resource;


/**
 * 商品属性
 *
 * @author mtmb
 * @email mtmb@gmail.com
 * @date 2023-03-23 01:47:06
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Resource
    AttrService attrService;


    @GetMapping("/base/list/{catelogId}")
    public R baseAttrList(@RequestParam Map<String, Object> params,
                          @PathVariable("catelogId") Long catelogId){
        PageUtils page = attrService.queryBaseAttrPage(params,catelogId);
        return R.ok().put("page",page);
    }
    /**
     * 列表
     */
//    @RequestMapping("/list")
//    //RequiresPermissions("product:attr:list")
//    public R list(@RequestParam Map<String, Object> params) {
//        PageUtils page = attrService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId) {
        AttrEntity attr = attrService.getById(attrId);

        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attr) {
        attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrEntity attr) {
        attrService.updateById(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
