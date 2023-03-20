<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="/static/layui/layui.js"  type="text/javascript"></script>
    <script src="/static/js/publicUtils.js" type="text/javascript"></script>
    <script src="/static/js/common.js" type="text/javascript"></script>
    <link href="/static/layui/css/layui.css" rel="stylesheet">
    <title >${(table.comment)!}</title>
    <style>
        .main-body{
            padding: 18px;
        }
    </style>
</head>
<body class="main-body">
<form class="layui-form" action="/app/list" id="dataForm" >
    <#assign strPrefix = "${" />
    <#assign strSuffix = "}" />
    <input id="pageIndex" name="pageInfo.pageIndex" th:value="${strPrefix!}queryData?.pageInfo?.pageIndex${strSuffix!}" type="hidden">
    <input id="pageSize" name="pageInfo.pageSize" th:value="${strPrefix!}queryData?.pageInfo?.pageSize${strSuffix!}" type="hidden">
    <input id="pageTotal"  th:value="${strPrefix!}pageData.total${strSuffix!}" type="hidden">
    <div class="layui-form-item query-form-item">
        <#list table.fields as field>
            <div class="layui-inline">
                <label class="layui-form-label">${field.comment}</label>
                <div class="layui-input-inline">
                    <input type="text" name="${field.propertyName}"   th:value="${strPrefix!}queryData.${field.propertyName}${strSuffix!}" autocomplete="off" class="layui-input" >
                </div>
            </div>
        </#list>
        <div class="layui-inline">
            <label class="layui-form-label">创建时间</label>
            <div class="layui-input-inline">
                <input type="text" name="startTime" id="queryParam['createStartTime']"  autocomplete="off"   th:value="${strPrefix!}queryData?.queryParam['createStartTime']${strSuffix!}" class="layui-input" >
            </div>
            <div class="layui-form-mid">-</div>
            <div class="layui-input-inline">
                <input type="text" name="endTime" id="queryParam['createEndTime']" th:value="${strPrefix!}queryData?.queryParam['createEndTime']${strSuffix!}"  autocomplete="off" class="layui-input" >
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">更新时间</label>
            <div class="layui-input-inline">
                <input type="text" name="startTime" id="queryParam['updateStartTime']"   th:value="${strPrefix!}queryData?.queryParam['updateStartTime']${strSuffix!}" autocomplete="off" class="layui-input" >
            </div>
            <div class="layui-form-mid">-</div>
            <div class="layui-input-inline">
                <input type="text" name="endTime" id="queryParam['updateEndTime']"  th:value="${strPrefix!}queryData?.queryParam['updateEndTime']${strSuffix!}" autocomplete="off" class="layui-input" >
            </div>
        </div>
        <div class="layui-inline">
            <button id="search" class="layui-btn layuiadmin-btn-list" lay-submit >
                <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
            </button>
        </div>
    </div>


    </div>

</form>
<table class="layui-table" id="tableList" lay-size="lg"  lay-filter="tableList"  >
    <thead>
    <tr>
        <th lay-data="{field:'left',type:'checkbox',algin:'center'}"></th>
        <#list table.fields as field>
        <th lay-data="{field:'${field.propertyName}'}">${field.comment}</th>
        </#list>
    </tr>
    </thead>
    <tbody>
    <tr th:each="item:${strPrefix!}pageData.records}">
        <td></td>
        <#list table.fields as field>
        <td th:text="${strPrefix!}item.${field.propertyName}${strSuffix!}"></td>
        </#list>
        <td></td>
    </tr>
    </tbody>
</table>
<div id="page"></div>
<script type="text/html" id="toolbar">
    <a class="layui-btn layui-btn-xs " lay-event="add">新增</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="dels">删除</a>
</script>

<script type="text/html" id="tool">
    <a class="layui-btn layui-btn-xs " lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs " lay-event="del">删除</a>
</script>



</body>
<script type="text/javascript">
    layui.use(['laydate'],function () {
        var laydate = layui.laydate;

        laydate.render(
            {
                elem:'#createStartTime',
                type:'datetime'
            })
        laydate.render(
            {
                elem:'#createEndTime',
                type:'datetime'
            })
        laydate.render(
            {
                elem:'#updateStartTime',
                type:'datetime'
            })
        laydate.render(
            {
                elem:'#updateEndTime',
                type:'datetime'
            })
        renderList("列表","${moduleName!}");
    });



    function tableToolEvent(table,obj) {
    }

    function tableToolBarEvent(table,obj) {

    }
</script>
</html>