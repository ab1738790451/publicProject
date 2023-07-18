var currTable;
var tabFilter = top.tabFilter;
var moduleName;
var currTabLayId = top.currTabLayId;
var urlPrefix = top.urlPrefix;

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

//表格初始化
function renderList(title,module,searchFilter,done) {
    hideCol();
    layui.use(['form','table','laypage'],function () {
        currTable = layui.table
        moduleName = module;
        let laypage = layui.laypage;
        var form = layui.form;
        //行内工具栏注册
        currTable.on('tool(tableList)',function (obj) {
            defaultTableToolEvent(currTable,obj);
        });
        //头部工具栏注册
        currTable.on('toolbar(tableList)',function (obj) {
            defaultTableToolBarEvent(currTable,obj);
        });

        var defaultToolbar=['filter'];
        defaultToolbar.push({ //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '查询条件展示与隐藏',
            layEvent: 'search_display',
            icon: 'layui-icon-layer'
        })

        //表格初始化
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
                //表格初始化后得回调函数
                if(typeof done == 'function'){
                    done();
                }
            }
        });

        currTable.render();

        //列表页查询
        searchFilter = (searchFilter == undefined || searchFilter == null || searchFilter.length == 0)?'search':searchFilter;
        form.on('submit('+searchFilter+')',function (data) {
            if (typeof beforeSearch == 'function') {
                if (!beforeSearch(data)) {
                    return false;
                }
            }
            return true;
        });

        //分页插件注册
        laypage.render({
            elem: 'page', //注意，这里的 test1 是 ID，不用加 # 号
            count: $("#pageTotal").val(), //数据总数，从服务端得到
            limit: $("#pageSize").val(),
            curr:$("#pageIndex").val(),
            limits:[10,20,30,50,100],
            layout:['prev', 'page', 'next','limit'],
        });

        //上一页
        if($(".layui-laypage-prev").length != 0 && !($(".layui-laypage-prev").hasClass("layui-disabled"))){
            $(".layui-laypage-prev").on('click',function () {
                let prePage =  $(".layui-laypage-prev").attr("data-page") +1;
                $("#pageIndex").val(prePage);
                $("#pageSize").val($(".layui-laypage-limits").find("select").val());
                $("#search").click();
            })
        }

        //下一页
        if($(".layui-laypage-next").length != 0 && !($(".layui-laypage-next").hasClass("layui-disabled"))){
            $(".layui-laypage-next").on('click',function () {
                let prePage =  $(".layui-laypage-next").attr("data-page") -1 ;
                $("#pageIndex").val(prePage);
                $("#pageSize").val($(".layui-laypage-limits").find("select").val());
                $("#search").click();
            })
        }

        //指定页
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

        //分页选项
        $("#page").find(".layui-laypage-limits").find("select").on('change',function (item) {
            $("#pageIndex").val(1);
            $("#pageSize").val($(this).val());
            $("#search").click();
        });
    });
}

