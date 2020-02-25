package com.morty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.morty.entity.ManagerEntity;

import java.util.List;

public interface ManagerService extends IService<ManagerEntity> {

    /**
     * 查询
     * @param managerEntity
     * @return 查询实体集合
     */
    List<ManagerEntity> queryByEntity(ManagerEntity managerEntity);

    ManagerEntity getEntity(ManagerEntity managerEntity);

    /**
     * 保存用户
     * @param managerEntity
     */
    void saveManager(ManagerEntity managerEntity);

    void updateManager(ManagerEntity managerEntity);

    /**
     * 查询用户的所有权限
     * @param managerId  用户ID
     */
    List<String> queryAllPerms(int managerId);

    /**
     * 批量删除
     * @param ids
     */
    void deleteIds(int[] ids);
}
