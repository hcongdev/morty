<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户</title>
    <#include "/comment/comment_header.ftl">
</head>
<body>
    <div id="manager">
        <el-container>
            <el-header class="button-header">
                <el-row>
                    <el-button type="info" icon="el-icon-plus" @click="save">新增</el-button>
                    <el-button type="info" class="el-icon-edit" @click="update">修改</el-button>
                    <el-button type="danger" class="el-icon-delete" @click="del">删除</el-button>
                </el-row>
            </el-header>
            <el-table @selection-change="handleSelectionChange" :data="managerList" style="width: 100%" border>
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="managerId" label="用户编号" width="180"> </el-table-column>
                <el-table-column prop="managerName" label="账号" width="180"> </el-table-column>
                <el-table-column prop="managerNickname" label="昵称" width="180"> </el-table-column>
                <el-table-column prop="managerTime" label="创建时间" > </el-table-column>
            </el-table>
        </el-container>
    </div>
</body>
</html>
<script>
var managerVue = new Vue({
    el:'#manager',
    data:{
        managerList:[], //用户列表
        selData: [], //选中菜单
    },
    methods:{
        //获取菜单列表
        list:function () {
            var that=this;
            axios({
                method: 'get',
                url: '${request.contextPath}/manager/list.do',
            }).then(
                function (res) {
                    that.managerList = res.data;
                }
            )
        },
        //新增菜单
        save:function () {
            window.location.href="${request.contextPath}/manager/form.do";
        },
        //编辑菜单
        update:function () {
            var that =this;
            if(that.selData.length == 1 ){
                window.location.href = "${request.contextPath}/manager/form.do?managerId=" + that.selData[0].managerId;
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
                that.$confirm('确认删除?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    axios.post("${request.contextPath}/manager/del.do", that.selData).then(function (data) {
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
