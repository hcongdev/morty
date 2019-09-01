<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>菜单</title>
    <#include "/comment/comment_header.ftl">
</head>
<body>
    <div id="menuForm">
        <el-container>
            <div class="mt-container">
                <div class="mt-form-header">新增</div>
                <el-form class="mt-form" ref="menuForm" :model="menuForm" label-width="80px">
                    <el-form-item label="菜单类型">
                        <el-input v-model="menuForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="菜单名称">
                        <el-input v-model="menuForm.menuName"></el-input>
                    </el-form-item>
                    <el-form-item label="上级菜单">
                        <el-input v-model="menuForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="菜单URL">
                        <el-input v-model="menuForm.menuUrl"></el-input>
                    </el-form-item>
                    <el-form-item label="菜单权限">
                        <el-input v-model="menuForm.menuPerms"></el-input>
                    </el-form-item>
                    <el-form-item label="排序">
                        <el-input v-model="menuForm.menuOrder"></el-input>
                    </el-form-item>
                    <el-form-item label="图标">
                        <el-input v-model="menuForm.menuIcon"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="save">确认</el-button>
                        <el-button>返回</el-button>
                    </el-form-item>
                </el-form>
            </div>

        </el-container>
    </div>
</body>
</html>
<script>
var menuFormVue = new Vue({
    el:'#menuForm',
    data:{
        menuForm:{
            menuName:"",
            menuIcon:"",
            menuType: 0 ,
            menuPerms:"",
        }, //菜单表单
    },
    methods:{
        save:function () {
            var that = this;
            axios.post('${request.contextPath}/menu/save.do',
                Qs.stringify(that.menuForm),{ headers:{'Content-Type':'application/x-www-form-urlencoded'}}
            ).then(
                function (data) {
                    if (data.code== 1){
                        that.$message.success("保存");
                    }else{
                        that.$message.error(data.msg);
                    }
                }
            )
        }
    },
    mounted(){
    }
})
</script>
<style>

</style>
