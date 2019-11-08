package com.morty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.morty.entity.ManagerRoleEntity;
import com.morty.entity.RoleMenuEntity;

import java.util.List;

public interface ManagerRoleMapper extends BaseMapper<ManagerRoleEntity> {


    int deleteBatch(int[] roleIds);

    List<Integer> queryRoleIdList(int managerId);
}
