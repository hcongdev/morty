<!DOCTYPE html>
<html >
<head>
<meta charset="UTF-8">
	<title>后台登录</title>
	<#include "/comment/comment_header.ftl">
	<link rel="stylesheet" href="${request.contextPath}/static/css/normalize.css">
</head>
<body>

<div class="login">
	<h1>Login</h1>
	<div>
		<input type="text" v-model="form.managerName" placeholder="用户名" required="required" />
		<input type="password"  v-model="form.managerPassword" placeholder="密码" required="required" />
		<button @click="onSubmit" class="btn btn-primary btn-block btn-large">登录</button>
	</div>
</div>
</body>
</html>
<script>
	var loginVue = new Vue({
		el: '.login',
		data: {
			form:{
				managerName:'',
				managerPassword:"",
			}
		},
		methods:{
			onSubmit:function () {
				var that = this;
				if(this.form.managerName == "" || that.form.managerPassword == ""){
					return ;
				}
				var data = "username="+that.form.managerName + "&password=" + that.form.managerPassword;
				axios({
					method: 'post',
					url: 'checkLogin.do',
					data: data,
					headers:{'Content-Type':'application/x-www-form-urlencoded'}
				}).then(function (data) {
					if (data.code == 1){
						window.location.href = "index";
					}else {
						that.$message.error(data.msg);
					}
				});
			},
		},
	})
</script>