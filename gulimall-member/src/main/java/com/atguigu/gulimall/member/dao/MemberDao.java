package com.atguigu.gulimall.member.dao;

import com.atguigu.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author mtmb
 * @email mtmb@gmail.com
 * @date 2023-03-24 22:50:14
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
