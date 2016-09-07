<%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html>
    <head>
        <%@ include file="head.jsp"%>
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
                <h1 class="panel-title">货品管理</h1>
            </div>
            <div class="panel-body" id="opt-body">
                
                <!--<a class="btn btn-success" href="productCOU.html">
                    <span class="glyphicon glyphicon-save"></span> 导入
                </a>-->
                <div class="row">
                        <a class="btn btn-primary newBtn" href="hpManage/productCOU">
                            <span class="glyphicon glyphicon-plus"></span> 新建
                        </a>
                        <div class="input-group tableSearchContainer col-md-6">
                            <input class="form-control" id="searchNo" type="text" placeholder="货号">
                            <span class="input-group-btn">
                                <button id="searchBtn" class="btn btn-default searchBtn" type="button">搜索</button>
                            </span>
                        </div>
                        <!--<div class="col-md-2">
                            <select class="form-control" id="searchStatus">
                                <option value="">全部</option>
                            </select>
                        </div>-->
                    </div>
                <div id="searchPanel" class="searchPanel">
                   
                    
                    <div class="row">
                        <label class="control-label col-md-1">品牌</label>
                        <div class="col-md-11">
                            <c:forEach var="b" items="${brand}">
                               <span class="item" data-type="brand" data-id="${b.id}">${b.name}</span>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="row">
                        <label class="control-label col-md-1">品类</label>
                        <div class="col-md-11">
                           <c:forEach var="c" items="${category}">
                              <span class="item" data-type="category" data-id="${c.id}">${c.name}</span>
                           </c:forEach>
                        </div>
                    </div>

                    <div class="row">
                        <label class="control-label col-md-1">颜色</label>
                        <div class="col-md-11">
                            <c:forEach var="cl" items="${color}">
                              <span class="item" data-type="color" data-id="${cl.id}">${cl.name}</span>
                           </c:forEach>
                        </div>
                    </div>
                    <div class="row">
                        <label class="control-label col-md-1">尺码</label>
                        <div class="col-md-11">
                            <c:forEach var="s" items="${size}">
                              <span class="item" data-type="size" data-id="${s.id}">${s.name}</span>
                           </c:forEach>
                        </div>
                    </div>
                    <div class="collapseBtnCtrl" id="searchPanelCtrl" data-target="down">
                        <span class="glyphicon glyphicon-chevron-up"></span><span class="text">收起选项</span>
                    </div>
                </div>

                <table id="myTable" class="dataTable">
                    <thead>
                    <tr>
                        <th>货号</th>
                        <th>品牌</th>
                        <th>品类</th>
                        <th>时间</th>
                        <th>尺码</th>
                        <th>颜色</th>
                        <th>吊牌价</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>


<%@ include file="loading.jsp"%>

<script src="resources/js/lib/jquery-2.0.3.min.js"></script>
<script src="resources/js/lib/jquery.dataTables.min.js"></script>
<script src="resources/js/lib/bootstrap.min.js"></script>
<script src="resources/js/lib/jquery.toastmessage.js"></script>
<script src="resources/js/src/config.js"></script>
<script src="resources/js/src/functions.js"></script>
<script src="resources/js/src/productMgr.js"></script>
</body>
</html>