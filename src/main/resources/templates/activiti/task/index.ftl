<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>任务列表</title>
    <#include "/comment/comment_header.ftl">
</head>
<body>
<div id="task">
    <el-container>
        <el-table @selection-change="handleSelectionChange" :data="taskList" style="width: 100%" border>
            <el-table-column prop="id" label="任务id"> </el-table-column>
            <el-table-column prop="name" label="任务名称"> </el-table-column>
            <el-table-column prop="createTime" label="创建时间" :formatter="timesFormatter"> </el-table-column>
            <el-table-column prop="reason" label="请假原因" > </el-table-column>
            <el-table-column prop="startDate" label="请假开始时间"  :formatter="startDateFormatter"> </el-table-column>
            <el-table-column prop="endDate" label="请假结束时间"  :formatter="endDateFormatter"> </el-table-column>
            <el-table-column prop="assigneeName" label="办理人"> </el-table-column>
            <el-table-column label="操作" fixed="right" align="center" width="200">
                <template slot-scope="scope">
                    <el-link type="primary" v-if="scope.row.name == '请假申请'" :underline="false" @click="id=scope.row.id;completeTask(scope.row)">提交审核</el-link>
                    <el-link type="primary" v-else :underline="false" @click="isShow=true;id=scope.row.id;aproveName=scope.row.name">办理任务</el-link>
                    <el-link type="primary" :underline="false" @click="showPic(scope.row.processInstanceId)">查看当前流程图</el-link>
                </template>
            </el-table-column>
        </el-table>
    </el-container>
    <el-dialog title="模型新增" :visible.sync="isShow">
        <el-form :model="taskForm"  ref='taskForm' :rules='taskFormRules' size="mini" label-width="100px">
            <el-form-item label="审批意见"  prop='approve' >
                <el-radio-group v-model="taskForm.approve">
                    <el-radio :label="true">同意</el-radio>
                    <el-radio :label="false">不同意</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="原因"  prop='reason'>
                <el-input v-model="taskForm.reason" size="mini" placeholder="请输入原因"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="isShow = false" size="mini">取 消</el-button>
            <el-button type="primary" @click="completeTask" size="mini">确 定</el-button>
        </div>
    </el-dialog>
</div>
</body>
<script>
    var taskVue = new Vue({
        el:'#task',
        data:{
            isShow:false,
            id: 0,//任务编号
            aproveName:"",//任务名称
            taskForm:{
                approve:"",
                name:'',
                reason:'',
            },
            taskList:[], //模型集合
            taskFormRules: {
                name: [{
                    required: true,
                    message: '请输入名称',
                    trigger: 'blur',
                }, ],
                key: [{
                    required: true,
                    message: '请选择key',
                    trigger: 'blur'
                }],
                description: [{
                    required: true,
                    message: '请输入描述',
                    trigger: 'blur',
                }]
            },
        },
        methods: {
            //加载任务列表
            getTaskList:function (){
                let that = this;
                axios({
                    method: 'get',
                    url: '${request.contextPath}/task/todoTask.do',
                }).then(function (data) {
                    if (data.code == 1 ){
                        that.taskList = data.data;
                    }
                })
            },
            handleSelectionChange: function(selection) {//列表选中项
                this.selData = selection;
            },
            //时间转换
            timesFormatter:function(row, column){
                return timestampToTime(row.createTime);
            },
            //时间转换
            startDateFormatter:function(row, column){
                return formateDate(row.startDate);
            },
            //时间转换
            endDateFormatter:function(row, column){
                return formateDate(row.endDate);
            },
            //办理当前任务
            completeTask:function (data) {
                let that = this;
                this.isShow = true;
                if (data.id != null){
                    that.isShow = false;
                    that.taskForm.approve = data.approve;
                    that.taskForm.name = data.name;
                }else if (that.aproveName == "班主任"){
                    that.isShow = true;
                    that.taskForm.teacherApprove = that.taskForm.approve;
                    that.taskForm.teacherReason = that.taskForm.reason;
                    delete  that.taskForm.approve;
                }else if(that.aproveName == "教务处"){
                    that.isShow = true;
                    that.taskForm.headApprove =  that.taskForm.approve;
                    that.taskForm.headReason = that.taskForm.reason;
                    delete  that.taskForm.approve;
                }
                axios({
                    method: 'get',
                    params: that.taskForm,
                    url: '${request.contextPath}/task/complete/'+that.id +'.do',
                }).then(function (data) {
                    if (data.code == 1 ){
                        that.isShow = false;
                        that.getTaskList();
                    }
                })
            },
            //显示流程图片
            showPic:function (processInstanceId) {
                window.open("${request.contextPath}/task/getpng?processInstanceId="+processInstanceId);
            }
        },
        mounted(){
            this.getTaskList();
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