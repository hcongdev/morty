package com.morty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.morty.entity.ManagerRoleEntity;
import com.morty.entity.RoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManagerRoleMapper extends BaseMapper<ManagerRoleEntity> {


    int deleteBatch(int[] roleIds);

    List<Integer> queryRoleIdList(int managerId);
}
