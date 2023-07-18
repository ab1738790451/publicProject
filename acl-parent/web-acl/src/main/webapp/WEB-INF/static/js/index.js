var element;
var tabIsAdd = false;
let manualDeleted = false;
var menuFilter = "system-menu";
var tabFilter = "headrTab";
var navFilter = "headr-nav";
var table;
var currTabLayId;
var urlPrefix;
//注意：导航 依赖 element 模块，否则无法进行功能性操作
layui.use(['element','table'], function(){
    element = layui.element;
    table = layui.table;
    //选项卡切换
    element.on('tab('+tabFilter+')',function (data) {
        //console.log(data.index); //得到当前Tab的所在下标
        //console.log(data.elem); //得到当前的Tab大容器
        //新增的选项卡不执行任何操作,仅仅切换选中状态
        if(tabIsAdd){
            tabIsAdd = false;
            return;
        }
    })

    //选项卡删除
    element.on('tabDelete('+tabFilter+')', function(data){
       /* if(manualDeleted){
            manualDeleted = true;
            return false;
        }*/
        let index = data.index;
        let tab = data.elem;
        let lis = $(tab).find("li");
        let layId;
        if(lis.length > 0 && index > 0){
            let node = lis[index-1];
            layId =  $(node).attr("lay-id");
            currTabLayId = layId;
        }else{
            layId = -1;
        }
        currTabLayId = layId;
        menuChange(currTabLayId);
        //没有打开的选项卡，不再为菜单添加状态
        if(layId){
            //为选项卡对应的菜单赋予选中状态
            $(".body-col-left a").each(function (index,item) {
                let currentLayId =  $(this).attr("lay-id");
                if(currentLayId == layId){
                    let isParent =  $(this).attr("isParent");
                    //父菜单添加到自己身上，子菜单添加到父节点上
                    if(isParent != null && isParent != undefined){
                        $(this).addClass("layui-this");
                    }else{
                        let parentNode =  this.parentNode;
                        $(parentNode).addClass("layui-this");
                    }
                    $(this).closest("li").addClass("layui-nav-itemed");
                }
            })
        }


    });


    //触发导航点击
    element.on('nav('+menuFilter+')', function(elem){
        let layId =  this.getAttribute('lay-id');
        let title = this.innerText;
        let url = this.getAttribute('data-url');
        if(layId){
            let sourceNode = $(".body-col-left .layui-this");
            if (sourceNode.length > 0) {
                sourceNode.removeClass("layui-this");
            }
            tabChange(tabFilter,layId,title,url);
            if(url || layId <=0 ){
                $(this).addClass("layui-this");
            }
        }

    });

    //触发导航点击
    element.on('nav('+navFilter+')', function(elem){
        let layId =  this.getAttribute('lay-id');
        let title = this.innerText;
        let url = this.getAttribute('data-url');
        if(layId){
            //清空所有菜单的选中状态
            let sourceNode =  $(".body-col-left .layui-this");
            if(sourceNode.length > 0){
                sourceNode.removeClass("layui-this");
            }
            //清空所有菜单的打开状态
            let sourceParentNode = $(".body-col-left .layui-nav-itemed");
            if(sourceParentNode.length > 0){
                sourceParentNode.removeClass("layui-nav-itemed");
            }
            tabChange(tabFilter,layId,title,url);
        }

    });


    //触发折叠面板
    element.on('collapse('+menuFilter+')', function(data){
        //console.log(data.show); //得到当前面板的展开状态，true或者false
        //console.log(data.title); //得到当前点击面板的标题区域DOM对象
        //console.log(data.content); //得到当前点击面板的内容区域DOM对象
    });
});

//初始化页面事件
$(function () {
    $(".menu-hide").click(function () {
        $(".menu-hide").hide();
        $(".menu-show").show();
        $(".body-col-left").hide();
    })
    $(".menu-show").click(function () {
        $(".menu-show").hide();
        $(".menu-hide").show();
        $(".body-col-left").show();
    })
})

