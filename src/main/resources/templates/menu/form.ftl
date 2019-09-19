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
                <el-form class="mt-form" ref="menuForm" :rules="rules" :model="menuForm" label-width="80px">
                    <el-form-item label="菜单类型" prop="menuType">
                        <el-radio-group v-model="menuForm.menuType">
                            <el-radio :label="0">目录</el-radio>
                            <el-radio :label="1">菜单</el-radio>
                            <el-radio :label="2">按钮</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="菜单名称" prop="menuName">
                        <el-input v-model="menuForm.menuName"></el-input>
                    </el-form-item>
                    <el-form-item label="上级菜单">
                        <el-input v-model="menuForm.menuParentName" class="menuTree" @focus="dialogVisible = true"  readonly></el-input>
                    </el-form-item>
                    <el-form-item label="菜单URL" prop="menuUrl" v-if="menuForm.menuType ==1">
                        <el-input v-model="menuForm.menuUrl"></el-input>
                    </el-form-item>
                    <el-form-item label="菜单权限" v-if="!(menuForm.menuType ==0)">
                        <el-input v-model="menuForm.menuPerms"></el-input>
                    </el-form-item>
                    <el-form-item label="排序"  v-if="!(menuForm.menuType ==2)">
                        <el-input v-model="menuForm.menuOrder"></el-input>
                    </el-form-item>
                    <el-form-item label="图标"  v-if="!(menuForm.menuType ==2)">
                        <el-input v-model="menuForm.menuIcon"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="saveOrUpdate">确认</el-button>
                        <el-button onclick="window.history.back(-1);">返回</el-button>
                    </el-form-item>
                </el-form>
            </div>

        </el-container>
        <el-dialog
                title="选择菜单"
                :visible.sync="dialogVisible"
                width="30%">
            <el-tree
                    :data="newList"
                    :props="defaultProps"
                    @node-click="handleNodeClick"
                    :props="defaultProps"
                    default-expand-all>
            </el-tree>
        </el-dialog>
    </div>

</body>
</html>
<script>
var menuFormVue = new Vue({
    el:'#menuForm',
    data:{
        menuId: 0 ,//菜单编号
        menuForm:{
            menuName:"",
            menuIcon:"",
            menuType: 0 ,
            menuPerms:"",
            menuParentId: 1,
            menuParentName: "系统设置",
        }, //菜单表单
        menuList:[],//菜单列表
        newList:[],
        dialogVisible:false,
        defaultProps:{
            label: 'menuName',
            children: 'children',
        },
        //表单验证
        rules:{
            menuName:[
                { required: true, message: '请输入菜单名称', trigger: 'blur' },
            ],
            menuUrl:[
                { required: true, message: '请输入菜单url', trigger: 'blur' },
            ],
        }
    },
    methods:{
        saveOrUpdate:function () {
            var that = this;
            this.$refs.menuForm.validate((valid) => {
                if (valid) {
                    var url = '';
                    if (this.menuId > 0){
                        url = '${request.contextPath}/menu/update.do';
                    }else{
                        url ='${request.contextPath}/menu/save.do';
                    }
                    delete that.menuForm.children;
                    axios.post(url,
                        Qs.stringify(that.menuForm),{ headers:{'Content-Type':'application/x-www-form-urlencoded'}}
                    ).then(
                        function (data) {
                            if (data.code== 1){
                                that.$message.success("保存成功");
                                window.location.href = "${request.contextPath}/menu/index.do";
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
        //获取菜单列表
        list:function () {
            var that=this;
            axios({
                method: 'get',
                url: '${request.contextPath}/menu/menuList.do',
            }).then(
                function (data) {
                    var child = {};
                    child.menuName = "一级菜单";
                    child.children = data;
                    that.newList[0] = child;
                    console.log(that.newList)
                }
            )
        },
        get:function(menuId){
            var that =this;
            axios({
                method: 'get',
                params: {menuId:menuId},
                url: '${request.contextPath}/menu/get.do',
            }).then(
                function (result) {
                    if (result.code == 1){
                       that.menuForm = result.data;
                    }
                }
            )
        },
        handleNodeClick:function (data) {
            this.dialogVisible = false;
            this.menuForm.menuParentId = data.menuId;
            this.menuForm.menuParentName = data.menuName;
            console.log(data)
        }
    },
    mounted(){
        this.list();
        this.menuId = parseInt(getParameter("menuId"));
        if (this.menuId > 0){
            this.get(this.menuId);
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
