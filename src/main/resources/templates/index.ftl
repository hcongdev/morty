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
                    <span v-if="!isCollapse" style="width: 175px;">MortyMorty</span>
                </div>

                <div class="item" @click="collapse" style="margin: 10px 0;">
                   <i style="font-size: 35px" class="iconfont icon-zhankai"></i>
                </div>
                <div class="item">
                    <el-dropdown trigger="click" placement="top-start">
                        <span style="color: #fff">
                            admin
                        </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item @click.native="openInfo">修改密码</el-dropdown-item>
                            <el-dropdown-item @click.native="userQuit">退出</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>


            </el-header>
            <!--头部导航结束-->


            <el-container>
                <el-aside :class="[isCollapse ? 'collapseLength' :'nocollapseLength'] " style="min-height: 743px;">
                <!--侧边导航开始-->
                     <el-menu
                             default-active="2"
                             class="el-menu-vertical-demo"
                             @open="handleOpen"
                             background-color="#545c64"
                             text-color="#fff"
                             active-text-color="#ffd04b"
                             :collapse="isCollapse"
                             unique-opened="true"
                             >
                         <el-submenu :index="menu.menuId+''" :data-index="menu.menuId+''" v-for="(menu,i) in parentMenuList" :key='i'>
                             <template slot="title">
                                 <i class="iconfont" v-html="menu.menuIcon"></i>
                                 <span v-text="menu.menuName"></span>
                             </template>
                             <!-- 子菜单 -->
                             <el-menu-item :index="sub.menuId+''" :data-index="sub.menuId" v-for="(sub,index) in getSubMenu(menu.menuId)"
                                           :key='sub.menuId' v-text="sub.menuName" @click.self='openIframe(sub.menuUrl)'></el-menu-item>
                         </el-submenu>
                     </el-menu>
                 </el-aside>
                 <!-- 侧边导航结束-->

                <el-container>
                    <el-main>
                        <iframe class="iframe-class" :src="url"></iframe>
                    </el-main>
                </el-container>
            </el-container>
        </el-container>
    </div>

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
            openInfo: function(){

            },
            //退出登录
            userQuit:function(){

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
        },
        mounted(){
            this.getMemuList();
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