var element;
var tabIsAdd = false;
var menuFilter = "system-menu";
var tabFilter = "headrTab";
var table;
var currTabLayId;
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
        let index = data.index;
        let tab = data.elem;
        let lis = $(tab).find("li");
        if(lis.length > 0 && index > 0){
            let node = lis[index-1];
            let layId =  $(node).attr("lay-id");
            let  url =  $(node).attr("data-url");
            menuChange(layId,url);
            currTabLayId = layId;
        }else{
            menuChange(-1);
            currTabLayId = -1;
        }
    });


    //触发导航点击
    element.on('nav('+menuFilter+')', function(elem){
        let layId =  this.getAttribute('lay-id');
        let title = this.innerText;
        let url = this.getAttribute('data-url');
        if(layId){
            tabChange(tabFilter,layId,title,url);
            if(url){
                $(this).addClass("layui-this");
            }
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
function menuChange(layId,url) {
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
    //没有打开的选项卡，不再为菜单添加状态
    if(layId < 0){
        return;
    }
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

//子页面删除
function tabDelete(filter,layId,lastLayId,refresh){
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



