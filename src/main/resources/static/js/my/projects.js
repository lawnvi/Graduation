function postAjax(datas, url) {
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

function addProject() {
    var title = document.getElementById("updateName").value;
    // var role = document.getElementById("updateRole").value;
    var id = document.getElementById("updateId").value;
    var notes = document.getElementById("updateNotes").value;
    var funds = document.getElementById("updateFunds").value;
    if(title === ""){
        alert("请输入主持的项目名称");
        return;
    }
    if(funds === ""){
        alert("请输入主持项目经费(单位:万元)");
        return;
    }
    let datas = {
        "name": title,
        // "role": role,
        "funds": funds,
        "id":id,
        "notes":notes
    };
    if(id > 0)
        url = './updateProject';
    else
        url = './addProject';
    $("#myModal").modal('hide');
    postAjax(datas, url);
}

function deleteProjects() {
    var id = document.getElementsByName("checkedId");
    var array = new Array();
    var op = 0;
    for (i = 0; i < id.length; i++){

        if(id[i].checked){
            array[op] = id[i].value;
            console.log(array);
            op++;
        }
    }
    if(op === 0)
        return;
    if(!confirm("确定删除选中的项目吗?")){
        return;
    }
    let dates = {
        "id": array
    };
    $.ajax(
        {
            url: './deleteProjects',//这里是写controller中requestMapping中的路径
            type: 'POST',//通过get或者post在发送请求
            datatype: "json",//这是数据的格式，可以是String、json、xml等
            async: true,//同步或异步，如果有多个ajax请求，则设成false
            data: dates,//传给后台的参数，也可以手动写成json或其他格式，这里提前在上面创建一个json对象，如果不想这样写，也可以手动{id:<%= user.getId()%>}写一个匿名对象传过去
            success: function(data){//回调函数，如果请求成功，则会调用success方法
                if(data === "ok"){
                    window.location.reload();
                }else {
                    alert("删除失败");
                }

            },
            error: function(){//如果失败则会调用error方法
                alert("未知网络错误");
            }
        }
    )
}

function transportData(ID, name, funds, notes) {
    $("#updateId").val(ID);
    $("#updateName").val(name);
    if(notes !== "null")
        $("#updateNotes").val(notes);
    $("#updateFunds").val(funds);
}

function claimProject(id, action) {
    let data0 = {
        "pid": id,
        "action": action
    };
    var url = "./claimProject";
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