package com.morty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.morty.entity.RoleEntity;

import java.util.List;

public interface RoleService extends IService<RoleEntity> {
    /**
     * 查询
     * @param roleEntity
     * @return
     */
    List<RoleEntity> queryByEntity(RoleEntity roleEntity);

    void saveRole(RoleEntity role);
}
