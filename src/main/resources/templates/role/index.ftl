<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>角色</title>
    <#include "/comment/comment_header.ftl">
</head>
<body>
    <div id="role">
        <el-container>
            <el-header class="button-header">
                <el-row>
                    <el-input style="width:200px" v-model="roleName" placeholder="请输入角色名称"></el-input>
                    <el-button type="info" icon="el-icon-search" @click="search">搜索</el-button>
                    <el-button type="info" icon="el-icon-plus" @click="save">新增</el-button>
                    <el-button type="info" class="el-icon-edit" @click="update">修改</el-button>
                    <el-button type="danger" class="el-icon-delete" @click="del">删除</el-button>
                </el-row>

            </el-header>
            <el-table @selection-change="handleSelectionChange" :data="roleList" style="width: 100%" border>
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="roleId" label="角色编号" align="center" width="180"> </el-table-column>
                <el-table-column prop="roleName" label="角色名称"> </el-table-column>
            </el-table>
        </el-container>
    </div>
</body>
</html>
<script>
var roleVue = new Vue({
    el:'#role',
    data:{
        roleName: "", //角色名称
        roleList:[], //角色列表
        selData: [], //选中菜单
    },
    methods:{
        //获取角色列表
        list:function () {
            var that=this;
            axios({
                method: 'get',
                url: '${request.contextPath}/role/list.do',
                params: {roleName:that.roleName},
            }).then(
                function (res) {
                    if (res.code = 1){
                        that.roleList = res.data;
                    }
                }
            )
        },
        //搜索
        search:function(){
            //this.roleName =
            this.list()



        },
        //新增菜单
        save:function () {
            window.location.href="${request.contextPath}/role/form.do";
        },
        //编辑菜单
        update:function () {
            var that =this;
            if(that.selData.length == 1 ){
                window.location.href = "${request.contextPath}/role/form.do?roleId=" + that.selData[0].roleId;
            }else if (that.selData.length == 0 ) {
                that.$notify.info('请先选择菜单');
            }else{
                that.$notify.info('只能选择一项菜单');
            }
        },
        //删除菜单
        del:function () {
            var that = this;
            if(that.selData.length > 0 ){
                var roleIds = [];
                for (var i=0;i <that.selData.length;i++ ){
                    roleIds.push(that.selData[i].roleId);
                }
                that.$confirm('确认删除?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    axios.post("${request.contextPath}/role/del.do",{params:
                            JSON.stringify(roleIds),
                    }).then(function (data) {
                        if (data) {
                            that.$notify.success('删除成功');
                            that.list();
                        }
                    })
                })
            }else {
                that.$notify.info('请先选择菜单');
            }

        },
        handleSelectionChange: function(selection) {//列表选中项
            this.selData = selection;
        },
    },
    mounted(){
        this.list();
    }
})
</script>
