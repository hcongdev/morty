<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>后台登录</title>
    <#include "/comment/comment_header.ftl">
</head>
<body>
    <div id="login">
        <el-form ref="form" :model="form"  label-width="80px">
            <el-form-item label="账号">
                <el-input v-model="form.managerName"></el-input>
            </el-form-item>
            <el-form-item label="密码">
                <el-input v-model="form.managerPassword"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">登录</el-button>
                <el-button>取消</el-button>
            </el-form-item>
        </el-form>
    </div>
</body>
</html>
<script>
    var loginVue = new Vue({
        el: '#login',
        data: {
            form:{
                managerName:'',
                managerPassword:"",
            }
        },
        methods:{
            onSubmit:function () {
                var that = this;
                var data = "username="+that.form.managerName + "&password=" + that.form.managerPassword;
                axios({
                    method: 'post',
                    url: 'checkLogin.do',
                    data: data,
                    headers:{'Content-Type':'application/x-www-form-urlencoded'}
                }).then(function (data) {
                    if (data.code == 1){
                        window.location.href = "index";
                    }else {
                        that.$message.error(data.data.msg);
                    }
                });
            },
            list:function () {
               axios.get(
                   "list",{managerId:50}
               ).then(function (value) {
                   console.log(value);
               })
            }
        },
        mounted(){
           this.list();
        }
    })
</script>