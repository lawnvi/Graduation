function transportData(date, place, advice, score, id, rid, status) {
    console.log(id);
    console.log(rid);
    console.log(status);
    var date2 = new Date(date);
    $("#new_date").val(date2);
    $("#new_place").val(place);
    $("#new_advice").val(advice);
    $("#new_score").val(score);
    $("#new_status").val(status);
    $("#new_id").val(id);
    $("#new_rid").val(rid);
}

function newTransportData(rid) {
    console.log(rid);
    $("#new_rid").val(rid);
}

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

function addInterview() {
    var date = document.getElementById("new_date").value;
    var place = document.getElementById("new_place").value;
    var advice = document.getElementById("new_advice").value;
    var score = document.getElementById("new_score").value;
    var id = document.getElementById("new_id").value;
    var rid = document.getElementById("new_rid").value;
    var status = document.getElementById("new_status").value;
    if(date === ""){
        alert("请选择面试时间");
        return;
    }
    if(place === ""){
        alert("请选择面试地点");
        return;
    }
    if(status === "待评判" && score > 0){
        alert("请确认面试是否已结束，当前状态为待评判");
        return;
    }

    let datas = {
        "date": date,
        "place": place,
        "advice": advice,
        "id":id,
        "score":score,
        "rid":rid,
        "status":status
    };
    if(id > 0)
        url = './updateInterview';
    else
        url = './addInterviewPlan';
    $("#myModal").modal('hide');
    postAjax(datas, url);
}

function isAccept(rid, flag) {
    var url = "./pass";
    let data = {
        "rid" : rid,
        "flag" : flag
    }
    postAjax(data, url);
}