package com.morty.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("menu")
public class MenuEntity {
    private static final long serialVersionUID = 1L;

    @TableId
    private int menuId;

    private Long menuParentId;

    private String menuName;

    private String menuUrl;

    private String menuPerms;

    private int menuType;

    private String menuIcon;

    private int menuOrder;
}
