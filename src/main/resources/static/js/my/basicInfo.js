// $("#education").val($("#graduationValue").value);
// $("#status").val($("#statusValue").value);

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
                    alert("修改成功");
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

function updateInfo() {
    var name = document.getElementById("name").value;
    // var role = document.getElementById("updateRole").value;
    // var id = document.getElementById("user").value;
    var notes = document.getElementById("notes").value;
    var sexRadio = document.getElementById("sex");
    if(sexRadio.checked === true){
        var sex = '女';
    }else {
        sex = '男';
    }
    var email = document.getElementById("email").value;
    var tel = document.getElementById("tel").value;
    var birthday = document.getElementById("birthday").value;
    var address = document.getElementById("contactAddress").value;
    var education = document.getElementById("education").value;
    var major = document.getElementById("major").value;
    var status = document.getElementById("status").value;
    var title = document.getElementsByName("title");
    var fund = document.getElementsByName("funds");
    var resumePath = document.getElementById("resumePath").value;

    var titles = [];
    var op = 0;
    for (i = 0; i < title.length; i++){
        if(title[i].checked){
            titles[op] = title[i].value;
            op++;
            // console.log(titles);
            // console.log(array);
        }
    }
    var funds = [];
    op = 0;
    for (i = 0; i < fund.length; i++){
        if(fund[i].checked){
            funds[op] = fund[i].value;
            op++;
            // console.log(funds);
        }
    }

    let datas = {
        "name": name,
        "sex": sex,
        "funds": funds,
        "titles":titles,
        "notes":notes,
        "birthday": birthday,
        "contactAddress":address,
        "major":major,
        "tel":tel,
        "education":education,
        "status":status,
        "resumePath":resumePath
    };
    url = './updateBasicInfo.do';
    var formData = new FormData();
    if(document.getElementById("inputGroupFile01") !== null) {
        formData.append("file", $("#inputGroupFile01")[0].files[0]);
    }
    formData.append("picture", $("#img_upload")[0].files[0]);
    formData.append("name", name);
    formData.append("sex", sex);
    formData.append("funds", funds.toString());
    formData.append("titles", titles.toString());
    formData.append("notes", notes);
    formData.append("birthday", birthday);
    formData.append("contactAddress", address);
    formData.append("major", major);
    formData.append("tel", tel);
    formData.append("education", education);
    formData.append("status", status);
    formData.append("resumePath", resumePath);

    $.ajax({
        type: 'post',
        url: url,
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        success: function(data){//回调函数，如果请求成功，则会调用success方法
            if(data) {
                alert("修改成功");
                window.location.reload();
            }else {
                alert("操作失败");
            }
        },
        error: function(){//如果失败则会调用error方法
            alert("未知网络错误");
        }
    })

//    postAjax(datas, url);
}
