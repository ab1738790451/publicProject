var element = top.element;
var tabFilter = top.tabFilter;
var currTabLayId = top.currTabLayId;


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


    object.submitSave=((url,data,lastLayId,rollback)=>{
                let index = layer.load(2, {time: 10*1000});
                $.ajax({
                    url:url,
                    data:data,
                    type:'post',
                    dataType:'json',
                    success:function (res) {
                        layer.close(index);
                        if(res.code == 200){
                            if(rollback && typeof rollback == 'function'){
                                rollback(res);
                            }else{
                                if(currTabLayId && currTabLayId != -1 ){
                                    top.tabDelete(tabFilter,currTabLayId,lastLayId,true);
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
        });


    object.get = (({url,data,rollback})=>{
        $.ajax({
            url:url,
            data:data,
            type:'get',
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
            type:'get',
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