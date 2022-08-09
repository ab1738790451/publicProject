var element = top.element;
var tabFilter = top.tabFilter;
var currTabLayId = top.currTabLayId;

//菜单组合
var menuUtils = new (function() {
    let object = {};
    object.treeMenu = function treeMenu(el,data,template) {
        let html = "";
        if(template){
            if(!template.title){
                template.title = "title";
            }
            if(!template.dataUrl){
                template.dataUrl = "dataUrl";
            }
            if(!template.layId){
                template.layId = "layId";
            }
            if(!template.children){
                template.children = "children";
            }
        }else{
            template = {
                title:"title",
                dataUrl:"dataUrl",
                layId:"layId",
                children:"children"
            }
        }
        for(let item of data){
            html += temp(item,template);
        }
        $("#"+el).html(html);
        element.render();
    };

    function temp(data,template) {
        let layId = template.layId;
        let url = data[template.dataUrl]?data[template.dataUrl]:"";
        let html = '<li class="layui-nav-item">' ;
        if(data[template.children] && data[template.children].length > 0){
            html +='<a href="javascript:;" isParent data-url="'+url+'" lay-id='+data[layId]+'>'+data[template.title]+'</a>';
            html += childrenMenuTemp(data[template.children],template);
        }else{
            html +='<a href="javascript:;" data-url="'+data[template.dataUrl]+'" lay-id='+data[layId]+'>'+data[template.title]+'</a>';
        }
        html += '</li>';
        return html;
    };

    function childrenMenuTemp(data,template) {
        let html ='<dl class="layui-nav-child">';
        for(let item of data){
            let layId = template.layId;
            let url = data[template.dataUrl]?data[template.dataUrl]:"";
            if(item[template.children] && item[template.children].length > 0){
                html += '<dd><a href="javascript:;" isParent data-url="'+url+'" lay-id='+item[layId]+'>'+item[template.title]+'</a>';
                html += childrenMenuTemp(item[template.children],template);
            }else{
                html += '<dd><a href="javascript:;" data-url="'+item[template.dataUrl]+'" lay-id='+item[layId]+'>'+item[template.title]+'</a>';
            }
            html += '</dd>';
        }
        html += '</dl>';
        return html;
    };
    return object;
})


var navUtil = new (function () {
    let object={};

    //将目标导航数组合并到源导航数组
    object.mergeNavData = ((source,target,sourceTemplate,targetTemplate)=>{
        if(!source){
            return target;
        }
        if(!target){
            return source;
        }
        sourceTemplate =  checkTemplate(sourceTemplate);
        if(!targetTemplate){
            targetTemplate = sourceTemplate;
        }else{
            targetTemplate =  checkTemplate(targetTemplate);
        }
        for(let item of target){
            let newData = {};
            newData[sourceTemplate.title] = item[targetTemplate.title];
            newData[sourceTemplate.dataUrl] = item[targetTemplate.dataUrl];
            newData[sourceTemplate.target] = item[targetTemplate.target];
            newData[sourceTemplate.children] = item[targetTemplate.children];
            newData[sourceTemplate.hasImg] = item[targetTemplate.hasImg];
            newData[sourceTemplate.img] = item[targetTemplate.img];
            source.push(newData);
        }
    })

    //加载菜单
    object.loadTreeNav = ((el,data,template) =>{
        let html = "";
        template =  checkTemplate(template);
        for(let item of data){
            html += temp(item,template);
        }
        $("#"+el).html(html);
        element.render();
    })

    //模板处理
    function checkTemplate(template) {
        if(template){
            if(!template.title){
                template.title = "title";
            }
            if(!template.dataUrl){
                template.dataUrl = "dataUrl";
            }
            if(!template.children){
                template.children = "children";
            }
            if(!template.target){
                template.target = "target";
            }
            if(!template.hasImg){
                template.hasImg = "hasImg";
            }
            if(!template.img){
                template.img = "img";
            }
        }else{
            template = {
                title:"title",
                dataUrl:"dataUrl",
                children:"children",
                target:"target",
                hasImg:"hasImg",
                img:"img"
            }
        }
        return template;
    }

    //单个导航
    function temp(data,template) {
        let html = '<li class="layui-nav-item">' ;
        html += checka(data,template);
        if(data[template.children] && data[template.children].length > 0){
            html += '<dl class="layui-nav-child layui-panel">';
            html += childrenMenuTemp(data[template.children],template);
            html +='</dl>';
        }
        html += '</li>';
        return html;
    }

    //导航子菜单
    function childrenMenuTemp(data,template) {
        let html ='<ul class="layui-menu" >';
        for(let item of data){
            let layId = template.layId;
            if(item[template.children] && item[template.children].length > 0){
                html += ' <li class="layui-menu-item-parent" lay-options="{type: \'parent\'}">';
                html += checka(item,template) ;
                html +=' <div class="layui-panel layui-menu-body-panel">';
                html += childrenMenuTemp(item[template.children],template);
                html += '</div></li>';
            }else{
                html +=' <li lay-options="{id: '+item[layId]+'}">';
                html += checka(item,template);
                html += ' </li>';
            }
        }
        html += '</ul>';
        return html;
    }

    //导航链接处理
    function checka(data,template) {
        let target = data[template.target]?data[template.target]:"_blank";
        let title = data[template.hasImg] ? ('<img src="'+data[template.img]+'" class="layui-nav-img">'+data[template.title]):data[template.title];
        if(data[template.dataUrl]){
            return  '<a class="layui-menu-body-title" href="'+data[template.dataUrl]+'"  target="'+target+'">'+title+'</a>' ;
        }else{
            return  '<a class="layui-menu-body-title" href="javascript:;" >'+title+'</a>' ;
        }
    }
    return object;
})

