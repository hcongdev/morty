<html>
<head>
    <!--vue-->
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <!-- 引入样式 -->
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <!-- 引入组件库 -->
    <script src="https://unpkg.com/element-ui/lib/index.js"></script>
    <!--axios-->
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <link rel="stylesheet" type="text/css"  href="${request.contextPath}/static/css/comment.css"/>
    <#-- iconfont-->
    <link rel="stylesheet" type="text/css" href="//at.alicdn.com/t/font_1126314_3xlr4xzucq3.css">
    <script>
        //axois拦截，只返回data参数
        axios.interceptors.response.use(res => {
            return res.data
        })
    </script>
</head>
</html>