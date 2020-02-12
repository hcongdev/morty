<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>主页</title>
    <#include "/comment/comment_header.ftl">
</head>
<body>
    <div id="index" v-cloak>
        <el-container>
            <!--头部导航开始-->
            <el-header class="main-header">
                <div class="main-header-title item">
                    <span v-if="isCollapse">Morty</span>
                    <span v-if="!isCollapse" style="width: 175px;">欢迎使用</span>
                </div>

                <div class="item" @click="collapse" style="margin: 10px 0;">
                   <i style="font-size: 35px" class="iconfont icon-zhankai"></i>
                </div>
                <div class="item">
                    <el-dropdown trigger="click" placement="top-start">
                        <span style="color: #fff">
                           {{userName}}
                        </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item @click.native="changeFormVisible = true">修改密码</el-dropdown-item>
                            <el-dropdown-item @click.native="userQuit">退出</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>


            </el-header>
            <!--头部导航结束-->


            <el-container>
                <!--侧边导航开始-->
                     <el-menu
                             style="height: 100vh;"
                             default-active="2"
                             class="el-menu-vertical-demo"
                             @open="handleOpen"
                             background-color="#545c64"
                             text-color="#fff"
                             active-text-color="#ffd04b"
                             :collapse="isCollapse"
                             :unique-opened="true"
                             >
                         <el-submenu :index="menu.menuId+''" :data-index="menu.menuId+''" v-for="(menu,i) in parentMenuList" :key='i'>
                             <template slot="title">
                                 <i class="iconfont" v-html="menu.menuIcon"></i>
                                 <span v-text="menu.menuName"></span>
                             </template>
                             <!-- 子菜单 -->
                             <el-menu-item :index="sub.menuId+''" :data-index="sub.menuId" v-for="(sub,index) in getSubMenu(menu.menuId)"
                                           :key='sub.menuId' v-text="sub.menuName" @click.self='openIframe(sub.menuUrl);menuName=menu.menuName'></el-menu-item>
                         </el-submenu>
                     </el-menu>
                 <!-- 侧边导航结束-->

                <el-container>
                    <el-header class="container-header" style="padding: 5px 0px 0px 10px;">
                        {{menuName}}
                    </el-header>
                    <el-main>
                        <iframe class="iframe-class" :src="url"></iframe>
                    </el-main>
                </el-container>
            </el-container>
        </el-container>
        <el-dialog title="修改密码" :visible.sync="changeFormVisible" width="40%">
            <el-form :model="passwordForm"  :rules="rules" ref="passwordForm" >
                <el-form-item label="原密码" label-width="120px" prop="managerOldPassword">
                    <el-input v-model="passwordForm.managerOldPassword" show-password></el-input>
                </el-form-item>
                <el-form-item label="新密码" label-width="120px" prop="managerPassword">
                    <el-input v-model="passwordForm.managerPassword" show-password></el-input>
                </el-form-item>
                <el-form-item label="确认新密码" label-width="120px" prop="managerNewPassword">
                    <el-input v-model="passwordForm.managerNewPassword" show-password></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="changeFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="changePassword">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</body>
<script>
    var indexVue = new Vue({
        el:'#index',
        data:{
            isCollapse: true,
            activeIndex2: '1',
            url:"",
            menuList:[], //菜单集合
            parentMenuList:[], //一级菜单
            subMenuList:[], //二级菜单
            changeFormVisible: false, //修改密码弹窗
            userName:"",//用户名
            menuName:"主界面",//菜单名
            passwordForm:{
                managerOldPassword:"", //旧密码
                managerPassword:"", //新密码
                managerNewPassword:"", //确认新密码
            },
            rules: {
                managerOldPassword: [
                    { required: true, message: '请输入原密码', trigger: 'blur' },
                    { min: 6, max: 30, message: '长度在 6 到 30 个字符', trigger: 'blur' }
                ],
                managerPassword: [
                    { required: true, message: '请输入新密码', trigger: 'blur' },
                    { min: 6, max: 30, message: '长度在 6 到 30 个字符', trigger: 'blur' }
                ],
                managerNewPassword: [
                    { required: true, message: '请输入确认新密码', trigger: 'blur' },
                    { min: 6, max: 30, message: '长度在 6 到 30 个字符', trigger: 'blur' }
                ],
            }
        },
        watch:{
            menuList:function (n,o) {
                var that = this;
                n && n.forEach(function (item, index) {
                    item.menuParentId == 0 ? that.parentMenuList.push(item) : that.subMenuList.push(item)
                })
            }
        },
        methods: {
            //折叠菜单
            collapse: function(){
                indexVue.isCollapse = !indexVue.isCollapse
                this.isCollapse = indexVue.isCollapse
            },
            //修改密码
            changePassword: function(){
                var that = this;
                this.$refs.passwordForm.validate((valid) => {
                    if (valid) {
                        if (that.passwordForm.managerPassword != that.passwordForm.managerNewPassword){
                            that.$message.error("确认新密码和新密码不一致");
                            return ;
                        }
                        axios.post("${request.contextPath}/manager/changePassword.do",
                            Qs.stringify(that.passwordForm),{ headers:{'Content-Type':'application/x-www-form-urlencoded'}}
                        ).then(
                            function (data) {
                                if (data.code== 1){
                                    that.$message.success("修改成功");
                                    window.location.href = "${request.contextPath}/login.do";
                                }else{
                                    that.$message.error(data.msg);
                                }
                            }
                        )
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });

            },
            //退出登录
            userQuit:function(){
                this.$confirm('确认退出?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    axios({
                        method: 'get',
                        url: '${request.contextPath}/manager/quit.do',
                    }).then(
                        function (data) {
                            if (data.code == 1){
                                window.location.href = "${request.contextPath}/login.do"
                            }
                        }
                    )
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消'
                    });
                });
            },
            //打开iframe
            openIframe:function(url){
               this.url =  "${request.contextPath}/" + url;
               console.log(this.url);
            },
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            //加载菜单
            getMemuList:function (){
                let that = this;
                axios({
                    method: 'get',
                    url: 'menu/list.do',
                }).then(function (data) {
                    that.menuList = data;
                })
            },
            // 获取当前菜单的子菜单
            getSubMenu: function (menuId) {
                var result = [];
                var that = this;
                that.subMenuList && that.subMenuList.forEach(function (item) {
                   item.menuParentId == menuId ? result.push(item) : ''
                })
                return result;
            },
            getUser:function () {
                var that = this;
                axios({
                    method: 'get',
                    url: '${request.contextPath}/manager/info.do',
                }).then(
                    function (data) {
                        if (data.code == 1){
                            that.userName = data.data.managerName

                        }
                    }
                )
            }
        },
        mounted(){
            this.getMemuList();
            this.getUser();
            this.openIframe('main');
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