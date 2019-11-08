package com.morty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.morty.entity.RoleMenuEntity;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenuEntity> {


    int deleteBatch(int[] roleIds);

    List<Integer> queryMenuIdList(int roleId);
}
