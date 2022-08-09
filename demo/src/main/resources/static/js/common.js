var currTable;
var tabFilter = top.tabFilter;
var moduleName;

function renderList(title,module,table) {
    currTable = table;
    moduleName = module;
    table.on('tool(tableList)',function (obj) {
        defalutTableToolEvent(table,obj);
    });
    table.on('toolbar(tableList)',function (obj) {
        defalutTableToolBarEvent(table,obj);
    });

    var defaultToolbar=['filter'];
    defaultToolbar.push({ //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
        title: '查询条件展示与隐藏',
        layEvent: 'search_display',
        icon: 'layui-icon-layer'
    })

    table.init('tableList', {
        id: 'tableList',
        // skin: 'line', //行边框风格
        even: true, //开启隔行背景
        // size: 'lg', //小尺寸的表格
        toolbar: '#toolbar', //开启头部工具栏，并为其绑定左侧模板
        defaultToolbar: defaultToolbar,
        title: title,
        limit: Number.MAX_VALUE,
        page: false,
        /*done: function () {
            relayout()
        }*/
    });

    table.render();
}

//表格头部工具栏事件
function defalutTableToolBarEvent(table,obj) {
    var data = table.checkStatus(obj.config.id).data;
    switch (obj.event) {
        case "add":
            top.tabChange(tabFilter,moduleName + "-0",'新增',"/"+moduleName+"/toEdit");
            break;
        case "dels":
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
function defalutTableToolEvent(table,obj) {
    var data = obj.data;
    switch (obj.event) {
        case "edit":
            top.tabChange(tabFilter,moduleName + data.id,'修改',"/"+moduleName+"/toEdit");
            break;
        case "del":
            break;
    }
    if(typeof tableToolEvent == 'function'){
           tableToolEvent(table,obj);
    }
}



