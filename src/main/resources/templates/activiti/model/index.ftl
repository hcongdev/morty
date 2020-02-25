<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>主页</title>
    <#include "/comment/comment_header.ftl">
</head>
<body>
<div id="model">
    <el-container>
        <el-header class="button-header">
            <el-row>
                <el-button type="info" icon="el-icon-plus" @click="open">新增</el-button>
                <el-button type="danger" class="el-icon-delete" @click="del">删除</el-button>
            </el-row>

        </el-header>
        <el-table @selection-change="handleSelectionChange" :data="modelList" style="width: 100%" border>
            <el-table-column type="selection" width="55" align="center"></el-table-column>
            <el-table-column prop="id" label="模型id"> </el-table-column>
            <el-table-column prop="name" label="模型名称"> </el-table-column>
            <el-table-column prop="key" label="模型key"> </el-table-column>
            <el-table-column prop="version" label="模型版本"> </el-table-column>
            <el-table-column label="操作" fixed="right" align="center" width="200">
                <template slot-scope="scope">
                    <el-link type="primary" :underline="false" @click="editModel(scope.row.id)">编辑模型</el-link>
                    <el-link type="primary" :underline="false" @click="deploy(scope.row.id)">发布</el-link>
                </template>
            </el-table-column>
        </el-table>
    </el-container>
    <el-dialog title="模型新增" :visible.sync="isShow">
        <el-form :model="modelForm"  ref='modelForm' :rules='modelFormRules' size="mini" label-width="100px">
            <el-form-item label="名称"  prop='name'>
                <el-input v-model="modelForm.name" size="mini" placeholder="请输入名称"></el-input>
            </el-form-item>
            <el-form-item label="key值"  prop='key'>
                <el-input v-model="modelForm.key" size="mini" placeholder="请输入名称"></el-input>
            </el-form-item>
            <el-form-item label="描述"  prop='description'>
                <el-input v-model="modelForm.description" size="mini" placeholder="请输入描述"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="isShow = false" size="mini">取 消</el-button>
            <el-button type="primary" @click="save" size="mini">确 定</el-button>
        </div>
    </el-dialog>
</div>
</body>
<script>
    var modelVue = new Vue({
        el:'#model',
        data:{
            isShow:false,
            selData:[],
            modelForm:{
                name:'',
                key:'',
                description:'',
            },
            modelList:[], //模型集合
            modelFormRules: {
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
            open:function(){
                this.isShow = true;
                this.$refs.modelForm.resetFields();
            },
            //加载模型
            getModelList:function (){
                let that = this;
                axios({
                    method: 'get',
                    url: '${request.contextPath}/models/list.do',
                }).then(function (data) {
                    that.modelList = data.data;
                })
            },
            //创建模型
            save:function (){
                let that = this;
                axios({
                    method: 'get',
                    url: '${request.contextPath}/models/create.do',
                    params: {key:that.modelForm.key,name:that.modelForm.name,description:that.modelForm.description},
                }).then(function (data) {
                    if(data.code == 1){
                        that.getModelList();
                        that.isShow = false;
                    }
                })
            },
            handleSelectionChange: function(selection) {//列表选中项
                this.selData = selection;
            },
            //删除菜单
            del:function () {
                var that = this;
                if(that.selData.length == 1 ){
                    that.$confirm('确认删除?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        axios({
                            method: 'post',
                            url: '${request.contextPath}/models/delete',
                            params: {id:that.selData[0].id},
                        }).then(
                            function (res) {
                                if (res.code = 1){
                                    that.$notify.success('删除成功');
                                    that.getModelList();
                                }
                            }
                        )
                    })
                }else if (that.selData.length == 0) {
                    that.$notify.info('请先选择模型');
                }else {
                    that.$notify.info('最多只能选择一个模型');
                }

            },
            //编辑模型
            editModel:function (id) {
                window.open("${request.contextPath}/models/edit.do?modelId=" + id);
            },
            //发布模型
            deploy:function (id) {
                let that = this;
                axios({
                    method: 'post',
                    url: "${request.contextPath}/models/"+id+"/deployment.do",
                }).then(function (data) {
                    if (data.code == 1){
                        that.$message.success("发布成功");
                    } else {
                        that.$message.error(data.msg);
                    }
                })
            }
        },
        mounted(){
            this.getModelList();
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