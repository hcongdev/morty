<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>申请列表</title>
    <#include "/comment/comment_header.ftl">
</head>
<body>
<div id="history">
    <el-container>
        <el-header class="button-header">
            <el-row>
<#--                <el-input style="width:200px" v-model="roleName" placeholder="请输入角色名称"></el-input>-->
<#--                <el-button type="info" icon="el-icon-search" @click="search">搜索</el-button>-->
<#--                <el-button type="info" class="el-icon-edit" @click="update">修改</el-button>-->
<#--                <el-button type="danger" class="el-icon-delete" @click="del">删除</el-button>-->
            </el-row>

        </el-header>
        <el-table @selection-change="handleSelectionChange" :data="historyList" style="width: 100%" border>
            <el-table-column type="selection" width="55" align="center"></el-table-column>
            <el-table-column prop="id" label="任务id"> </el-table-column>
            <el-table-column prop="name" label="任务名称"> </el-table-column>
            <el-table-column prop="createTime" label="创建时间" :formatter="timesFormatter"> </el-table-column>
            <el-table-column prop="assignee" label="办理人"> </el-table-column>
            <el-table-column label="操作" fixed="right" align="center" width="200">
                <template slot-scope="scope">
                    <el-link type="primary" :underline="false" @click="isShow=true;id=scope.row.id">办理任务</el-link>
                    <el-link type="primary" :underline="false" @click="showPic(scope.row.processInstanceId)">查看当前流程图</el-link>
                </template>
            </el-table-column>
        </el-table>
    </el-container>
</div>
</body>
<script>
    var historyVue = new Vue({
        el:'#history',
        data:{
            isShow:false,
            id: 0,//任务编号
            historyList:[], //模型集合
        },
        methods: {
            //加载申请记录
            getHistoryList:function (){
                let that = this;
                axios({
                    method: 'get',
                    url: '${request.contextPath}/history/process/mys',
                }).then(function (data) {
                    if (data.code == 1 ){
                        that.historyList = data.data;
                    }
                })
            },
            handleSelectionChange: function(selection) {//列表选中项
                this.selData = selection;
            },
        },
        mounted(){
            this.getHistoryList();
        }
    })
</script>
</body>
</html>
<style>
    .main-header{
        align-items: center;
        background-color: rgb(84, 92, 100);
        display: flex;
    }
    .item:last-child {
        margin-left: auto;
        margin-right: 0px;
    }
    .main-header-title{
        display: flex;
        align-items: center;
        overflow: hidden;
    }
    .el-menu{
        height: 100%;
    }
    .el-menu-vertical-demo:not(.el-menu--collapse) {
        width: 200px;
    }
    ..el-aside:not(.el-menu--collapse) {
        width: 200px;
    }
    .collapseLength{
        width: 65px !important;
    }
    .nocollapseLength{
        width: 201px !important;
    }
    .iframe-class{
        width: 100%;
        height: 100vh;
        border-width: 0px;
    }
</style>