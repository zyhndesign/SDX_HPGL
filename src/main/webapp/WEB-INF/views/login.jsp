<%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html>
    <head>
    <%@ include file="head.jsp"%>

    <link type="text/css" rel="stylesheet" href="resources/css/src/login.css">
</head>
<body>

<div class="header">
    <h1 class="logo">
        <a href="#">
            圣德西货品管理
        </a>
    </h1>
</div>

<form class="pCenter" id="myForm" method="post" action="login">
    <h2 class="title">系统登陆</h2>
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
        <input type="submit" class="ctrlBtn" value="登陆">
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