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
        <el-table :data="historyList" style="width: 100%">
            <el-table-column type="expand">
                <template slot-scope="props">
                    <el-form label-position="left" inline class="demo-table-expand">
                        <el-row v-if="props.row.teacherApprove != undefined">
                            <el-col :span="12" >
                                <el-form-item label="班主任审核">
                                    <span v-if="props.row.teacherApprove">同意</span>
                                    <span v-else>不同意</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="理由">
                                    <span>{{ props.row.teacherReason }}</span>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row v-if="props.row.headApprove != undefined">
                            <el-col :span="12">
                                <el-form-item label="校长审核">
                                    <span v-if="props.row.headApprove">同意</span>
                                    <span v-else>不同意</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="理由">
                                    <span>{{ props.row.headReason }}</span>
                                </el-form-item>
                            </el-col>
                        </el-row>

                    </el-form>
                </template>
            </el-table-column>
            <el-table-column prop="startUser" label="申请人"> </el-table-column>
            <el-table-column prop="reason" label="请假原因"> </el-table-column>
            <el-table-column prop="startDate" label="开始时间" :formatter="startDateFormatter"> </el-table-column>
            <el-table-column prop="endDate" label="结束时间" :formatter="endDateFormatter"> </el-table-column>
            <el-table-column prop="approve" label="请假结果" :formatter="approveFormatter"> </el-table-column>
            <el-table-column label="操作" fixed="right" align="center" width="200">
                <template slot-scope="scope">
                    <el-link type="primary" :underline="false" @click="delHistory(scope.row.processInstanceId)">删除记录</el-link>
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
            //时间转换
            startDateFormatter:function(row, column){
                return formateDate(row.startDate);
            },
            //时间转换
            endDateFormatter:function(row, column){
                return formateDate(row.endDate);
            },
            approveFormatter:function(row){
                if (row.headApprove == "true") {
                    return "已同意"
                }else if (row.headApprove == "false"){
                    return  "不同意";
                }else{
                    return "审核中"
                }
            },
            delHistory:function (id) {
                var that = this;
                that.$confirm('确认删除?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    axios({
                        method: 'post',
                        url: '${request.contextPath}/history/delete',
                        params: {processInstanceId:id},
                    }).then(
                        function (res) {
                            if (res.code = 1){
                                that.$notify.success('删除成功');
                                that.getHistoryList();
                            }else if (res.status == 500){
                                that.$notify.success('审核中的任务不可删除');
                            }
                        }
                    )
                })
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
    .demo-table-expand {
        font-size: 0;
    }
    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }
    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
</style>