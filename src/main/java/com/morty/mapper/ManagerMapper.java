package com.morty.mapper;

import com.morty.entity.ManagerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("managerMapper")
public interface ManagerMapper {

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
}
