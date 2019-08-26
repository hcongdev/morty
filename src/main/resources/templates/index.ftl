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
                <el-aside width="200px">
                <!--侧边导航开始-->
                    <!-- <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">
                         <el-radio-button :label="false">展开</el-radio-button>
                         <el-radio-button :label="true">收起</el-radio-button>
                     </el-radio-group>-->
                     <el-menu
                             default-active="2"
                             class="el-menu-vertical-demo"
                             @open="handleOpen"
                             @close="handleClose"
                             background-color="#545c64"
                             text-color="#fff"
                             active-text-color="#ffd04b"
                             :collapse="isCollapse"
                             >
                         <el-submenu index="1">
                             <template slot="title">
                                 <i class="el-icon-location"></i>
                                 <span slot="title">系统管理</span>
                             </template>
                             <el-menu-item-group>
                                 <el-menu-item index="1-1" @click="openIframe('admin')">管理员管理</el-menu-item>
                                 <el-menu-item index="1-2" @click="openIframe('role')">角色管理</el-menu-item>
                             </el-menu-item-group>
                         </el-submenu>
                     </el-menu>
                 </el-aside>
                 <!-- 侧边导航结束-->


                <el-container>
                    <el-main>
                        <iframe :src="url"></iframe>
                    </el-main>
                    <el-footer>Footer</el-footer>
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
                this.src = url;
            },
            handleSelect(key, keyPath) {
                console.log(key, keyPath);
            },
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            }
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

</style>