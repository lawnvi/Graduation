
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

function addPaper() {
    var name = document.getElementById("updateName").value;
    var role = document.getElementById("updateRole").value;
    var id = document.getElementById("updateId").value;
    var notes = document.getElementById("updateNotes").value;
    var conference = document.getElementById("updateMeeting").value;
    var citation = document.getElementById("updateCitation").value;
    var section = document.getElementById("updateSection").value;
    var isEsi = document.getElementById("isEsiChecked").checked;
    console.log(isEsi);
    if(name === ""){
        alert("请输入会议论文名称");
        return;
    }
    if(role === ""){
        alert("请选择作者类别");
        return;
    }
    if(conference === ""){
        alert("请输入会议名称");
        return;
    }
    if(section === ""){
        alert("请选择分区");
        return;
    }
    if(citation === ""){
        alert("请输入被引次数");
        return;
    }
    let datas = {
        "name": name,
        "role": role,
        "conference": conference,
        "id":id,
        "notes":notes,
        "citation":citation,
        "section":section,
        "isEsi":isEsi
    };
    if(id > 0)
        url = './updateConferencePaper';
    else
        url = './addConferencePaper';
    $("#myModal").modal('hide');
    postAjax(datas, url);
}

function deletePapers() {
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
    if(!confirm("确定删除选中的专利吗?")){
        return;
    }
    let dates = {
        "id": array
    };
    $.ajax(
        {
            url: './deleteConferencePaper',//这里是写controller中requestMapping中的路径
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

function transportData(ID, name, meeting, section, citation, isEsi, role, notes) {
    $("#updateId").val(ID);
    $("#updateName").val(name);
    $("#updateRole").val(role);
    $("#updateSection").val(section);
    $("#updateMeeting").val(meeting);
    $("#updateCitation").val(citation);
    console.log(isEsi);
    if(isEsi === "true") {
        $("#isEsiChecked").attr('checked', true);
        console.log("1");
    }
    else {
        $("#isEsiChecked").attr("'checked'", false);
        console.log("2");
    }
    $("#updateNotes").val(notes);
}