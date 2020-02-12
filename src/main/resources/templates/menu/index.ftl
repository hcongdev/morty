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
                    <#if shiro.hasPermission("menu:save")>
                        <el-button type="info" icon="el-icon-plus" @click="save">新增</el-button>
                    </#if>
                    <#if shiro.hasPermission("menu:update")>
                    <el-button type="info" class="el-icon-edit" @click="update">修改</el-button>
                    </#if>
                    <#if shiro.hasPermission("menu:del")>
                    <el-button type="danger" class="el-icon-delete" @click="del">删除</el-button>
                    </#if>
                </el-row>

            </el-header>
            <el-table
                    :data="menuList"
                    @selection-change="handleSelectionChange"
                    style="width: 100%"
                    row-key="menuId"
                    border
                    style="width: 100%"
                    :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="menuName" label="菜单名称" align="center" width="180"> </el-table-column>
                <el-table-column prop="menuIcon" label="菜单图标" width="180"> </el-table-column>
                <el-table-column prop="menuType" label="菜单类型" width="180" :formatter="typeFormatter"> </el-table-column>
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
        selData: [], //选中菜单
    },
    methods:{
        //获取菜单列表
        list:function () {
            var that=this;
            axios({
                method: 'get',
                url: '${request.contextPath}/menu/menuList.do',
            }).then(
                function (data) {
                    if(data.length >0){
                        that.menuList =data;
                    }
                }
            )
        },
        //新增菜单
        save:function () {
            window.location.href="${request.contextPath}/menu/form.do";
        },
        //编辑菜单
        update:function () {
            var that =this;
            if(that.selData.length == 1 ){
                window.location.href = "${request.contextPath}/menu/form.do?menuId=" + that.selData[0].menuId;
            }else if (that.selData.length == 0 ) {
                that.$notify.info('请先选择菜单');
            }else{
                that.$notify.info('只能选择一项菜单');
            }
        },
        //删除菜单
        del:function () {
            var that = this;
            if(that.selData.length > 0 ){
                that.$confirm('确认删除?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    axios.post("${request.contextPath}/menu/del.do", that.selData).then(function (data) {
                        if (data) {
                            that.$notify.success('删除成功');
                            that.list();
                        }
                    })
                })
            }else {
                that.$notify.info('请先选择菜单');
            }

        },
        //按钮类型
        typeFormatter:function(row, column){
            if (row.menuType == 0){
                return "目录"
            }else if(row.menuType == 1){
                return "菜单"
            }else if (row.menuType == 2){
                return "按钮"
            }
        },
        handleSelectionChange: function(selection) {//列表选中项
            this.selData = selection;
        },
    },
    mounted(){
        this.list();
    }
})
</script>
