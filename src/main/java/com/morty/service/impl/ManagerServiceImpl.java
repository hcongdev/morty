package com.morty.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.morty.entity.ManagerEntity;
import com.morty.mapper.ManagerMapper;
import com.morty.service.ManagerRoleService;
import com.morty.service.ManagerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("managerService")
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper,ManagerEntity> implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private ManagerRoleService managerRoleService;

    @Override
    public List<ManagerEntity> queryByEntity(ManagerEntity managerEntity) {
        return baseMapper.queryByEntity(managerEntity);
    }

    @Override
    public ManagerEntity getEntity(ManagerEntity managerEntity) {
        return managerMapper.getEntity(managerEntity);
    }

    @Override
    public void saveManager(ManagerEntity managerEntity) {
        managerEntity.setManagerPassword(SecureUtil.md5(managerEntity.getManagerPassword()));
        managerEntity.setManagerTime(new Date());
        managerMapper.save(managerEntity);

        //保存用户与角色的关系
        managerRoleService.saveOrUpdate(managerEntity.getManagerId(),managerEntity.getRoleIdList());
    }

    @Override
    public void updateManager(ManagerEntity managerEntity) {
        if(StringUtils.isBlank(managerEntity.getManagerPassword())){
            managerEntity.setManagerPassword(null);
        }else{
            managerEntity.setManagerPassword(SecureUtil.md5(managerEntity.getManagerPassword()));
        }
        this.updateById(managerEntity);

        //保存用户与角色的关系
        managerRoleService.saveOrUpdate(managerEntity.getManagerId(),managerEntity.getRoleIdList());
    }
}
