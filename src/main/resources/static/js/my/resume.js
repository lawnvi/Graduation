function responseResume(keyword, rid) {
    if(keyword === 'detail'){
        window.location.href = './resumeDetail?id='+rid;
        return;
    }
    let datas = {
        "rid" : rid,
        "keyword" : keyword
    };
    url = './responseResume'
    $.ajax(
        {
            url: url,//这里是写controller中requestMapping中的路径
            type: 'POST',//通过get或者post在发送请求
            datatype: "json",//这是数据的格式，可以是String、json、xml等
            async: true,//同步或异步，如果有多个ajax请求，则设成false
            data: datas,//传给后台的参数，也可以手动写成json或其他格式，这里提前在上面创建一个json对象，如果不想这样写，也可以手动{id:<%= user.getId()%>}写一个匿名对象传过去
            success: function(data){//回调函数，如果请求成功，则会调用success方法
                if(data) {
                    alert(data);
                    window.location.reload();
                }
            },
            error: function(){//如果失败则会调用error方法
                alert("未知网络错误");
            }
        }
    )
}

function handleObj(keyword, id, obj) {
    if(keyword === 'detail'){
        window.location.href = './handleObj?id='+rid;
        return;
    }
    let datas = {
        "id" : id,
        "keyword" : keyword,
        "obj":obj
    };
    url = './handleObj';
    $.ajax(
        {
            url: url,//这里是写controller中requestMapping中的路径
            type: 'POST',//通过get或者post在发送请求
            datatype: "json",//这是数据的格式，可以是String、json、xml等
            async: true,//同步或异步，如果有多个ajax请求，则设成false
            data: datas,//传给后台的参数，也可以手动写成json或其他格式，这里提前在上面创建一个json对象，如果不想这样写，也可以手动{id:<%= user.getId()%>}写一个匿名对象传过去
            success: function(data){//回调函数，如果请求成功，则会调用success方法
                if(data) {
                    alert(data);
                    window.location.reload();
                }
            },
            error: function(){//如果失败则会调用error方法
                alert("未知网络错误");
            }
        }
    )
}
