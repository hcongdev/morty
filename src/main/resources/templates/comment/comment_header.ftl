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
    <script src="https://cdn.bootcss.com/qs/6.5.1/qs.min.js"></script>
    <script src="${request.contextPath}/static/js/util/util.js"></script>
    <link rel="stylesheet" type="text/css"  href="${request.contextPath}/static/css/comment.css"/>
    <#-- iconfont-->
    <link rel="stylesheet" type="text/css" href="//at.alicdn.com/t/font_1369349_17vagmljv4d.css">
    <script>
        //axois拦截，只返回data参数
        axios.interceptors.response.use(res => {
            return res.data
        })
        axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
    </script>
</head>
</html>