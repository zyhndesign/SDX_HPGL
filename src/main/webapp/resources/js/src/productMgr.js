var productMgr=(function(config,functions){

    var searchParams={
        brand:[],
        category:[],
        date:[],
        color:[],
        size:[]
    };

    /**
     * 创建datatable
     * @returns {*|jQuery}
     */
    function createTable(){

        var ownTable=$("#myTable").dataTable({
            "bServerSide": true,
            "sAjaxSource": config.ajaxUrls.productGetAll,
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
                { "mDataProp": "hp_num"},
                { "mDataProp": "brandList"},
                { "mDataProp": "categoryList"},
                { "mDataProp": "timeCategory"},
                { "mDataProp": "sizeList"},
                { "mDataProp": "colorList"},
                { "mDataProp": "price",
                    "fnRender":function(oObj){
                        return oObj.aData.price?oObj.aData.price:"无";
                    }},
                { "mDataProp": "opt",
                    "fnRender":function(oObj){
                        return '<a href="hpManage/productCOU/'+oObj.aData.id+'">查看</a>&nbsp;';
                            //'<a href="'+oObj.aData.id+'" class="remove">删除</a>';
                    }
                }
            ] ,
            "fnServerParams": function ( aoData ) {
                aoData.push({
                    name:"hp_num",
                    value:$("#searchNo").val()
                },{
                    name:"status",
                    value:$("#searchStatus").val()
                },{
                    name:"brand",
                    value:searchParams.brand.join(",")
                },{
                    name:"category",
                    value:searchParams.category.join(",")
                },{
                    name:"date",
                    value:searchParams.date.join(",")
                },{
                    name:"size",
                    value:searchParams.size.join(",")
                },{
                    name:"color",
                    value:searchParams.color.join(",")
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
                            functions.ajaxReturnErrorHandler(response.message);
                        }else{
                            var json = {
                                "sEcho" : response.sEcho
                            };

                            for (var i = 0, iLen = response.aaData.length; i < iLen; i++) {
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
        searchParams:searchParams,
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
                        functions.ajaxReturnErrorHandler(response.message);
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

    productMgr.createTable();

    $("#myTable").on("click","a.remove",function(){
        if(confirm(config.messages.confirmDelete)){
            productMgr.delete($(this).attr("href"));
        }
        return false;
    });

    $("#searchBtn").click(function(){
        productMgr.searchParams={
            brand:[],
            category:[],
            date:[],
            color:[],
            size:[]
        };
        productMgr.tableRedraw();
    });

    $("#searchPanel").on("click",".item",function(){
        var el=$(this);
        var type=el.data("type");
        var id= el.data("id");
        var index;

        $("#searchNo").val("");

        if(el.hasClass("active")){
            index=productMgr.searchParams[type].indexOf(id);
            productMgr.searchParams[type].splice(index,1);
            el.removeClass("active");
        }else{
            productMgr.searchParams[type].push(id);
            el.addClass("active");
        }
        productMgr.tableRedraw();
    });

    $("#searchPanelCtrl").click(function(){
        if($(this).data("target").indexOf("down")!=-1){
            $("#searchPanel .row").hide(400);
            $(this).find(".glyphicon").removeClass("glyphicon-chevron-up").addClass("glyphicon-chevron-down");
            $(this).data("target","up");
            $(this).find(".text").text("展开选项");
        }else{
            $("#searchPanel .row").show(400);
            $(this).find(".glyphicon").removeClass("glyphicon-chevron-down").addClass("glyphicon-chevron-up");
            $(this).data("target","down");
            $(this).find(".text").text("收起选项");
        }

    });

});