//树形表单初始化
function renderTree(title,module,searchFilter,treeUrl){
    let array = $("#dataForm").serializeArray();
    let value ={};
    for (let item of array){
        value[item.name] = item.value;
    }
    ajaxUtil.get({url:treeUrl,data:value,rollback:function (res) {
            let data = res.data.treeDatas;
            let heads =  $("#tableList").find("thead").find("th");
            let headArray = [];
            for (let i=0;i < heads.length; i++){
                let layData = $(heads[i]).attr('lay-data');
                let splits = layData.replace("{","").replace("}","").replace("'","").replace("'","").split(",");
                let field = "";
                for(let item of splits){
                    let key = item.split(":")[0];
                    if(key == 'field'){
                        field = item.split(":")[1];
                    }
                }
                let node = {};
                let colNum =  $("#tableList").attr("treeCol");
                if(field == 'left'){
                    colNum += 1;
                    node['field'] = 'left';
                    node['parentNode'] = -1;
                }else{
                    node['field'] = field;
                    if(colNum == i+1){
                        node['parentNode'] = 1;
                    }else{
                        node['parentNode'] = 0;
                    }
                }
                headArray.push(node);
            }
            let html = "";
            for(let item of data){
                html +=iteretor(item,headArray,0);
            }
            $("#tableList").find("tbody").html(html);
            hideCol();
            //表格初始化
            renderList(title,module,searchFilter,function () {
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
                        let tr = $(".layui-table-body tr")[index];
                        if(level > 0){
                            $(tr).addClass('node_close');
                        }
                        let dataId =  $(this).attr('data-id');
                        $(tr).attr("data-id",dataId);
                        let dataParentId =  $(this).attr('data-parent-id');
                        $(tr).addClass("parent_node_"+dataParentId);
                    })
                    $(".layui-table-body "+cellClass).each(function (index,item) {
                        let trIndex =  $(this).closest('tr').attr("data-index");
                        let tr = $("#tableList").find("tbody").find("tr")[trIndex];
                        let level = $(tr).attr('node-level');
                        let td = $(tr).find(".parent_node_colse");
                        if(td.length > 0){
                            let html  = $(this).html();
                            let dataId =  $(tr).attr('data-id');
                            $(this).html(getmultipartBlank(level) + '<i class="layui-icon">&#xe623;</i>' + html)
                            $(this).on('click',function () {
                                if($(this).hasClass('parent_node_colse') || !$(this).hasClass('parent_node_open')){
                                    $(this).removeClass('parent_node_colse');
                                    $(this).addClass('parent_node_open');
                                    $(".parent_node_"+dataId).removeClass('node_close');
                                }else{
                                    $(this).removeClass('parent_node_open');
                                    $(this).addClass('parent_node_colse');
                                    $(".parent_node_"+dataId).addClass('node_close');
                                }
                            })
                        }else{
                            let html  = $(this).html();
                            $(this).html(getmultipartBlank(level)  + html)
                        }
                        observer.observe(item, { attributes: true, attributeFilter: ["class"] })
                    })
                }
            })
        }});
}

//表格数据迭代
function iteretor(data,headArray,level) {
    let html = "";
    let children =  data.children;
    if(children != null && children.length > 0){
        html += tdHtml(headArray,data,level,true);
        for (let item of children){
            html += iteretor(item,headArray,level +1);
        }
    }else{
        html += tdHtml(headArray,data,level,false);
    }
    return html;
}
//组装表格行
function tdHtml(headArray,data,level,hasIcon) {
    let html = '<tr class="tree-node" node-level="'+level+'"  data-id="'+data.id+'"  data-parent-id="'+data.parentId+'" ';
    html += '>';
    for (let item of headArray){
        let val = data[item.field];
        if(val == undefined || val == null || val.length == 0){
            val = "";
        }
        if(item.parentNode == -1){
            html += '<td></td>';
        }else if(item.parentNode == 1 && hasIcon){
            html += '<td class="parent_node_colse">';
            html += val+'</td>'
        }else{
            html += '<td>'+val+'</td>'
        }
    }
    html += '</tr>';
    return html;
}

//获得倍数空白字符
function getmultipartBlank(num) {
    let html = "";
    if(num ==undefined || num == null || num < 1){
        return html;
    }
    for (let i = 0; i < num *4 ; i++) {
        html += "&nbsp;"
    }
    return html;
}


