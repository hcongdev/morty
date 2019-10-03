package com.morty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.morty.entity.ManagerRoleEntity;
import com.morty.mapper.ManagerRoleMapper;
import com.morty.service.ManagerRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("managerRoleService")
public class ManagerRoleServiceImpl extends ServiceImpl<ManagerRoleMapper, ManagerRoleEntity> implements ManagerRoleService {

    @Override
    public void saveOrUpdate(int managerId, List<Integer> roleIdList) {
        //先删除用户与角色关系
        this.remove(new QueryWrapper<ManagerRoleEntity>().eq("manager_id", managerId));

        if(roleIdList == null || roleIdList.size() == 0){
            return ;
        }

        //保存用户与角色关系
        for(int roleId : roleIdList){
            ManagerRoleEntity managerRoleEntity = new ManagerRoleEntity();
            managerRoleEntity.setManagerId(managerId);
            managerRoleEntity.setRoleId(roleId);

            this.save(managerRoleEntity);
        }
    }

    @Override
    public List<Integer> queryRoleIdList(int managerId) {
        return baseMapper.queryRoleIdList(managerId);
    }

    @Override
    public int deleteBatch(int[] roleIds){
        return baseMapper.deleteBatch(roleIds);
    }
}
