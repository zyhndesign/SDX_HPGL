<%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html>
    <head>
    <%@ include file="head.jsp"%>


    <link href="resources/css/lib/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="resources/css/lib/zTreeStyle.css" rel="stylesheet" type="text/css">
    <link href="resources/css/lib/jquery.toastmessage.css" rel="stylesheet" type="text/css">
    <link href="resources/css/src/main.css" rel="stylesheet" type="text/css">
    <script>
        var id="${hp.id}";
    </script>
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
                <h1 class="panel-title">货品录入/修改</h1>
            </div>
            <div class="panel-body" id="opt-body">

                <c:choose>
                    <c:when test="${empty hp}">
                    <form class="form-horizontal" id="myForm" action="hpManage/insert" method="post">
                    </c:when>
                    <c:otherwise>
                    <form class="form-horizontal" id="myForm" action="hpManage/update" method="post">
                    <input type="hidden" name="id" value="${hp.id}">
                    </c:otherwise>
                </c:choose>

                    <div class="col-md-6">
                        <div class="form-group">
                            <label  class="control-label col-md-2">货号*</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" value="${hp.hp_num}" name="hp_num">
                            </div>
                        </div>
                        <div class="form-group">
                            <label  class="control-label col-md-2">品牌*</label>
                            <div class="col-md-6">
                                <input type="text" id="brand" name="brand" class="form-control" value="${hp.brandList}">
                                <c:if test="${empty hp.id}">
                                    <input type="hidden" id="brandOld" value="${hp.brand}">
                                </c:if>
                            </div>
                            <div class="col-md-2">
                                <button type="button" class="btn btn-default showTreePanel" data-type="brand">
                                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                </button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label  class="control-label col-md-2">品类*</label>
                            <div class="col-md-6">
                                <input type="text" id="category" class="form-control" value="${hp.categoryList}">
                                <c:if test="${empty hp.id}">
                                    <input type="hidden" id="categoryOld" value="${hp.category}">
                                </c:if>
                            </div>
                            <div class="col-md-2">
                                <button type="button" class="btn btn-default showTreePanel" data-type="category">
                                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                </button>
                            </div>
                        </div>
                        <!--<div class="form-group">
                            <label  class="control-label col-md-2">时间*</label>
                            <div class="col-md-6">
                                <input type="text" id="date" class="form-control" value="">
                                <input type="hidden" id="dateOld" value="">
                            </div>
                            <div class="col-md-2">
                                <button type="button" class="btn btn-default showTreePanel" data-type="date">
                                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                </button>
                            </div>
                        </div>-->
                        <div class="form-group">
                            <label  class="control-label col-md-2">尺寸*</label>
                            <div class="col-md-6">
                                <input type="text" id="size" class="form-control" value="${hp.sizeList}">
                                <c:if test="${empty hp.id}">
                                    <input type="hidden" id="sizeOld" value="${hp.size}">
                                </c:if>
                            </div>
                            <div class="col-md-2">
                                <button type="button" class="btn btn-default showTreePanel" data-type="size">
                                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                </button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label  class="control-label col-md-2">颜色*</label>
                            <div class="col-md-6">
                                <input type="text" id="color" class="form-control"  value="${hp.colorList}">
                                <c:if test="${empty hp.id}">
                                    <input type="hidden" id="colorOld" value="${hp.color}">
                                </c:if>
                            </div>
                            <div class="col-md-2">
                                <button type="button" class="btn btn-default showTreePanel" data-type="color">
                                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                </button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label  class="control-label col-md-2">吊牌价*</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" value="${hp.price}" name="price">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="control-label col-md-2">图*</label>
                            <div class="col-md-10" id="uploadContainer">
                                <p class="help-block">请上传4:3的jpg，png，宽度400px-800px</p>
                                <a href="#" class="btn btn-success" id="uploadBtn1">
                                    <c:if test="${empty hp.imageUrl1}">
                                    <img  id="image1"  style="width:100px"
                                    src="resources/images/app/defaultThumb.jpg"/>
                                    <input type="hidden" id="imageUrl1" name="imageUrl1">
                                    </c:if>

                                    <c:if test="${!empty hp.imageUrl1}">
                                    <img  id="image1"  style="width:100px"
                                    src="${hp.imageUrl1}"/>
                                    <input type="hidden" id="imageUrl1" value="${hp.imageUrl1}" name="imageUrl1">
                                    </c:if>
                                </a>
                                <a href="#" class="btn btn-success" id="uploadBtn2">
                                    <c:if test="${empty hp.imageUrl2}">
                                    <img  id="image2"  style="width:100px"
                                    src="resources/images/app/defaultThumb.jpg"/>
                                    <input type="hidden" id="imageUrl2" name="imageUrl2">
                                    </c:if>

                                    <c:if test="${!empty hp.imageUrl2}">
                                    <img  id="image2"  style="width:100px"
                                    src="${hp.imageUrl2}"/>
                                    <input type="hidden" id="imageUrl2" value="${hp.imageUrl2}" name="imageUrl2">
                                    </c:if>
                                </a>
                                <a href="#" class="btn btn-success" id="uploadBtn3">
                                    <c:if test="${empty hp.imageUrl3}">
                                    <img  id="image3"  style="width:100px"
                                    src="resources/images/app/defaultThumb.jpg"/>
                                    <input type="hidden" id="imageUrl3" name="imageUrl3">
                                    </c:if>

                                    <c:if test="${!empty hp.imageUrl3}">
                                    <img  id="image3"  style="width:100px"
                                    src="${hp.imageUrl3}"/>
                                    <input type="hidden" id="imageUrl3" value="${hp.imageUrl3}" name="imageUrl3">
                                    </c:if>
                                </a>

                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-2 col-md-8">
                                <button type="submit" class="btn btn-success form-control">保存</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!--弹出界面-->
<div class="modal fade" id="showTreeModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">设置xx</h4>
            </div>
            <div class="modal-body">
                <ul id="treeDemo" class="ztree"></ul>
                <br><br>
                <button class="btn btn-success" id="saveTreeSelect">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<%@ include file="loading.jsp"%>

<script src="resources/js/lib/jquery-2.0.3.min.js"></script>
<script src="resources/js/lib/jquery.ztree.all.min.js"></script>
<script src="resources/js/lib/bootstrap.min.js"></script>
<script src="resources/js/lib/plupload.full.min.js"></script>
<script src="resources/js/lib/qiniu.js"></script>
<script src="resources/js/lib/jquery.form.js"></script>
<script src="resources/js/lib/jquery.validate.min.js"></script>
<script src="resources/js/lib/jquery.toastmessage.js"></script>
<script src="resources/js/src/config.js"></script>
<script src="resources/js/src/functions.js"></script>
<script src="resources/js/src/productCOU.js"></script>
</body>
</html>