var productCOU=(function(config,functions){
    var preValue={
        brand:[],
        category:[],
        date:[],
        size:[],
        color:[]
    };
    var currentSetType="brand";
    var loadCount=0;

    function filter(treeId, parentNode, response) {
        if(!response.success){
            $().toastmessage("showSuccessToast",config.messages.loadDataError);
            return [];
        }

        //转换数据
        var childNodes=response.object;

        if (!childNodes) return null;
        for (var i=0, l=childNodes.length; i<l; i++) {

            //根节点不需要checkbox
            if(childNodes[i].id==0){
                childNodes[i].nocheck=true;
                childNodes[i].checked=false;
            }else{

                //设置checkbox的选中状态
                if(preValue[currentSetType].indexOf(String(childNodes[i].id))!=-1||
                    preValue[currentSetType].indexOf(childNodes[i].id)!=-1){
                    childNodes[i].checked=true;
                }
            }


            childNodes[i].isParent=true;
            childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        }
        return childNodes;
    }

    function asyncNodes(nodes) {
        if (!nodes) return;
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        for (var i=0, l=nodes.length; i<l; i++) {
            zTree.reAsyncChildNodes(nodes[i], "refresh", true);
        }
    }
    function beforeAsync() {
        loadCount++;
    }
    function onAsyncSuccess(event, treeId, treeNode, msg){
        var zTree, nodes;

        loadCount--;

        if(!treeNode){
            zTree=$.fn.zTree.getZTreeObj("treeDemo");
            nodes=zTree.getNodes();
        }else{
            nodes=treeNode.children;
        }

        asyncNodes(nodes);

        if(loadCount<=0){
            zTree=$.fn.zTree.getZTreeObj("treeDemo");
            nodes=zTree.getNodes();//这样获取出来的只有根节点
            zTree.expandNode(nodes[0], true, false, true,true);

            functions.hideLoading();
        }
    }
    function onClick(event, treeId, treeNode){
        var zTree=$.fn.zTree.getZTreeObj("treeDemo");
        var checkeds=zTree.getCheckedNodes(true);

        for(var i= 0,len=checkeds.length;i<len;i++){
            zTree.checkNode(checkeds[i],false,true);
        }
        zTree.checkNode(treeNode, true, true,true);
    }
    function onCheck(event, treeId, treeNode){
        var zTree=$.fn.zTree.getZTreeObj("treeDemo");

        zTree.checkNode(treeNode.getParentNode(),true, true,true);

    }
    function onExpand(event, treeId, treeNode){
        var zTree=$.fn.zTree.getZTreeObj("treeDemo");
        var node=zTree.getNodesByFilter(function(node){
            return node.checked;
        },true,treeNode);
        zTree.expandNode(node, true, false, true,true);
    }
    return {
        setting : {
            async: {
                enable: true,
                type:"GET",
                url:config.ajaxUrls.brandGetAll,
                //autoParam:["id", "name=n", "level=lv"],
                autoParam:["id"],
                //otherParam:{otherParam:"zTreeAsyncTest"},
                dataFilter: filter
            },
            check:{
                enable:true,
                chkStyle:"radio",
                radioType:"level"
            },
            view:{
                dblClickExpand:false,
                selectedMulti:false
            },
            data: {
                keep:{
                    parent:true
                },
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: 0
                }
            },
            callback: {
                beforeAsync: beforeAsync,
                onAsyncSuccess: onAsyncSuccess,
                onClick:onClick,
                onExpand:onExpand,
                onCheck:onCheck
            }
        },
        submitForm:function(form){
            var me=this;
            functions.showLoading();
            $(form).ajaxSubmit({
                dataType:"json",
                success:function(response){
                    if(response.success){
                        $().toastmessage("showSuccessToast",config.messages.optSuccess);
                        setTimeout(function(){
                            window.location.href="hpManage/productMgr";
                        },3000);
                    }else{
                        functions.ajaxReturnErrorHandler(response.message);
                    }
                },
                error:function(){
                    functions.ajaxErrorHandler();
                }
            });
        },
        showTree:function(type){
            var me=this;
            var zTree=$.fn.zTree.getZTreeObj("treeDemo");

            currentSetType=type;
            me.setting.async.url=config.ajaxUrls[type+"GetAll"];

            if(zTree){
                loadCount=0;
                zTree.destroy();
            }

            $.fn.zTree.init($("#treeDemo"), me.setting);

            $("#showTreeModal").modal("show");

            functions.showLoading();
        },
        saveTreeSelect:function(){
            var me=this;
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            var selects = zTree.getCheckedNodes(true);
            var valueArray=[],valueNameArray=[];

            for(var j= 0,length=selects.length;j<length;j++){
                valueArray.push(selects[j].id);
                valueNameArray.push(selects[j].name);
            }

            if(!id){
                //如果是在录入页面，直接设置值即可
                preValue[currentSetType]=valueArray;
                $("#"+currentSetType).val(valueNameArray.join("/"));
                $("#"+currentSetType+"Id").val(valueArray.join(","));
                $("#showTreeModal").modal("hide");
            }else{
                functions.showLoading();
                $.ajax({
                    url:config.ajaxUrls.productTagSet,
                    type:"post",
                    dataType:"json",
                    data:{
                        hp_id:id,
                        pre_id:$("#"+currentSetType+"Old").val(),
                        new_id:valueArray.join(","),
                        type:currentSetType
                    },
                    success:function(response){
                        if(response.success){
                            $().toastmessage("showSuccessToast",config.messages.optSuccess);
                            functions.hideLoading();
                            preValue[currentSetType]=valueArray;
                            $("#"+currentSetType).val(valueNameArray.join("/"));
                            $("#showTreeModal").modal("hide");
                        }else{
                            functions.ajaxReturnErrorHandler(response.error_code);
                        }
                    },
                    error:function(){
                        functions.ajaxErrorHandler();
                    }
                });
            }
        },
        createUploaders:function(){
            for(var i= 1;i<=3;i++){

                //使用立即执行，将i作为参数传入，不然受闭包影响，i总是4
                (function(i){
                    functions.createQiNiuUploader({
                        maxSize:config.uploader.sizes.img,
                        filter:config.uploader.filters.img,
                        uploadBtn:"uploadBtn"+i,
                        multiSelection:false,
                        multipartParams:null,
                        uploadContainer:"uploadContainer",
                        fileAddCb:null,
                        progressCb:null,
                        uploadedCb:function(info,file,up){
                            if(i==3){
                                if(info.w!=800&&info.h!=1200){
                                    $().toastmessage("showErrorToast",config.messages.imageSizeError);
                                    return ;
                                }
                            }else{
                                if(info.w!=1400&&info.h!=2800){
                                    $().toastmessage("showErrorToast",config.messages.imageSizeError);
                                    return ;
                                }
                            }
                            $("#imageUrl"+i).val(info.url);

                            $("#image"+i).attr("src",info.url);

                            $(".error[for='imageUrl"+i+"']").remove();
                        }
                    });
                })(i);

            }
        },
        initValues:function(){
            preValue.brand=preBrand.split(",");
            preValue.category=preCategory.split(",");
            preValue.size=preSize.split(",");
            preValue.color=preColor.split(",");
        }
    }
})(config,functions);

$(document).ready(function(){
    if(id){
        productCOU.initValues();
    }

    productCOU.createUploaders();

    $(".showTreePanel").click(function(){
        productCOU.showTree($(this).data("type"));
    });
    $("#saveTreeSelect").click(function(){
        productCOU.saveTreeSelect();
    });

    $("#myForm").validate({
        ignore:[],
        rules:{
            hp_num:{
                required:true,
                maxlength:32
            },
            brand:{
                required:true
            },
            category:{
                required:true
            },
            size:{
                required:true
            },
            color:{
                required:true
            },
            price:{
                required:true
            }
        },
        messages:{
            hp_num:{
                required:config.validErrors.required,
                maxlength:config.validErrors.maxLength.replace("${max}",32)
            },
            brand:{
                required:config.validErrors.required
            },
            category:{
                required:config.validErrors.required
            },
            size:{
                required:config.validErrors.required
            },
            color:{
                required:config.validErrors.required
            },
            price:{
                required:config.validErrors.required
            }
        },
        submitHandler:function(form) {
            productCOU.submitForm(form);
        }
    });
});