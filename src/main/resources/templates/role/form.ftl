<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>角色</title>
    <#include "/comment/comment_header.ftl">
</head>
<body>
    <div id="roleForm">
        <el-container>
            <div class="mt-container">
                <div class="mt-form-header">新增</div>
                <el-form class="mt-form" ref="roleForm" :rules="rules" :model="roleForm" label-width="80px">
                    <el-form-item label="角色名称" prop="roleName">
                        <el-input v-model="roleForm.roleName"></el-input>
                    </el-form-item>
                    <el-form-item label="权限管理">
                        <el-tree
                                ref="tree"
                                :data="menuList"
                                :props="defaultProps"
                                show-checkbox
                                node-key="menuId"
                                @check="handleCheck"
                                default-expand-all>
                        </el-tree>
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
var roleFormVue = new Vue({
    el:'#roleForm',
    data:{
        roleId: 0 , //角色编号
        roleForm:{
            roleName:"", //角色名称
            menuIdList:[], //选中的菜单编号列表
        }, //菜单表单
        menuList:[],//菜单列表
        defaultProps:{
            label: 'menuName',
            children: 'children',
        },
        //表单验证
        rules:{
            roleName:[
                { required: true, message: '请输入角色名称', trigger: 'blur' },
            ],
        }
    },
    methods:{
        handleCheck:function(node,checkedNodes){
            //获取所有选中的menuId
            this.roleForm.menuIdList = checkedNodes.checkedKeys;
        },
        saveOrUpdate:function () {
            var that = this;
            this.$refs.roleForm.validate((valid) => {
                if (valid) {
                    var url = '';
                    if (this.roleId > 0){
                        url = '${request.contextPath}/role/update.do';
                    }else{
                        url ='${request.contextPath}/role/save.do';
                    }
                    delete that.roleForm.children;
                    axios.post(url,
                        Qs.stringify(that.roleForm),{ headers:{'Content-Type':'application/x-www-form-urlencoded'}}
                    ).then(
                        function (data) {
                            if (data.code== 1){
                                that.$message.success("保存成功");
                                window.location.href = "${request.contextPath}/role/index.do";
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
                    if (data.length > 0){
                        that.menuList = data;
                    }
                }
            )
        },
        get:function(roleId){
            var that =this;
            axios({
                method: 'get',
                params: {roleId:roleId},
                url: '${request.contextPath}/role/get.do',
            }).then(
                function (result) {
                    if (result.code == 1){
                       that.roleForm = result.data;
                        // console.log(data.menuIdList)
                        that.$refs.tree.setCheckedKeys(result.data.menuIdList);
                    }
                }
            )
        },
    },
    mounted(){
        this.list();
        this.roleId = parseInt(getParameter("roleId"));
        if (this.roleId > 0){
            this.get(this.roleId);
        }
    }
})
</script>
