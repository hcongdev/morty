<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>菜单</title>
    <#include "/comment/comment_header.ftl">
</head>
<body>
    <div id="menu">
        <el-container>
            <el-header class="button-header">
                <el-row>
                    <el-button icon="el-icon-search">查询</el-button>
                    <el-button type="info" icon="el-icon-plus" @click="save">新增</el-button>
                    <el-button type="info" class="el-icon-edit" @click="update">修改</el-button>
                    <el-button type="danger" class="el-icon-delete" @click="del">删除</el-button>
                </el-row>

            </el-header>
            <el-table @select="handleselect" :data="menuList" style="width: 100%" border>
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="menuName" label="菜单名称" align="center" width="180"> </el-table-column>
                <el-table-column prop="menuIcon" label="菜单图标" width="180"> </el-table-column>
                <el-table-column prop="menuType" label="菜单类型" width="180"> </el-table-column>
                <el-table-column prop="menuPerms" label="菜单权限" align="center"> </el-table-column>
            </el-table>
        </el-container>
    </div>
</body>
</html>
<script>
var menuVue = new Vue({
    el:'#menu',
    data:{
        menuList:[], //菜单列表
    },
    methods:{
        //获取菜单列表
        list:function () {
            var that=this;
            axios({
                method: 'get',
                url: '${request.contextPath}/menu/list.do',
            }).then(
                function (data) {
                    that.menuList = data;
                }
            )
        },
        //新增菜单
        save:function () {
            window.location.href="${request.contextPath}/menu/form.do";
        },
        //编辑菜单
        update:function () {

        },
        //删除菜单
        del:function () {

        },
        //获取表格勾选数据
        handleselect:function (selection, row) {
            console.log(selection, row);
        }
    },
    mounted(){
        this.list();
    }
})
</script>
