package com.atguigu.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import java.util.Map;

/**
 * 属性分组
 *
 * @author mtmb
 * @email mtmb@gmail.com
 * @date 2023-03-23 01:47:06
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {


    PageUtils queryPage(Map<String, Object> params);
}

