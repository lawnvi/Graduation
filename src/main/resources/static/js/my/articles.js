
function postAjax(datas, url) {
    $.ajax(
        {
            url: url,//这里是写controller中requestMapping中的路径
            type: 'POST',//通过get或者post在发送请求
            datatype: "json",//这是数据的格式，可以是String、json、xml等
            async: true,//同步或异步，如果有多个ajax请求，则设成false
            data: datas,//传给后台的参数，也可以手动写成json或其他格式，这里提前在上面创建一个json对象，如果不想这样写，也可以手动{id:<%= user.getId()%>}写一个匿名对象传过去
            success: function(data){//回调函数，如果请求成功，则会调用success方法
                if(data){

                    window.location.reload();
                }else {
                    alert("未找到"+datas.title+"请检查是否正确输入，尝试更新或手动输入相关信息");
                    window.location.reload();
                }

            },
            error: function(){//如果失败则会调用error方法
                alert("未知网络错误");
            }
        }
    )
}

function addArticle() {
    var title = document.getElementById("articleName").value;
    var role = document.getElementById("role").value;
    var notes = document.getElementById("articleNotes").value;

    if(title === ""){
        alert("请输入题目");
        return;
    }
    if(role === ""){
        alert("请选择作者类别");
        return;
    }
    let datas = {
        "title": title,
        "role": role,
        "notes": notes
    };
    var url = './addArticle';
    $("#myModal").modal('hide');
    postAjax(datas, url);
}

function deleteArticles() {
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
    if(!confirm("确定删除选中的论文吗?")){
        return;
    }
    let dates = {
        "id": array
    };
    $.ajax(
        {
            url: './deleteUserArticles',//这里是写controller中requestMapping中的路径
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

function transportData(ID, name, journal, notes, role) {
    $("#updateId").val(ID);
    $("#updateName").val(name);
    $("#updateJournal").val(journal);
    if(notes !== "null")
        $("#updateNotes").val(notes);
    $("#updateRole").val(role);
    // $("#sel option[value='xx']").prop("selected",true);
}

function updateArticles() {
    var name = document.getElementById("updateName").value;
    var id = document.getElementById("updateId").value;
    var journal = document.getElementById("updateJournal").value;
    var role = document.getElementById("updateRole").value;
    var notes = document.getElementById("updateNotes").value;
    var isAutoFill = $("#isAutoFill").value;
    if(isAutoFill === false){
        var isEsi = document.getElementById("isEsiChecked").value;
        var citation = document.getElementById("addCitation").value;
    }

    if(name === ""){
        alert("请输入题目");
        return;
    }
    if(role === ""){
        alert("请选择作者类别");
        return;
    }


    let dates = {
        "id": id,
        "title": name,
        "journal": journal,
        "role": role,
        "notes": notes,
        "isEsi":isEsi,
        "citation":citation
    };

    if(isAutoFill){
        var url = './updateArticle';
    }
    else {
        url = './addArticleByHand';
        $("#isAutoFill").val(true);
        // $("#isEsiGroup").setAttribute('hidden', 'true');
        // $("#citationGroup").setAttribute('hidden', 'true');
    }
    if(id === "" && isAutoFill){
        alert("逻辑错误");
    }
    $("#myModal2").modal('hide');
    $.ajax(
        {
            url: url,//这里是写controller中requestMapping中的路径
            type: 'POST',//通过get或者post在发送请求
            datatype: "json",//这是数据的格式，可以是String、json、xml等
            async: true,//同步或异步，如果有多个ajax请求，则设成false
            data: dates,//传给后台的参数，也可以手动写成json或其他格式，这里提前在上面创建一个json对象，如果不想这样写，也可以手动{id:<%= user.getId()%>}写一个匿名对象传过去
            success: function(data){//回调函数，如果请求成功，则会调用success方法
                if(data === "ok"){
                    window.location.reload();
                }else {
                    alert("操作失败");
                }

            },
            error: function(){//如果失败则会调用error方法
                alert("未知网络错误");
            }
        }
    )
}

function showEsi() {
    $("#isEsiGroup").removeAttr('hidden');
    $("#citationGroup").removeAttr('hidden');
    transportData();
    $("#isAutoFill").val(false);
}