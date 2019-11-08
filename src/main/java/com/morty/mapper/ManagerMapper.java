package com.morty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.morty.entity.ManagerEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface ManagerMapper extends BaseMapper<ManagerEntity> {

    List<String> queryAllPerms(int managerId);

    /**
     * 查询
     * @param managerEntity
     * @return 查询实体集合
     */
    List<ManagerEntity> list(ManagerEntity managerEntity);

    /**
     * 获取实体
     * @param managerEntity
     * @return
     */
    ManagerEntity getEntity(ManagerEntity managerEntity);

    List<ManagerEntity> queryByEntity(ManagerEntity managerEntity);

    void save(ManagerEntity managerEntity);
}
