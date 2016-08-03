var categoryMgr=(function(config,functions){
    function remove(id,pId){
        functions.showLoading();
        var me=this;
        $.ajax({
            url:config.ajaxUrls.categoryDelete.replace(":id",id),
            type:"post",
            data:{
                parentId:pId,
                id:id
            },
            dataType:"json",
            success:function(response){
                if(response.success){
                    functions.hideLoading();
                    $().toastmessage("showSuccessToast",config.messages.optSuccess);
                }else{
                    functions.ajaxReturnErrorHandler(response.message);
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
        var no=(new Date()).getTime();
        $.ajax({
            url:config.ajaxUrls.categoryAdd,
            type:"post",
            data:{
                name:"新品类"+no,
                id:treeNode.id
            },
            dataType:"json",
            success:function(response){
                if(response.success){
                    functions.hideLoading();
                    $().toastmessage("showSuccessToast",config.messages.optSuccess);

                    var zTree = $.fn.zTree.getZTreeObj("treeDemo");

                    if(treeNode.check_Child_State!=-1){
                        zTree.addNodes(treeNode, {id:response.object, pId:treeNode.id, name:"新品类" +no,isParent:true});
                    }else{
                        zTree.expandNode(treeNode);
                    }

                }else{
                    functions.ajaxReturnErrorHandler(response.message);
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
            url:config.ajaxUrls.categoryUpdate,
            type:"post",
            data:{
                name:name,
                parentId:pId,
                id:id
            },
            dataType:"json",
            success:function(response){
                if(response.success){
                    functions.hideLoading();
                    $().toastmessage("showSuccessToast",config.messages.optSuccess);
                }else{
                    functions.ajaxReturnErrorHandler(response.message);
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
        var childNodes=response.object;

        if (!childNodes) return null;
        for (var i=0, l=childNodes.length; i<l; i++) {
            childNodes[i].isParent=true;
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
                type:"GET",
                url:config.ajaxUrls.categoryGetAll,
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
            edit: {
                enable: true,
                editNameSelectAll: true
            },
            callback: {
                beforeRemove: beforeRemove,
                onRemove:onRemove,
                onRename:onRename,
                beforeRename: beforeRename,
                beforeDrag: function(treeId, treeNodes){
                    return false;
                }
            }
        }
    }
})(config,functions);

$(document).ready(function(){

    $.fn.zTree.init($("#treeDemo"), categoryMgr.setting);

});

