package com.morty.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("role_menu")
public class RoleMenuEntity implements Serializable {

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
    private int menuId;

}
