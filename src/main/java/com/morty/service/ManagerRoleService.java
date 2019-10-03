package com.morty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.morty.entity.ManagerRoleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerRoleService extends IService<ManagerRoleEntity> {

    void saveOrUpdate(int managerId, List<Integer> roleIdList);

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Integer> queryRoleIdList(int managerId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(int[] managerIds);
}
