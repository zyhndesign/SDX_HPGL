<%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html>
    <head>
    <%@ include file="head.jsp"%>

    <link href="css/lib/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="css/lib/zTreeStyle.css" rel="stylesheet" type="text/css">
    <link href="css/lib/jquery.toastmessage.css" rel="stylesheet" type="text/css">
    <link href="css/src/main.css" rel="stylesheet" type="text/css">
</head>
<body>

<%@ include file="header.jsp"%>

<div class="left">
    <%@ include file="menu.jsp"%>
</div>

<div class="right">
    <div class="main">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h1 class="panel-title">品类设置</h1>
            </div>
            <div class="panel-body" id="opt-body">
                <ul id="treeDemo" class="ztree"></ul>
            </div>
        </div>
    </div>
</div>


<%@ include file="loading.jsp"%>

<script src="js/lib/jquery-2.0.3.min.js"></script>
<script src="js/lib/jquery.ztree.all.min.js"></script>
<script src="js/lib/bootstrap.min.js"></script>
<script src="js/lib/jquery.toastmessage.js"></script>
<script src="js/src/config.js"></script>
<script src="js/src/functions.js"></script>
<script src="js/src/categoryMgr.js"></script>
</body>
</html>