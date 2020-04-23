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
                    window.location.reload();
                }
            },
            error: function(){//如果失败则会调用error方法
                alert("未知网络错误");
            }
        }
    )
}

function addPatent() {
    var title = document.getElementById("updateName").value;
    var role = document.getElementById("updateRole").value;
    var id = document.getElementById("updateId").value;
    var notes = document.getElementById("updateNotes").value;
    if(title === ""){
        alert("请输入专利名称");
        return;
    }
    if(role === ""){
        alert("请选择发明人类别");
        return;
    }
    var roleCheck = document.getElementById("customRadio1").checked;
    if(roleCheck){
        kind = "国内";
    }else {
        kind = "国际";
    }
    let datas = {
        "name": title,
        "role": role,
        "category": kind,
        "id":id,
        "notes":notes
    };
    if(id > 0)
        url = './updatePatent';
    else
        url = './addPatent';
    $("#myModal").modal('hide');
    postAjax(datas, url);
}

function deletePatents() {
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
    if(op === 0) {
        alert("请选中需要删除的选项");
        return;
    }
    if(!confirm("确定删除选中的专利吗?")){
        return;
    }
    let dates = {
        "id": array
    };
    $.ajax(
        {
            url: './deletePatents',//这里是写controller中requestMapping中的路径
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

function transportData(ID, name, kind, notes, role) {
    $("#updateId").val(ID);
    $("#updateName").val(name);
    if(kind === "国内")
        $("#customRadio1").prop("checked", "checked");
    else
        $("#customRadio2").prop("checked", "checked");
    if(notes !== "null")
        $("#updateNotes").val(notes);
    $("#updateRole").val(role);
}