var brandCOU=(function(config,functions){
    var oldValue={
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
            if(oldValue[currentSetType].indexOf(childNodes[i].id)!=-1){
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
                enable:true
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
                        functions.hideLoading();
                        me.ownTable.fnDraw();
                    }else{
                        functions.ajaxReturnErrorHandler(response.error_code);
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
            var value=[],valueName=[];
            for(var i= 0,len=selects.length;i<len;i++){
                value.push(selects[i].id);
                valueName.push(selects[i].name);
            }
            oldValue[currentSetType]=value;
            $("#"+currentSetType).val(valueName.join("/"));
            $("#showTreeModal").modal("hide");

            /*functions.showLoading();
            $.ajax({
                url:config.ajaxUrls.productSet,
                type:"post",
                dataType:"json",
                data:{
                    oldValue:oldValue[currentSetType].join(","),
                    value:value.join(","),
                    type:currentSetType
                },
                success:function(response){
                    if(response.success){
                        $().toastmessage("showSuccessToast",config.messages.optSuccess);
                        functions.hideLoading();
                        oldValue[currentSetType]=value;
                        $("#"+type).val(valueName.join("/"));
                        $("#showTreeModal").modal("hide");
                    }else{
                        functions.ajaxReturnErrorHandler(response.error_code);
                    }
                },
                error:function(){
                    functions.ajaxErrorHandler();
                }
            });*/
        },
        createUploaders:function(){
            for(var i= 1;i<=3;i++){
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
                        if(info.w/info.h==4/3&&info.w>=400&&info.w<=800){
                            $("#imageUrl"+i).val(info.url);

                            $("#image"+i).attr("src",info.url);

                            $(".error[for='imageUrl'"+i+")]").remove();
                        }else{
                            $().toastmessage("showErrorToast",config.messages.imageSizeError);
                        }
                    }
                });
            }
        }
    }
})(config,functions);

$(document).ready(function(){

    brandCOU.createUploaders();

    $(".showTreePanel").click(function(){
        brandCOU.showTree($(this).data("type"));
    });
    $("#saveTreeSelect").click(function(){
        brandCOU.saveTreeSelect();
    });

    $("#myForm").validate({
        ignore:[],
        rules:{
            no:{
                required:true,
                maxlength:32
            },
            "thumb[]":{
                required:true
            },
            price:{
                required:true
            }
        },
        messages:{
            no:{
                required:config.validErrors.required,
                maxlength:config.validErrors.maxLength.replace("${max}",32)
            },
            "thumb[]":{
                required:config.validErrors.required
            },
            price:{
                required:config.validErrors.required
            }
        },
        submitHandler:function(form) {
            brandCOU.submitForm(form);
        }
    });
});