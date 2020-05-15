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
    var way = "code";
    if($("#radio_name").prop("checked")){
        way = "name";
    }
    document.getElementById("btn_search").innerHTML = "搜索中...";
    document.getElementById("btn_search").setAttribute("disabled", true);
    let data0 = {
        "title": title,
        "way": way
    };
    var url = "./addProject";
    window.location.href = url + '?title=' + title+'&way='+way;
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
                window.location.href = "./addProject";
            },
            error: function(){//如果失败则会调用error方法
                alert("未知网络错误");
            }
        }
    )

}

function addPatent() {
    var title = document.getElementById("articleName").value;
    var kind = document.getElementById("kind").value;
    var role = document.getElementById("role").value;
    var email = document.getElementById("email").value;
    var notes = document.getElementById("notes").value;
    if(title === null || title === ''){
        alert("请填写专利名称");
        return;
    }
    if(kind === null || kind === ''){
        alert("请选择专利类型");
        return;
    }
    if(email === null || email === ''){
        alert("请填写发明人邮箱");
        return;
    }
    if(role === null || role === ''){
        alert("请选择发明人身份");
        return;
    }
    if(notes === null){
        notes = '';
    }
    let data0 = {
        "title": title,
        "kind": kind,
        "role": role,
        "email":email,
        "notes": notes
    };
    var url = "./addPatent.do";
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

function fillPatent(id, uid, name, kind, role, notes) {
    document.getElementById("new_id").value = id;
    document.getElementById("uid").value = uid;
    document.getElementById("patent_name").value = name;
    // document.getElementById("kind").innerHTML = kind;
    if(kind === "国内") {
        $("#kind1").prop("checked", true);
        $("#kind2").prop("checked", false);
    }else {
        $("#kind2").prop("checked", true);
        $("#kind1").prop("checked", false);
    }
    $("#role").val(role);
    // document.getElementById("role").innerHTML = role;
    document.getElementById("notes").innerHTML = notes;
}

function updatePatent() {
    var id = document.getElementById("new_id").value;
    var uid = document.getElementById("uid").value;
    var title = document.getElementById("patent_name").value;
    var role = document.getElementById("role").value;
    var notes = document.getElementById("notes").value;

    var kind = "国内";
    if($("#kind2").prop("checked")){
        kind = "国际";
    }
    console.log(kind);
    if(title === null || title === ''){
        alert("请填写专利名称");
        return;
    }
    if(kind === ''){
        alert("请选择专利类型");
        return;
    }
    if(role === null || role === ''){
        alert("请选择发明人身份");
        return;
    }
    if(notes === null){
        notes = '';
    }
    let data0 = {
        "uid":uid,
        "id" : id,
        "title": title,
        "kind": kind,
        "role": role,
        "notes": notes
    };
    var url = "./updatePatent.do";
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

function addPaper() {
    var esi = document.getElementById("esi").value;
    var role = document.getElementById("role").value;
    var meeting = document.getElementById("meeting").value;
    var email = document.getElementById("email").value;
    var notes = document.getElementById("notes").value;
    var title = document.getElementById("paperName").value;
    var section = document.getElementById("section").value;
    var citation = document.getElementById("citation").value;
    var uid = document.getElementById("uid").value;
    var id = document.getElementById("pid").value;

    if(title === null || title === ''){
        alert("请填写论文名称");
        return;
    }
    if(citation === null || citation === ''){
        alert("请填写被引次数");
        return;
    }
    if(meeting === null || meeting === ''){
        alert("请填写会议名称");
        return;
    }
    if((id === null || id <= 0 ) && (email === null || email === '')){
        alert("请填写著者邮箱");
        return;
    }
    if(role === null || role === ''){
        alert("请选择这著者身份");
        return;
    }
    if(notes === null){
        notes = '';
    }
    let data0 = {
        "title": title,
        "esi": esi,
        "role": role,
        "email":email,
        "notes": notes,
        "citation": citation,
        "section": section,
        "meeting": meeting,
        "id":id,
        "uid":uid
    };
    var url = "./addPaper.do";
    if(id > 0){
        url = "./updatePaper.do";
    }
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

function searchJournal() {
    var title = document.getElementById("journalName").value;
    if (title === null || title === "") {
        alert("请输入期刊关键字");
        return;
    }
    document.getElementById("btn_search").innerHTML = "搜索中...";
    document.getElementById("btn_search").setAttribute("disabled", true);
    let data0 = {
        "title": title
    };
    var url = "./addJournal";
    window.location.href = url + '?title=' + title;
}

function saveJournal(title) {
    let data0 = {
        "code": title
    };
    var url = "./addJournal.do";
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

