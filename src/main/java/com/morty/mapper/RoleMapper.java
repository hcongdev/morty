package com.morty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.morty.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<RoleEntity> {

    List<RoleEntity> queryByEntity(RoleEntity roleEntity);

    void save(RoleEntity role);

    RoleEntity getEntity(RoleEntity role);
}
