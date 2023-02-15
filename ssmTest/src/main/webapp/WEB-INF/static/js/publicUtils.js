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
                type:'get',
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
                error:function (err) {
                    layer.close(index);
                    let text = err.responseText;
                    if(text.length != 0 && text.indexOf("alert") != -1){
                        $('body').html(err.responseText);
                    }
                }
            });
        })

    });


    object.submitSave=((url,data,lastLayId,rollback)=>{
                let index = layer.load(2, {time: 10*1000});
                $.ajax({
                    url:url,
                    data:JSON.stringify(data),
                    contentType:'application/json;charset=utf-8',
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
                    error:function (err) {
                        layer.close(index);
                        if(err.responseText.length != 0 && err.responseText.indexOf("<srcipt>") !=-1){
                            $('body').html(err.responseText);
                        }
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
            error:function (err) {
                if(err.responseText.length != 0 && err.responseText.indexOf("<srcipt>") !=-1){
                    $('body').html(err.responseText);
                }
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
            error:function (err) {
                if(err.responseText.length != 0 && err.responseText.indexOf("<srcipt>") !=-1){
                    $('body').html(err.responseText);
                }
            }
        });
    });

    return object;
});