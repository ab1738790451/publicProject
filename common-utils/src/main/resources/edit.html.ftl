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
<#assign strPrefix = "${" />
    <#assign strSuffix = "}" />
<form class="layui-form" th:action="${strPrefix!}ADMIN_URL_PREFIX${strSuffix!} +'/${moduleName!}/dosave'" id="dataForm" >
    <input type="hidden" name="id" th:value="${strPrefix!}data?.id${strSuffix!}">

    <#list table.fields as field>
        <div class="layui-form-item query-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">${field.comment}</label>
                <div class="layui-input-inline" >
                    <input type="text" name="${field.propertyName}"  lay-verify="required"  th:value="${strPrefix!}data?.${field.propertyName}${strSuffix!}" autocomplete="off" class="layui-input" >
                </div>
            </div>
        </div>
    </#list>
    <div class="layui-form-item query-form-item">
        <div class="layui-inline">
            <button id="save" class="layui-btn layuiadmin-btn-list" lay-submit  lay-filter="save">保存</button>
        </div>
    </div>

</form>
</body>
<script type="text/javascript">
    layui.use(['form'],function () {
       renderEdit('save','[[${strPrefix!}lastLayId${strSuffix!}]]');
    });


</script>
</html>