var ajaxUtil = new (function(){
    let object = {};
    object.confirmRequest = ((url,data,title,message,rollback)=>{
        layer.confirm(message,{btn:['确认','取消'],title},function () {
            let index = layer.load(2, {time: 10*1000});
            $.ajax({
                url:url,
                type:'post',
                data:data,
                dataType:'json',
                success:function (res) {
                    layer.close(index);
                    if(res.code == 200){
                        if(rollback && typeof rollback == 'function'){
                            rollback(res);
                        }else{
                            layer.msg(res.message,{icon:1});
                        }

                    }else{
                        layer.msg(res.message,{icon:2});
                    }
                },
                error:function () {
                    layer.close(index);
                    layer.msg("提交失败",{icon:2});
                }
            });
        })

    });

    object.submitSave=((url,filter,lastLayId,rollback)=>{
        layui.use(['form'], function () {
            let form = layui.form;
            form.on('submit('+filter+')',function (data) {
                if(typeof beforeSubmit == 'funtion'){
                    if(!beforeSubmit(data)){
                        return false;
                    };
                }
                let index = layer.load(2, {time: 10*1000});
                $.ajax({
                    url:url,
                    data:data.field,
                    dataType:'post',
                    success:function (res) {
                        layer.close(index);
                        if(res.code == 200){
                            if(rollback && typeof rollback == 'function'){
                                rollback(res);
                            }else{
                                if(currTabLayId && currTabLayId != -1 ){
                                    tabDelete(tabFilter,currTabLayId,lastLayId,true);
                                }else{
                                    layer.msg(res.message,{icon:1});
                                }

                            }

                        }else{
                            layer.msg(res.message,{icon:2});
                        }
                    },
                    error:function () {
                        layer.close(index);
                        layer.msg("保存失败",{icon:2});
                    }
                });
                return false;
            })
        });

    });

    object.get = ((url,rollback)=>{
        object.get(url,null,rollback);
    });

    object.get = (({url,data,rollback})=>{
        $.ajax({
            url:url,
            data:data,
            dataType:'json',
            success:function (res) {
                if(res.code == 200){
                    if(rollback && typeof rollback == 'function'){
                        rollback(res);
                    }
                }else{
                    layer.msg(res.message,{icon:2});
                }
            },
            error:function () {
                layer.msg("请求失败",{icon:2});
            }
        });
    });


    object.syncGet = (({url,data,rollback})=>{
        $.ajax({
            url:url,
            data:data,
            dataType:'json',
            async:false,
            success:function (res) {
                if(res.code == 200){
                    if(rollback && typeof rollback == 'function'){
                        rollback(res);
                    }
                }else{
                    layer.msg(res.message,{icon:2});
                }
            },
            error:function () {
                layer.msg("请求失败",{icon:2});
            }
        });
    });

    return object;
});