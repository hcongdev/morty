package com.morty.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("role")
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
    @TableId
    private int roleId;

    /**
     * 角色编号
     */
    private String roleName;

    /**
     * 菜单权限列表
     */
    @TableField(exist=false)
    private List<Integer> menuIdList;

    /**
     * 管理员角色编号
     */
    private String roleManagerId;

}
