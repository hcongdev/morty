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
                        <el-radio-group v-model="menuForm.name">
                            <el-radio :label="0">目录</el-radio>
                            <el-radio :label="1">菜单</el-radio>
                            <el-radio :label="2">按钮</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="菜单名称">
                        <el-input v-model="menuForm.menuName"></el-input>
                    </el-form-item>
                    <el-form-item label="上级菜单">
                        <el-input v-model="menuForm.menuParentId" class="menuTree" @focus="dialogVisible = true"  readonly></el-input>
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
        <el-dialog
                title="选择菜单"
                :visible.sync="dialogVisible"
                width="30%">
            <el-tree
                    :data="newList"
                    :props="defaultProps"
                    @node-click="handleNodeClick"
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
        menuForm:{
            menuName:"",
            menuIcon:"",
            menuType: 0 ,
            menuPerms:"",
        }, //菜单表单
        menuList:[],//菜单列表
        dialogVisible:false,
        defaultProps:{
            label: 'menuName',
            children: 'children',
        },
        newList:[], //菜单列表
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
        },
        //获取菜单列表
        list:function () {
            var that=this;
            axios({
                method: 'get',
                url: '${request.contextPath}/menu/list.do',
            }).then(
                function (data) {
                    that.menuList = data;
                    var sonList= [];
                    for (var i =0;i<that.menuList.length;i++){
                        if (that.menuList[i].menuParentId ==0){
                            that.newList[i] =that.menuList[i];
                            sonList=[];
                          for (var j=0;j<that.menuList.length;j++) {
                              if (that.menuList[i].menuId == that.menuList[j].menuParentId) {
                                  sonList.push(that.menuList[j]);
                                  that.newList[i].children=sonList;
                              }
                          }
                        }
                    }
                }
            )
        },
        handleNodeClick:function (data) {
            this.dialogVisible = false;
            this.menuForm.menuParentId = data.menuId;
            console.log(data)
        }
    },
    mounted(){
        this.list();
    }
})
</script>
<style>
    .menuTree .el-input__inner{
        background-color: #eee;
        cursor: pointer;
    }
</style>
