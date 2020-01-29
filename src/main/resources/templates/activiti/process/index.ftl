<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>流程</title>
    <#include "/comment/comment_header.ftl">
</head>
<body>
<div id="process">
    <el-container>
        <el-table @selection-change="handleSelectionChange" :data="processList" style="width: 100%" border>
            <el-table-column prop="id" label="流程定义ID"> </el-table-column>
            <el-table-column prop="deploymentId" label="部署ID"> </el-table-column>
            <el-table-column prop="key" label="流程定义KEY"> </el-table-column>
            <el-table-column prop="version" label="版本号"> </el-table-column>
            <el-table-column prop="resourceName" label="XML资源名称">
                <template slot-scope="scope">
                    <el-link type="primary" :href="'${request.contextPath}/process/read-resource.do?id='+scope.row.id+'&resourceName='+scope.row.resourceName" target="_blank">{{scope.row.resourceName}}</el-link>
                </template>
            </el-table-column>
            <el-table-column prop="diagramResourceName" label="图片资源名称">
                <template slot-scope="scope">
                    <el-link type="primary" :href="'${request.contextPath}/process/read-resource.do?id='+scope.row.id+'&resourceName='+scope.row.diagramResourceName" target="_blank">{{scope.row.diagramResourceName}}</el-link>
                </template></el-table-column>
            <el-table-column label="操作" fixed="right" align="center" width="200">
                <template slot-scope="scope">
                    <el-link type="primary" :underline="false" @click="deleteProcess(scope.row.deploymentId)">删除</el-link>
                    <el-link type="primary" :underline="false" @click="openProcessDialog(scope.row.key)">启动</el-link>
                </template>
            </el-table-column>
        </el-table>
    </el-container>
    <el-dialog title="启动流程" :visible.sync="isShow">
        <el-form :model="processForm"  ref='processForm' :rules='processFormRules' size="mini" label-width="100px">
            <el-form-item label="请假开始时间"  prop='startDate'>
                <el-date-picker
                        v-model="processForm.startDate"
                        type="datetime"
                        placeholder="选择请假开始时间">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="请假结束时间"  prop='endDate'>
                <el-date-picker
                        v-model="processForm.endDate"
                        type="datetime"
                        placeholder="选择请假结束时间">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="请假原因"  prop='reason'>
                <el-input v-model="processForm.reason" size="mini" placeholder="请输入请假原因"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="isShow = false" size="mini">取 消</el-button>
            <el-button type="primary" @click="runProcess" size="mini">确 定</el-button>
        </div>
    </el-dialog>
</div>
</body>
<script>
    var processlVue = new Vue({
        el:'#process',
        data:{
            isShow:false,
            processList:[], //模型集合
            selData: [], //选中流程
            processForm:{
                startDate:'', //开始时间
                endDate:'', //结束时间
                reason:'', //原因
            },
            key:'',//部署key
            processFormRules:{},
        },
        methods: {
            open:function(){
              this.isShow = true;
            },
            //加载流程
            getProcessList:function (){
                let that = this;
                axios({
                    method: 'get',
                    url: '${request.contextPath}/process/list.do',
                }).then(function (data) {
                    that.processList = data.data;
                })
            },
            handleSelectionChange: function(selection) {//列表选中项
                this.selData = selection;
            },
            //删除流程
            deleteProcess:function (id) {
                var that = this;
                that.$confirm('确认删除?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    axios({
                        method: 'post',
                        url: '${request.contextPath}/process/delete.do',
                        params: {deploymentId:id},
                    }).then(
                        function (res) {
                            if (res.code = 1){
                                that.$notify.success('删除成功');
                                that.getProcessList();
                            }
                        }
                    )
                })
            },
            openProcessDialog:function(key){
                this.isShow = true;
                this.key = key;
            },
            //启动流程
            runProcess:function () {
                let that = this;
                axios({
                    method: 'get',
                    url: "${request.contextPath}/process/run/"+that.key+".do",
                    params: that.processForm
                }).then(function (data) {
                    if (data.code == 1){
                        that.$message.success("流程启动成功");
                    } else {
                        that.$message.error(data.msg);
                    }
                })
            }
        },
        mounted(){
            this.getProcessList();
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