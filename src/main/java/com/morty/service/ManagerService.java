package com.morty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.morty.entity.ManagerEntity;
import com.morty.entity.MenuEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerService extends IService<ManagerEntity> {

    /**
     * 查询
     * @param managerEntity
     * @return 查询实体集合
     */
    List<ManagerEntity> queryByEntity(ManagerEntity managerEntity);

    ManagerEntity getEntity(ManagerEntity managerEntity);
}
