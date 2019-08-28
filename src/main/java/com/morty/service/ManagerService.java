package com.morty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.morty.entity.ManagerEntity;
import com.morty.entity.MenuEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerService extends IService<ManagerEntity> {
    /**
     * 保存
     * @param managerEntity
     * @return 保存后的id
     */
    boolean save(ManagerEntity managerEntity);


    /**
     * 查询
     * @param managerEntity
     * @return 查询实体集合
     */
    List<ManagerEntity> list(ManagerEntity managerEntity);

    ManagerEntity getEntity(ManagerEntity managerEntity);
}
