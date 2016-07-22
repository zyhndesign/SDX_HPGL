var brandSet=(function(config,functions){
    function remove(id,pId){
        functions.showLoading();
        var me=this;
        $.ajax({
            url:config.ajaxUrls.brandDelete.replace(":id",id),
            type:"post",
            data:{
                pId:pId,
                id:id
            },
            dataType:"json",
            success:function(response){
                if(response.success){
                    functions.hideLoading();
                    $().toastmessage("showSuccessToast",config.messages.optSuccess);
                }else{
                    functions.ajaxReturnErrorHandler(response.error_code);
                }

            },
            error:function(){
                functions.ajaxErrorHandler();
            }
        });
    }
    function add(treeNode){
        functions.showLoading();
        var me=this;
        $.ajax({
            url:config.ajaxUrls.brandAdd,
            type:"post",
            data:{
                name:"新系列",
                pId:treeNode.id
            },
            dataType:"json",
            success:function(response){
                if(response.success){
                    functions.hideLoading();
                    $().toastmessage("showSuccessToast",config.messages.optSuccess);

                    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                    zTree.addNodes(treeNode, {id:Math.random(response.id), pId:treeNode.id, name:"新系列" +response.id});
                }else{
                    functions.ajaxReturnErrorHandler(response.error_code);
                }

            },
            error:function(){
                functions.ajaxErrorHandler();
            }
        });
    }
    function rename(id,pId,name){
        functions.showLoading();
        var me=this;
        $.ajax({
            url:config.ajaxUrls.brandAdd,
            type:"post",
            data:{
                name:name,
                pId:pId,
                id:id
            },
            dataType:"json",
            success:function(response){
                if(response.success){
                    functions.hideLoading();
                    $().toastmessage("showSuccessToast",config.messages.optSuccess);
                }else{
                    functions.ajaxReturnErrorHandler(response.error_code);
                }

            },
            error:function(){
                functions.ajaxErrorHandler();
            }
        });
    }

    function filter(treeId, parentNode, response) {
        if(!response.success){
            $().toastmessage("showSuccessToast",config.messages.loadDataError);
            return [];
        }

        //转换数据
        var childNodes=response.results;



        if (!childNodes) return null;
        for (var i=0, l=childNodes.length; i<l; i++) {
            childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        }
        return childNodes;
    }
    function beforeRemove(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.selectNode(treeNode);
        return confirm("确认删除 -- " + treeNode.name + " 吗？");
    }
    function onRemove(e, treeId, treeNode) {

        //这里讲数据发给后台
        remove(treeNode.id,treeNode.pId);
    }
    function beforeRename(treeId, treeNode, newName) {
        if (newName.length == 0) {
            $().toastmessage("showSuccessToast",config.validErrors.required);
            return false;
        }
        return true;
    }
    function onRename(e,treeId,treeNode){
        rename(treeNode.id,treeNode.pId,treeNode.name);
    }

    function addHoverDom(treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
            + "' title='add' onfocus='this.blur();'></span>";
        sObj.after(addStr);
        var btn = $("#addBtn_"+treeNode.tId);
        if (btn) btn.bind("click", function(){

            //这里讲数据发送给后台
            add(treeNode);
            return false;
        });
    }
    function removeHoverDom(treeId, treeNode) {
        $("#addBtn_"+treeNode.tId).unbind().remove();
    }

    return {
        setting : {
            async: {
                enable: true,
                url:config.ajaxUrls.brandGetAll,
                //autoParam:["id", "name=n", "level=lv"],
                autoParam:["id"],
                //otherParam:{otherParam:"zTreeAsyncTest"},
                dataFilter: filter
            },
            view: {
                expandSpeed:"",
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            edit: {
                enable: true
            },
            callback: {
                beforeRemove: beforeRemove,
                onRemove:onRemove,
                onRename:onRename,
                beforeRename: beforeRename
            }
        }
    }
})(config,functions);

$(document).ready(function(){

    $.fn.zTree.init($("#treeDemo"), brandSet.setting);

});

