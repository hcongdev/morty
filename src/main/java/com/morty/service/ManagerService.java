package com.morty.service;

import com.morty.entity.ManagerEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerService {
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

    /**
     * 删除
     * @param id
     * @return
     */
    boolean del(int id);

    /**
     * 修改
     * @param managerEntity
     * @return
     */
    boolean update(ManagerEntity managerEntity);

    ManagerEntity getEntity(ManagerEntity managerEntity);
}
