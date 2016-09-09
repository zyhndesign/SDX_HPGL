<%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <!DOCTYPE html>
    <html>
    <head>
    <%@ include file="head.jsp"%>
    <script>
        var id="${hp.id}";
        var preBrand="${hp.brand}";
        var preCategory="${hp.category}";
        var preSize="${hp.size}";
        var preColor="${hp.color}";
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
                            <label  class="control-label col-md-3">货号*</label>
                            <div class="col-md-9 input-group">
                                <input type="text" class="form-control" value="${hp.hp_num}" name="hp_num">
                            </div>
                        </div>
                        <div class="form-group">
                            <label  class="control-label col-md-3">品牌*</label>
                            <div class="col-md-9 input-group">
                                <input type="text" id="brand" class="form-control" value="${hp.brandList}">
                                <input type="hidden" id="brandId" name="brand" value="${hp.brand}">
                                <c:if test="${!empty hp.id}">
                                    <input type="hidden" id="brandOld" value="${hp.brand}">
                                </c:if>
                                <span class="input-group-btn">
                                <button type="button" class="btn btn-default showTreePanel" data-type="brand">
                                    <span class="sd-icon sd-icon-edit" aria-hidden="true"></span>
                                </button>
                                </span>
                            </div>
                            
                        </div>
                        <div class="form-group">
                            <label  class="control-label col-md-3">品类*</label>
                            <div class="col-md-9 input-group">
                                <input type="text" id="category" class="form-control" value="${hp.categoryList}">
                                <input type="hidden" id="categoryId" name="category" value="${hp.category}">
                                <c:if test="${!empty hp.id}">
                                    <input type="hidden" id="categoryOld" value="${hp.category}">
                                </c:if>
                                <span class="input-group-btn">
                                <button type="button" class="btn btn-default showTreePanel" data-type="category">
                                    <span class="sd-icon sd-icon-edit" aria-hidden="true"></span>
                                </button>
                            </span>
                            </div>
                            
                        </div>
                        <div class="form-group">
                            <label  class="control-label col-md-3">尺寸*</label>
                            <div class="col-md-9 input-group">
                                <input type="text" id="size" class="form-control" value="${hp.sizeList}">
                                <input type="hidden" id="sizeId" name="size" value="${hp.size}">
                                <c:if test="${!empty hp.id}">
                                    <input type="hidden" id="sizeOld" value="${hp.size}">
                                </c:if>
                                <span class="input-group-btn">
                                <button type="button" class="btn btn-default showTreePanel" data-type="size">
                                    <span class="sd-icon sd-icon-edit" aria-hidden="true"></span>
                                </button>
                            </span>
                            </div>
                            
                        </div>
                        <div class="form-group">
                            <label  class="control-label col-md-3">颜色*</label>
                            <div class="col-md-9 input-group">
                                <input type="text" id="color" class="form-control"  value="${hp.colorList}">
                                <input type="hidden" id="colorId" name="color" value="${hp.color}">
                                <c:if test="${!empty hp.id}">
                                    <input type="hidden" id="colorOld" value="${hp.color}">
                                </c:if>
                                <span class="input-group-btn">
                                <button type="button" class="btn btn-default showTreePanel" data-type="color">
                                    <span class="sd-icon sd-icon-edit" aria-hidden="true"></span>
                                </button>
                            </span>
                            </div>
                            
                        </div>
                        <div class="form-group">
                            <label  class="control-label col-md-3">吊牌价*</label>
                            <div class="col-md-9 input-group">
                                <input type="text" class="form-control" value="${hp.price}" name="price">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="control-label col-md-3">图片上传*</label>
                            <div class="col-md-9" id="uploadContainer">
                                <p class="help-block">1.配装正面图，png格式，1400x2800像素</p>
                                <a href="#" id="uploadBtn1">
                                    <c:if test="${empty hp.imageUrl1}">
                                    <img  id="image1" class="imgThumb"  style="width:100px"
                                    src="resources/images/app/defaultThumb.png"/>
                                    <input type="hidden" id="imageUrl1" name="imageUrl1">
                                    </c:if>

                                    <c:if test="${!empty hp.imageUrl1}">
                                    <img  id="image1" class="imgThumb"  style="width:100px"
                                    src="${hp.imageUrl1}"/>
                                    <input type="hidden" id="imageUrl1" value="${hp.imageUrl1}" name="imageUrl1">
                                    </c:if>
                                </a>
                                <p class="help-block">2.配装正面图，png格式，1400x2800像素</p>
                                <a href="#" id="uploadBtn2">
                                    <c:if test="${empty hp.imageUrl2}">
                                    <img  id="image2" class="imgThumb"  style="width:100px"
                                    src="resources/images/app/defaultThumb.png"/>
                                    <input type="hidden" id="imageUrl2" name="imageUrl2">
                                    </c:if>

                                    <c:if test="${!empty hp.imageUrl2}">
                                    <img  id="image2" class="imgThumb"  style="width:100px"
                                    src="${hp.imageUrl2}"/>
                                    <input type="hidden" id="imageUrl2" value="${hp.imageUrl2}" name="imageUrl2">
                                    </c:if>
                                </a>
                                <p class="help-block">3.缩略图，png/jpg格式，800x1200像素</p>
                                <a href="#" id="uploadBtn3">
                                    <c:if test="${empty hp.imageUrl3}">
                                    <img  id="image3" class="imgThumb"  style="width:100px"
                                    src="resources/images/app/defaultThumb.png"/>
                                    <input type="hidden" id="imageUrl3" name="imageUrl3">
                                    </c:if>

                                    <c:if test="${!empty hp.imageUrl3}">
                                    <img  id="image3" class="imgThumb"  style="width:100px"
                                    src="${hp.imageUrl3}"/>
                                    <input type="hidden" id="imageUrl3" value="${hp.imageUrl3}" name="imageUrl3">
                                    </c:if>
                                </a>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-12">
                           <hr>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary saveBtn">保存</button>
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
                <h4 class="modal-title">设置</h4>
            </div>
            <div class="modal-body">
                <ul id="treeDemo" class="ztree"></ul>
                <br><br>
                <button class="btn btn-primary saveBtn" id="saveTreeSelect">确认</button>
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