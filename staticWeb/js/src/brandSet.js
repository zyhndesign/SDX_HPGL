var brandSet=(function(config,functions){

    var currentBrandId;

    /**
     * 创建datatable
     * @returns {*|jQuery}
     */
    function createTable(){

        var ownTable=$("#myTable").dataTable({
            "bServerSide": true,
            "sAjaxSource": config.ajaxUrls.brandGetAll,
            "bInfo":true,
            "bLengthChange": false,
            "bFilter": false,
            "bSort":false,
            "bAutoWidth": false,
            "iDisplayLength":config.perLoadCounts.table,
            "sPaginationType":"full_numbers",
            "oLanguage": {
                "sUrl":config.dataTable.langUrl
            },
            "aoColumns": [
                { "mDataProp": "more",
                    "fnRender":function(oObj) {
                        return '<span class="detailController glyphicon glyphicon-plus"></span>';
                    }
                },
                { "mDataProp": "name"},
                { "mDataProp": "opt",
                    "fnRender":function(oObj){
                        return '<a href="'+oObj.aData.id+'" class="addSub">添加</a>&nbsp;' +
                            '<a href="'+oObj.aData.id+'" class="delete">删除</a>';
                    }
                }
            ] ,
            "fnServerParams": function ( aoData ) {
                aoData.push({
                    name:"name",
                    value:""
                })
            },
            "fnServerData": function(sSource, aoData, fnCallback) {

                //回调函数
                $.ajax({
                    "dataType":'json',
                    "type":"get",
                    "url":sSource,
                    "data":aoData,
                    "success": function (response) {
                        if(response.success===false){
                            functions.ajaxReturnErrorHandler(response.error_code);
                        }else{
                            var json = {
                                "sEcho" : response.sEcho
                            };

                            for (var i = 0, iLen = response.aaData.length; i < iLen; i++) {
                                response.aaData[i].more="more";
                                response.aaData[i].opt="opt";
                            }

                            json.aaData=response.aaData;
                            json.iTotalRecords = response.iTotalRecords;
                            json.iTotalDisplayRecords = response.iTotalDisplayRecords;
                            fnCallback(json);
                        }

                    }
                });
            },
            "fnFormatNumber":function(iIn){
                return iIn;
            }
        });

        return ownTable;
    }

    return {
        ownTable:null,
        createTable:function(){
            this.ownTable=createTable();
        },
        tableRedraw:function(){
            this.ownTable.fnSettings()._iDisplayStart=0;
            this.ownTable.fnDraw();
        },
        delete:function(id){
            functions.showLoading();
            var me=this;
            $.ajax({
                url:config.ajaxUrls.brandDelete.replace(":id",id),
                type:"post",
                dataType:"json",
                success:function(response){
                    if(response.success){
                        functions.hideLoading();
                        $().toastmessage("showSuccessToast",config.messages.optSuccess);
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
        showDetail:function(el){
            var me=this,
                tr = el.closest('tr')[0],
                id=currentBrandId=this.ownTable.fnGetData(tr)["id"];

            if ( this.ownTable.fnIsOpen(tr) ){
                el.removeClass('shown glyphicon-minus').addClass("glyphicon-plus");
                this.ownTable.fnClose( tr );
            }else{
                functions.showLoading();


                $.ajax({
                    url:config.ajaxUrls.brandGetAll.replace(":id",id),
                    type:"get",
                    dataType:"json",
                    success:function(response){
                        if(response.success){
                            el.addClass('shown glyphicon-minus').removeClass("glyphicon-plus");
                            me.ownTable.fnOpen( tr, me.detailContent(response.aaData), 'details' );

                            functions.hideLoading();
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
        detailContent:function(records){
            var string='<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;width:100%">';

            //for循环添加tr
            for(var i= 0,length=records.length;i<length;i++){
                string+="<tr><td>"+records[i].name+"</td>"+
                    "<td><a href='"+records[i].id+"' class='deleteChild'>删除</a></td>";
            }

            return string+"</table>";
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
        addSub:function(id){
            $("#addSubForm").attr("action",function(index,text){
                text=$(this).data("action");
                return  text.replace(":id",id);
            });
            $("#addSubModal").modal("show");
        },
        addSubFormSubmit:function(form){
            var me=this;
            functions.showLoading();
            $(form).ajaxSubmit({
                dataType:"json",
                success:function(response){
                    if(response.success){
                        $().toastmessage("showSuccessToast",config.messages.optSuccess);
                        functions.hideLoading();
                        me.ownTable.fnDraw();
                        $(form)[0].reset();
                        $("#addSubModal").modal("hide");
                    }else{
                        functions.ajaxReturnErrorHandler(response.error_code);
                    }
                },
                error:function(){
                    functions.ajaxErrorHandler();
                }
            });
        },
        deleteChild:function(){
            functions.showLoading();
            var me=this;
            $.ajax({
                url:config.ajaxUrls.brandDelete.replace(":id",id),
                type:"post",
                dataType:"json",
                success:function(response){
                    if(response.success){
                        functions.hideLoading();
                        $().toastmessage("showSuccessToast",config.messages.optSuccess);
                        me.ownTable.fnDraw();
                    }else{
                        functions.ajaxReturnErrorHandler(response.error_code);
                    }

                },
                error:function(){
                    functions.ajaxErrorHandler();
                }
            });
        }
    }
})(config,functions);

$(document).ready(function(){

    brandSet.createTable();

    $("#myTable").on("click","a.delete",function(){
        if(confirm(config.messages.confirmDelete)){
            brandSet.delete($(this).attr("href"));
        }
        return false;
    }).on("click","a.addSub",function(){
            brandSet.addSub($(this).attr("href"));
            return false;
        }).on("click",".detailController",function(){
            brandSet.showDetail($(this));
        }).on("click","a.deleteChild",function(){
            brandSet.deleteChild($(this).attr("href"));
            return false;
        });

    $("#myForm").validate({
        ignore:[],
        rules:{
            name:{
                required:true,
                maxlength:32
            }
        },
        messages:{
            name:{
                required:config.validErrors.required,
                maxlength:config.validErrors.maxLength.replace("${max}",32)
            }
        },
        submitHandler:function(form) {
            brandSet.submitForm(form);
        }
    });

    $("#addSubForm").validate({
        ignore:[],
        rules:{
            name:{
                required:true,
                maxlength:50
            }
        },
        messages:{
            name:{
                required:config.validErrors.required,
                maxlength:config.validErrors.maxLength.replace("${max}",50)
            }
        },
        submitHandler:function(form) {
            brandSet.addSubFormSubmit(form);
        }
    });
});

