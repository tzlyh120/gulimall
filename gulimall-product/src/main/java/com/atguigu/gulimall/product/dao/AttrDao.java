package com.atguigu.gulimall.product.dao;

import com.atguigu.gulimall.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品属性
 * 
 * @author mtmb
 * @email mtmb@gmail.com
 * @date 2023-03-23 01:47:06
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {
	
}