//菜单切换
function menuChange() {
    //清空所有菜单的选中状态
    let sourceNode = $(".body-col-left .layui-this");
    if (sourceNode.length > 0) {
        sourceNode.removeClass("layui-this");
    }
    //清空所有菜单的打开状态
    let sourceParentNode = $(".body-col-left .layui-nav-itemed");
    if (sourceParentNode.length > 0) {
        sourceParentNode.removeClass("layui-nav-itemed");
    }
}

//子页面删除
function tabDelete(filter,layId,lastLayId,refresh){
    //manualDeleted = true;
    element.tabDelete(filter, layId);
    if(lastLayId && refresh == true){
        //切换选项卡后再刷新页面
        tabIsAdd = true;
        tabChange(filter,lastLayId);
        document.getElementById("iframe-"+lastLayId).contentWindow.location.reload();
    }
}

//子页面切换
function tabChange(filter,layId,title,url){
    let isChange = false;
    $(".body-col-right .layui-tab-title li").each(function (index,item){
        if(isChange){
            return;
        }
        let id = $(this).attr("lay-id");
        if(id == layId){
            currTabLayId = layId;
            element.tabChange(filter, layId);
            isChange = true;
            return;
        }
    });

    if(!isChange && url){
        tabAdd(filter,layId,title,url);
    }
}

//打开新的子页面
function tabAdd(filter,layId,title,url){
    tabDelete(filter,layId,);
    element.tabAdd(filter, {
        title: title,//标题
        content: '<iframe id="iframe-'+layId+'" src="'+url+'" class="iframe-class"></iframe>', //支持传入html
        id: layId ,//选项卡layId
    });

    //使tab变成选中状态
    tabIsAdd = true;
    element.tabChange(filter, layId);
    currTabLayId = layId;
}


//菜单组合
var menuUtils = new (function() {
    let object = {};
    object.treeMenu = function treeMenu(el,data,template) {
        if(!data){
            return;
        }
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
            if(!template.urlPrefix){
                template.urlPrefix = "";
            }
        }else{
            template = {
                title:"title",
                dataUrl:"dataUrl",
                layId:"layId",
                children:"children",
                urlPrefix:""
            }
        }
        urlPrefix =  template.urlPrefix;
        for(let item of data){
            html += temp(item,template);
        }
        $("#"+el).append(html);
        element.render();
    };

    function temp(data,template) {
        let layId = template.layId;
        let url = data[template.dataUrl]?data[template.dataUrl]:"";
        if(url.length > 0){
            url = url.split(",")[0];
        }
        let html = '<li class="layui-nav-item">' ;
        if(data[template.children] && data[template.children].length > 0){
            html +='<a href="javascript:;" isParent data-url="'+url+'" lay-id='+data[layId]+'>'+data[template.title]+'</a>';
            html += childrenMenuTemp(data[template.children],template);
        }else{
            html +='<a href="javascript:;" data-url="'+url+'" lay-id='+data[layId]+'>'+data[template.title]+'</a>';
        }
        html += '</li>';
        return html;
    };

    function childrenMenuTemp(data,template) {
        let html ='<dl class="layui-nav-child">';
        for(let item of data){
            let layId = template.layId;
            let url = item[template.dataUrl]?item[template.dataUrl]:"";
            if(url.length > 0){
                url = url.split(",")[0];
            }
            if(item[template.children] && item[template.children].length > 0){
                html += '<dd><a href="javascript:;" isParent data-url="'+url+'" lay-id='+item[layId]+'>'+item[template.title]+'</a>';
                html += childrenMenuTemp(item[template.children],template);
            }else{
                html += '<dd><a href="javascript:;" data-url="'+url+'" lay-id='+item[layId]+'>'+item[template.title]+'</a>';
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
    object.loadTreeNav = ((el,filter,data,template) =>{
        if(!data){
            return;
        }
        let html = "";
        template =  checkTemplate(template);
        for(let item of data){
            html += temp(item,template);
        }
        $("#"+el).html(html);
        element.init('nav', filter)
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
            if(!template.layId){
                template.layId = "layId";
            }
        }else{
            template = {
                title:"title",
                dataUrl:"dataUrl",
                children:"children",
                target:"target",
                hasImg:"hasImg",
                img:"img",
                layId:"layId",
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