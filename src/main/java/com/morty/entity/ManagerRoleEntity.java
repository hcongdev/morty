package com.morty.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("manager_role")
public class ManagerRoleEntity {
    private static final long serialVersionUID = 1L;

    @TableId
    private int id;

    /**
     * 角色ID
     */
    private int roleId;

    /**
     * 菜单ID
     */
    private int managerId;
}
