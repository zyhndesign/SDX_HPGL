<%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta content="webkit" name="renderer">
        <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
        <title>服装货品数据管理系统</title>
        <base href="<%=request.getContextPath() %>/" />
        <link type="text/css" rel="stylesheet" href="resources/css/src/login.css">
    </head>
<body>



<form class="pCenter" id="myForm" method="post" action="dologin">
   <div class="logo">
        <span class="icon"></span>
    </div>
    <h2 class="appTitle">服装货品数据管理系统</h2>
    <div class="row">
        <div><label class="ctrlLabel icon-portrait"></label></div>
        <input class="ctrlInput" type="text" id="username" name="username" placeholder="请输入你的账号">
    </div>
    <div class="row">
        <div><label  class="ctrlLabel icon-lock"></label></div>
        <input class="ctrlInput" type="password" id="password" name="password" placeholder="请输入你的密码"/>
    </div>
    <div class="row">
        <input type="checkbox" checked="checked" id="rememberMe" class="ctrlRemember" value="记住我">记住我
    </div>
    <div class="row">
        <input type="submit" class="ctrlBtn" value="登录">
    </div>
     <c:if test="${!empty error}">
        <label class="error tCenter">用户名或者密码错误</label>
    </c:if>
</form>
</body>

<script src="resources/js/lib/jquery-2.0.3.min.js"></script>
<script src="resources/js/lib/jquery.form.js"></script>
<script src="resources/js/lib/jquery.validate.min.js"></script>
<script src="resources/js/src/config.js"></script>
<script src="resources/js/src/functions.js"></script>
<script src="resources/js/src/login.js"></script>
</html>