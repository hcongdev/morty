<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户表单</title>
    <#include "/comment/comment_header.ftl">
</head>
<body>
    <div id="managerForm">
        <el-container>
            <div class="mt-container">
                <div class="mt-form-header">新增</div>
                <el-form class="mt-form" ref="managerForm" :rules="rules" :model="managerForm" label-width="80px">
                    <el-form-item label="用户名" prop="managerName">
                        <el-input v-model="managerForm.managerName"></el-input>
                    </el-form-item>
                    <el-form-item label="用户昵称" prop="managerNickname">
                        <el-input v-model="managerForm.managerNickname"></el-input>
                    </el-form-item>
                    <el-form-item label="密码">
                        <el-input show-password v-model="managerForm.managerPassword"></el-input>
                    </el-form-item>
                    <el-form-item label="角色">
                        <el-checkbox-group v-model="managerForm.roleIdList"  @change="handleCheckedChange">
                            <el-checkbox v-for="role in roleList" :label="role.roleId" :key="role.roleId">{{role.roleName}}</el-checkbox>
                        </el-checkbox-group>
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" @click="saveOrUpdate">确认</el-button>
                        <el-button onclick="window.history.back(-1);">返回</el-button>
                    </el-form-item>
                </el-form>
            </div>

        </el-container>
    </div>

</body>
</html>
<script>
var managerFormVue = new Vue({
    el:'#managerForm',
    data:{
        managerId: 0,
        roleList:{},//角色列表
        managerForm:{
            managerName: '',
            managerNickname: '',
            managerPassword: '',
            roleIdList: [],
        },
        //表单验证
        rules:{
            managerName:[
                { required: true, message: '请输入用户名', trigger: 'blur' },
            ],
            managerNickName:[
                { required: true, message: '请输入用户昵称', trigger: 'blur' },
            ],
        }
    },
    methods:{
        //获取角色列表
        list:function () {
            var that=this;
            axios({
                method: 'get',
                url: '${request.contextPath}/role/list.do',
            }).then(
                function (res) {
                    if (res.code = 1){
                        that.roleList = res.data;
                    }
                }
            )
        },
        saveOrUpdate:function () {
            var that = this;
            this.$refs.managerForm.validate((valid) => {
                if (valid) {
                    var url = '';
                    if (this.managerId > 0){
                        url = '${request.contextPath}/manager/update.do';
                    }else{
                        url ='${request.contextPath}/manager/save.do';
                    }
                    axios.post(url,
                        Qs.stringify(that.managerForm),{ headers:{'Content-Type':'application/x-www-form-urlencoded'}}
                    ).then(
                        function (data) {
                            if (data.code== 1){
                                that.$message.success("保存成功");
                                window.location.href = "${request.contextPath}/manager/index.do";
                            }else{
                                that.$message.error(data.msg);
                            }
                        }
                    )
                } else {
                    return false;
                }
            });

        },
        handleCheckedChange:function(value){
            this.managerForm.roleIdList = value
        },
        get:function(managerId){
            var that =this;
            axios({
                method: 'get',
                params: {managerId:managerId},
                url: '${request.contextPath}/manager/get.do',
            }).then(
                function (result) {
                    if (result.code == 1){
                       that.managerForm = result.data;
                    }
                }
            )
        },
    },
    mounted(){
        this.list();
        this.managerId = parseInt(getParameter("managerId"));
        if (this.managerId > 0){
            this.get(this.managerId);
        }
    }
})
</script>
<style>
    .menuTree .el-input__inner{
        background-color: #eee;
        cursor: pointer;
    }
</style>
