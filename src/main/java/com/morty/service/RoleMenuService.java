package com.morty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.morty.entity.RoleMenuEntity;

import java.util.List;

public interface RoleMenuService extends IService<RoleMenuEntity> {

    void saveOrUpdate(int roleId, List<Integer> menuIdList);

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Integer> queryMenuIdList(int roleId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(int[] roleIds);
}
