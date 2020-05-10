function passData(id, pass, flag, obj) {
    var url;
    if(flag === "申请"){
        url = "./passArticle";
/*        switch (obj) {
            case "article": url = "./passArticle";break;
            case "project": url = "./passProject";break;
            case "paper": url = "./passPaper";break;
            case "patent": url = "./passPatent";break;
            default: alert("参数错误"); return;
        }*/

    }else {
        url = "./actionArticle";
    }
    let data0 = {
        "id": id,
        "msg": pass,
        "obj": obj
    };
    $.ajax(
        {
            url: url,//这里是写controller中requestMapping中的路径
            type: 'POST',//通过get或者post在发送请求
            datatype: "json",//这是数据的格式，可以是String、json、xml等
            async: true,//同步或异步，如果有多个ajax请求，则设成false
            data: data0,//传给后台的参数，也可以手动写成json或其他格式，这里提前在上面创建一个json对象，如果不想这样写，也可以手动{id:<%= user.getId()%>}写一个匿名对象传过去
            success: function(data){//回调函数，如果请求成功，则会调用success方法
                alert(data);
                window.location.reload();
            },
            error: function(){//如果失败则会调用error方法
                alert("未知网络错误");
            }
        }
    )
}

function searchArticle2(a) {
    console.log(a);
    var title = document.getElementById("articleName").value;
    if(title === ""){
        alert("请输入论文名称");
        return;
    }
    document.getElementById("btn_search").innerHTML = "搜索中...";
    document.getElementById("btn_search").setAttribute("disabled", true);
    let data0 = {
        "title": title
    };
    var url = "./addArticle";
    window.location.href=url+'?title='+title;
    /*$.ajax(
        {
            url: url,//这里是写controller中requestMapping中的路径
            type: 'POST',//通过get或者post在发送请求
            datatype: "json",//这是数据的格式，可以是String、json、xml等
            async: true,//同步或异步，如果有多个ajax请求，则设成false
            data: data0,//传给后台的参数，也可以手动写成json或其他格式，这里提前在上面创建一个json对象，如果不想这样写，也可以手动{id:<%= user.getId()%>}写一个匿名对象传过去
            success: function(data){//回调函数，如果请求成功，则会调用success方法
                // alert(data);
                window.location.reload();
            },
            error: function(){//如果失败则会调用error方法
                alert("未知网络错误");
            }
        }
    )*/
}

function saveArticle(title) {
    let data0 = {
        "code": title
    };
    var url = "./addArticle.do";
    $.ajax(
        {
            url: url,//这里是写controller中requestMapping中的路径
            type: 'POST',//通过get或者post在发送请求
            datatype: "json",//这是数据的格式，可以是String、json、xml等
            async: true,//同步或异步，如果有多个ajax请求，则设成false
            data: data0,//传给后台的参数，也可以手动写成json或其他格式，这里提前在上面创建一个json对象，如果不想这样写，也可以手动{id:<%= user.getId()%>}写一个匿名对象传过去
            success: function(data){//回调函数，如果请求成功，则会调用success方法
                alert(data);
            },
            error: function(){//如果失败则会调用error方法
                alert("未知网络错误");
            }
        }
    )

}

function searchProject() {
    var title = document.getElementById("projectName").value;
    if (title === null || title === "") {
        alert("请输入项目名称");
        return;
    }
    document.getElementById("btn_search").innerHTML = "搜索中...";
    document.getElementById("btn_search").setAttribute("disabled", true);
    let data0 = {
        "title": title
    };
    var url = "./addProject";
    window.location.href = url + '?title=' + title;
}

function saveProject(title) {
    let data0 = {
        "code": title
    };
    var url = "./addProject.do";
    $.ajax(
        {
            url: url,//这里是写controller中requestMapping中的路径
            type: 'POST',//通过get或者post在发送请求
            datatype: "json",//这是数据的格式，可以是String、json、xml等
            async: true,//同步或异步，如果有多个ajax请求，则设成false
            data: data0,//传给后台的参数，也可以手动写成json或其他格式，这里提前在上面创建一个json对象，如果不想这样写，也可以手动{id:<%= user.getId()%>}写一个匿名对象传过去
            success: function(data){//回调函数，如果请求成功，则会调用success方法
                alert(data);
            },
            error: function(){//如果失败则会调用error方法
                alert("未知网络错误");
            }
        }
    )

}

