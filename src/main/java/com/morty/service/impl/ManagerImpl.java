package com.morty.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.morty.entity.ManagerEntity;
import com.morty.mapper.ManagerMapper;
import com.morty.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("managerService")
public class ManagerImpl extends ServiceImpl<ManagerMapper,ManagerEntity> implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper;


    @Override
    public List<ManagerEntity> queryByEntity(ManagerEntity managerEntity) {
        return baseMapper.queryByEntity(managerEntity);
    }

    @Override
    public ManagerEntity getEntity(ManagerEntity managerEntity) {
        return managerMapper.getEntity(managerEntity);
    }
}
