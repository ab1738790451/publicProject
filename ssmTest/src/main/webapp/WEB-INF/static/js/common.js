var currTable;
var tabFilter = top.tabFilter;
var moduleName;
var currTabLayId = top.currTabLayId;


function renderEdit(submitFilter,lastLayId){
    layui.use(['form'],function () {
        var form = layui.form;
        form.on('submit('+submitFilter+')',function (data) {
            if (typeof beforeSubmit == 'function') {
                if (!beforeSubmit(data)) {
                    return false;
                }
            }
            ajaxUtil.submitSave(data.form.action,data.field,lastLayId);
            return false;
        });

    });
}


function renderList(title,module,searchFilter) {
    layui.use(['form','table','laypage'],function () {
        currTable = layui.table
        moduleName = module;
        let laypage = layui.laypage;
        var form = layui.form;
        currTable.on('tool(tableList)',function (obj) {
        defaultTableToolEvent(currTable,obj);
        });
        currTable.on('toolbar(tableList)',function (obj) {
        defaultTableToolBarEvent(currTable,obj);
        });

        var defaultToolbar=['filter'];
        defaultToolbar.push({ //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '查询条件展示与隐藏',
            layEvent: 'search_display',
            icon: 'layui-icon-layer'
        })

        currTable.init('tableList', {
        id: 'tableList',
        // skin: 'line', //行边框风格
        even: true, //开启隔行背景
        // size: 'lg', //小尺寸的表格
        toolbar: '#toolbar', //开启头部工具栏，并为其绑定左侧模板
        defaultToolbar: defaultToolbar,
        title: title,
        limit: Number.MAX_VALUE,
        page: false,
        done: function () {
           let treeCol = $("#tableList").attr("treeCol");
           if(treeCol){
               let cellClass = ".laytable-cell-1-0-"+treeCol;
               const observer = new MutationObserver(function(mutations) {
                   mutations.forEach(function(mutation) {
                       let classs = mutation.target.classList;
                       for (let item of classs){
                           if(item == 'parent_node_colse'){
                               $(mutation.target).find('i').html("&#xe623;")
                           }else if(item == 'parent_node_open'){
                               $(mutation.target).find('i').html("&#xe625;")
                           }
                       }
                   });
               });

               $(".tree-node").each(function (index,item) {
                    let level =  $(this).attr('node-level');
                    if(level > 0){
                       let tr = $(".layui-table-body tr")[index];
                       $(tr).addClass('node_close');
                    }
               })
               $(".layui-table-body "+cellClass).each(function (index,item) {
                  let trIndex =  $(this).closest('tr').attr("data-index");
                   let tr = $("#tableList").find("tbody").find("tr")[trIndex];
                   let td = $(tr).find(".parent_node_colse");
                   if(td.length > 0){
                       let html  = $(this).html();
                       $(this).html('<i class="layui-icon">&#xe623;</i>' + html)
                       $(this).on('click',function () {
                             if($(this).hasClass('parent_node_colse') || !$(this).hasClass('parent_node_open')){
                                 $(this).removeClass('parent_node_colse');
                                 $(this).addClass('parent_node_open');
                                 $($(this).closest('tr').next()).removeClass('node_close');
                             }else{
                                 $(this).removeClass('parent_node_open');
                                 $(this).addClass('parent_node_colse');
                                 $($(this).closest('tr').next()).addClass('node_close');
                             }
                       })
                   }
                   observer.observe(item, { attributes: true, attributeFilter: ["class"] })
               })
           }

        }
        });

        currTable.render();
        searchFilter = (searchFilter == undefined || searchFilter.length == 0)?'search':searchFilter;
        form.on('submit('+searchFilter+')',function (data) {
            if (typeof beforeSearch == 'function') {
                if (!beforeSearch(data)) {
                    return false;
                }
            }
            return true;
        });

        //执行一个laypage实例
        laypage.render({
            elem: 'page', //注意，这里的 test1 是 ID，不用加 # 号
            count: $("#pageTotal").val(), //数据总数，从服务端得到
            limit: $("#pageSize").val(),
            curr:$("#pageIndex").val(),
            limits:[10,20,30,50,100],
            layout:['prev', 'page', 'next','limit'],
        });

        if($(".layui-laypage-prev").length != 0 && !($(".layui-laypage-prev").hasClass("layui-disabled"))){
            $(".layui-laypage-prev").on('click',function () {
                let prePage =  $(".layui-laypage-prev").attr("data-page") +1;
                $("#pageIndex").val(prePage);
                $("#pageSize").val($(".layui-laypage-limits").find("select").val());
                $("#search").click();
            })
        }

        if($(".layui-laypage-next").length != 0 && !($(".layui-laypage-next").hasClass("layui-disabled"))){
            $(".layui-laypage-next").on('click',function () {
                let prePage =  $(".layui-laypage-next").attr("data-page") -1 ;
                $("#pageIndex").val(prePage);
                $("#pageSize").val($(".layui-laypage-limits").find("select").val());
                $("#search").click();
            })
        }

        $("#page").find("a").each(function (index,item) {
             if(!$(this).hasClass("layui-laypage-prev") && !$(this).hasClass("layui-laypage-next")){
                 $(this).on('click',function () {
                     let dataPage =  $(this).attr("data-page");
                     $("#pageIndex").val(dataPage);
                     $("#pageSize").val($(".layui-laypage-limits").find("select").val());
                     $("#search").click();
                 })

             }
        })

        $("#page").find(".layui-laypage-limits").find("select").on('change',function (item) {
            $("#pageSize").val($(this).val());
            $("#search").click();
        });
    });
}

//表格头部工具栏事件
function defaultTableToolBarEvent(table,obj) {
    var data = table.checkStatus(obj.config.id).data;
    switch (obj.event) {
        case "add":
            top.tabChange(tabFilter,moduleName + "-0",'新增',"/"+moduleName+"/toEdit?lastLayId="+currTabLayId);
            break;
        case "dels":
            if(data.length == 0){
                layer.msg("请至少选择一条数据",{icon:2});
                return ;
            }
            let pks = [];
            for(let item of data){
                pks.push(item.id);
            }
            ajaxUtil.confirmRequest("/"+moduleName + "/del?pks="+pks,null,"批量删除","是否确定要删除选中数据",function () {
                $("#search").click();
            });
            break;
        case "search_display":
            let display =  $("#dataForm").css("display");
            if( display == "none"){
                $("#dataForm").show();
            }else{
                $("#dataForm").hide();
            }
            break;
    }
    if(typeof tableToolBarEvent == 'function'){
        tableToolBarEvent(table,obj);
    }

}

//表格行事件
function defaultTableToolEvent(table,obj) {
    var data = obj.data;
    switch (obj.event) {
        case "edit":
            top.tabChange(tabFilter,moduleName + data.id,'修改',"/"+moduleName+"/toEdit?lastLayId="+currTabLayId+"&pk="+data.id);
            break;
        case "del":
            ajaxUtil.confirmRequest("/"+moduleName + "/del",{pks:data.id},"批量删除","是否确定要删除选中数据",function () {
                $("#search").click();
            });
            break;
    }
    if(typeof tableToolEvent == 'function'){
           tableToolEvent(table,obj);
    }
}