//表格头部工具栏事件
function defaultTableToolBarEvent(table,obj) {
    var data = table.checkStatus(obj.config.id).data;
    switch (obj.event) {
        case "add":
            top.tabAdd(tabFilter,moduleName + "-add",moduleName+'新增',urlPrefix+"/"+moduleName+"/toEdit?lastLayId="+currTabLayId);
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
            ajaxUtil.confirmRequest(urlPrefix+"/"+moduleName + "/del?pks="+pks,null,"批量删除","是否确定要删除选中数据",function () {
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
        case 'LAYTABLE_COLS':
            $(":checkbox[title='hideFilterField']").parents("li").hide();
            //监听工具栏筛选列操作，缓存隐藏列
            toolbarFilter();
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
            top.tabAdd(tabFilter,moduleName + "-edit",moduleName +'修改#'+ data.id,urlPrefix+"/"+moduleName+"/toEdit?lastLayId="+currTabLayId+"&pk="+data.id);
            break;
        case "del":
            ajaxUtil.confirmRequest(urlPrefix+"/"+moduleName + "/del",{pks:data.id},"批量删除","是否确定要删除选中数据",function () {
                $("#search").click();
            });
            break;
    }
    if(typeof tableToolEvent == 'function'){
        tableToolEvent(table,obj);
    }
}

$(function () {
    $("#searchPrefix").on('click',function () {
        $("#pageIndex").val(1);
        $("#search").click();
    })
})

function toolbarFilter() {
    let toolbarFilterJson = {};
    let lis =  $(".layui-table-tool-panel li");
    if(lis.length == 0){
        return;
    }
    let url = location.host + location.pathname;
    let jsonStr = sessionStorage.getItem(url);
    if(jsonStr == null || jsonStr == undefined){
        toolbarFilterJson = {};
    }else{
        toolbarFilterJson = JSON.parse(jsonStr);
    }

    lis.on('click',function () {
        let input = $(this).find("input");
        let name = $(input).attr('name');
        if(!name){
            name = $(input).attr('title');
        }

        let classS = $(this).find("div").attr("class");
        if(classS.indexOf('layui-form-checked') == -1){
            toolbarFilterJson[name] = "unChecked";
        }else{
            toolbarFilterJson[name] = "checked";
        }
        sessionStorage.setItem(url,JSON.stringify(toolbarFilterJson));
    })
}

function hideCol() {
    let toolbarFilterJson = {};
    let url = location.host + location.pathname;
    let jsonStr = sessionStorage.getItem(url);
    if(jsonStr == null || jsonStr == undefined){
        return;
    }
    toolbarFilterJson = JSON.parse(jsonStr);
    let tables = $("table");
    let currTable;
    if(tables.length > 0){
        for(let tb of tables){
            if($(tb).attr("lay-filter") == 'tableList'){
                currTable = tb;
                continue;
            }
        }
    }
    if(currTable == null || currTable == undefined){
        return;
    }

    let htrs = $(currTable).find("thead tr");
    if(htrs.length == 0){
        return;
    }
    for(let tr of htrs){
        let ths = $(tr).find("th");
        for(let th of ths){
            let layData = $(th).attr("lay-data");
            let fieldIndex = layData.indexOf("field");
            if(fieldIndex == -1){
                continue;
            }
            let s = layData.substring(fieldIndex).replace("}","");
            s = s.substring(s.indexOf(":")+1);
            let field = s.split(",")[0];
            field = field.replace("'","").replace("'","");
            if(field && toolbarFilterJson[field] ){
                let hide;
                if(toolbarFilterJson[field] == 'unChecked'){
                    hide = true;
                }else{
                    hide = false;
                }
                if(layData.indexOf("hide") != -1){
                    layData = layData.replace(/[h][i][d][e]\s*[:]\s*[a-z]{4,5}\s*/g,"hide:"+hide);
                }else{
                    layData = layData.replace("}","");
                    if(layData.trim().endsWith(",")){
                        layData += "hide:"+hide +"}";
                    }else{
                        layData += ",hide:"+hide +"}";
                    }
                }
                $(th).attr("lay-data",layData);
            }
        }
    }
}

function hideCol2(cols) {
    let toolbarFilterJson = {};
    let url = location.host + location.pathname;
    let jsonStr = sessionStorage.getItem(url);
    if(jsonStr == null || jsonStr == undefined){
        return;
    }
    toolbarFilterJson = JSON.parse(jsonStr);
    for(let col of cols){
        for(let cl of col){
            let field = cl['title'];
            if(field == null || field == undefined){
                continue;
            }


            if(field && toolbarFilterJson[field]){
                if(toolbarFilterJson[field] == 'unChecked'){
                    cl['hide'] = true;
                }else{
                    cl['hide'] = false;
                }
            }
        }
    }
}

function defaultToolBarListening() {
    let toolBars  = $(".ew-tree-table-tool-item");
    if(toolBars.length > 0){
        for(let item of toolBars){
            let layEvent = $(item).attr('lay-event');
            if(layEvent && layEvent == 'LAYTABLE_COLS'){
                $(item).on('click',function () {

                    let index = setInterval(function () {
                        let lis =  $(".layui-table-tool-panel li");
                        if(lis.length > 0){
                            toolbarFilter();
                            clearInterval(index);
                        }

                    },50)

                })
            }
        }
    }
}
