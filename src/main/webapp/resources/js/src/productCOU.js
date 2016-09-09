var productCOU=(function(config,functions){
    var preValue={
        brand:[],
        category:[],
        date:[],
        size:[],
        color:[]
    };
    var currentSetType="brand";

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
            }
            //设置checkbox的选中状态
            if(preValue[currentSetType].indexOf(childNodes[i].id)!=-1){
                childNodes[i].checked=true;
            }

            childNodes[i].isParent=true;
            childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        }
        return childNodes;
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
                radioType:"all"
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
                zTree.destroy();
            }

            $.fn.zTree.init($("#treeDemo"), me.setting);

            $("#showTreeModal").modal("show");
        },
        saveTreeSelect:function(){
            var me=this;
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            var selects = zTree.getCheckedNodes();
            var valueArray=[],valueNameArray=[];

            for(var i= 1,len=selects[0].level;i<len;i++){
                selects.unshift(selects[0].getParentNode());
            }

            for(var j= 0,length=selects.length;j<length;j++){
                valueArray.push(selects[j].id);
                valueNameArray.push(selects[j].name);
            }

            if(!id){
                //如果是在录入页面，直接设置值即可
                preValue[currentSetType]=valueArray.join(",");
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
                            preValue[currentSetType]=valueArray.join(",");
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
            preValue.brand=preBrand;
            preValue.category=preCategory;
            preValue.size=preSize;
            preValue.color=preColor;
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