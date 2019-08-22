package com.morty.service.impl;

import com.morty.entity.ManagerEntity;
import com.morty.mapper.ManagerMapper;
import com.morty.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerImpl implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public boolean save(ManagerEntity managerEntity) {
        return false;
    }

    @Override
    public List<ManagerEntity> list(ManagerEntity managerEntity) {
        return managerMapper.list(managerEntity);
    }

    @Override
    public boolean del(int id) {
        return false;
    }

    @Override
    public boolean update(ManagerEntity managerEntity) {
        return false;
    }

    @Override
    public ManagerEntity getEntity(ManagerEntity managerEntity) {
        return managerMapper.getEntity(managerEntity);
    }
}
