function search() {
    var title = document.getElementById("input_name").value;
    var kind = document.getElementById("select-category").value;
    if(title === ''){
        document.getElementById("input_name").setAttribute('placeholder', '请输入题目');
        return;
    }
    if(kind === ''){
        alert("请选择类别");
        return;
    }
    var url;
    if(kind === 'article'){
        url = './searchArticle.do'
    }
    if(kind === 'journal'){
        url = './searchJournal.do'
    }
    let datas = {
        'title':title
    };
    document.getElementById("btn_search").setAttribute("disabled", true);
    $.ajax(
        {
            url: url,//这里是写controller中requestMapping中的路径
            type: 'POST',//通过get或者post在发送请求
            datatype: "json",//这是数据的格式，可以是String、json、xml等
            async: true,//同步或异步，如果有多个ajax请求，则设成false
            data: datas,//传给后台的参数，也可以手动写成json或其他格式，这里提前在上面创建一个json对象，如果不想这样写，也可以手动{id:<%= user.getId()%>}写一个匿名对象传过去
            success: function(data){//回调函数，如果请求成功，则会调用success方法
                document.getElementById("btn_search").removeAttribute("disabled");
                if(data) {
                    // window.location.reload();
                    if(kind === 'article')
                        fillArticle(data);
                    if(kind === 'journal')
                        fillJournals(data)
                }else {
                    alert("未找到相关数据");
                }
            },
            error: function(){//如果失败则会调用error方法
                alert("未知网络错误");
                document.getElementById("btn_search").removeAttribute("disabled");
            }
        }
    )
}

function fillArticle(data) {
    if(data === null || data === 'null' ||data === ''){
        alert("未找到相关数据");
        return;
    }
    console.log(data);
    if(data['name'].startsWith("没有找到")){
        alert("未找到相关数据, 但有部分可能匹配但未识别");
        window.open(data['url'], 'newwindow','height=100, width=400, top=0, left=0, toolbar=no, menubar=no,scrollbars=no, resizable=no,location=no, status=no');
    }else {
        //match
        // $("#journal_name").setAttribute('title', data['journal']['name']);
        var journalName = document.getElementById("journal_name");
        journalName.setAttribute('title', data['journal']['name']);
        // journalName.innerHTML(data['journal']['AbbrTitle']+' ISSN:'+data['journal']['issn']);
        journalName.setAttribute('href', data['journal']['url']);
        $("#article_title").text(data['name']);
        document.getElementById("article_title").setAttribute('href', data['url']);
        var author = document.getElementById("article_author");
        // author.setAttribute('title', data['author']);
        $("#article_author").text(data['author']);
        $("#article_cauthor").text(data['cauthor']);
        $("#article_time").text(data['year']);
        $("#citation").text('被引次数:' + data['citation']);
        // $("#isesi").text('Esi高被引:' + data['isESI']);
        if (data['esi'] === true) {
            $("#isesi").removeAttr('hidden');
        }
        $("#journal_name").text(data['journal']['abbrTitle'] + ' ISSN:' + data['journal']['issn']);
        // $("#journal_name")['title'] = data['journal']['name'];
        // $("#journal_name")['text'] = data['journal']['AbbrTitle']+data['journal']['issn'];
        // $("#journal_name").setAttribute('text', data['journal']['AbbrTitle']+data['journal']['issn']);
        $("#journal_section").text(data['journal']['section']);
        var section = document.getElementById("journal_section");
        // section.innerHTML(data['journal']['section']);
        var top = document.getElementById("journal_top");
        $("#journal_IF").text(data['journal']['if']);
        // top.innerHTML(data['journal']['top']);
        // $("#journal_name")['href'] = data['journal']['url'];
        $("#journal_top").text(data['journal']['top']);

        $("#back_data").removeAttr('hidden');
    }
}

function fillJournals(data) {
    if(data === null || data === 'null' ||data === ''){
        alert("未找到相关数据");
        return;
    }
    console.log(data);
    //match
    // $("#journal_name").setAttribute('title', data[i]['name']);

    var html;
    for(var i = 0; i < data.length; i++){
        if(data[i]['notes'] !== '404'){
            console.log('find');
            var journalName = document.getElementById("journal_name");
            journalName.setAttribute('title', data[i]['name']);
            // journalName.innerHTML(data[i]['AbbrTitle']+' ISSN:'+data[i]['issn']);
            journalName.setAttribute('href', data[i]['url']);
            $("#article_title").text(data[i]['name']);
            $("#journal_name").text(data[i]['abbrTitle']+' ISSN:'+data[i]['issn']);
            // $("#journal_name")['title'] = data[i]['name'];
            // $("#journal_name")['text'] = data[i]['AbbrTitle']+data[i]['issn'];
            // $("#journal_name").setAttribute('text', data[i]['AbbrTitle']+data[i]['issn']);
            $("#journal_section").text(data[i]['section']);
            var section = document.getElementById("journal_section");
            // section.innerHTML(data[i]['section']);
            var top = document.getElementById("journal_top");
            // top.innerHTML(data[i]['top']);
            // $("#journal_name")['href'] = data[i]['url'];
            $("#journal_top").text(data[i]['top']);
            $("#journal_IF").text(data[i]['if']);
            $("#article_time").text(data[i]['notes']);

            $("#back_data").removeAttr('hidden');
            continue;
        }
        html +=
            "                    <div class='col-lg-12 mt-4 pt-2'>" +
            "                        <div class='job-list-box border rounded'>" +
            "                            <div class='p-3'>" +
            "                                <div class='row align-items-center'>" +
            "                                    <div class='col-lg-2'>" +
            "                                        <div class='company-logo-img'>" +
            "                                            <img src='../images/featured-job/img-1.png' alt='' class='img-fluid mx-auto d-block'>" +
            "                                        </div>" +
            "                                    </div>" +
            "                                    <div class='col-lg-7 col-md-9'>" +
            "                                        <div class='job-list-desc'>" +
            "                                            <h6 class='mb-2'><a href="+ data[i].url +" target='_blank' class='text-dark' >"+ data[i].name +"</a></h6>" +
            "                                            <p class='text-muted mb-0'><i class='mdi mdi-bank mr-2'></i>"+ data[i].abbrTitle+"</p>" +
            "                                            <ul class='list-inline mb-0'>" +
            "                                                <li class='list-inline-item mr-3'>" +
            "                                                    <p class='text-muted mb-0'><i class='mdi mdi-map-marker mr-2'></i>"+ data[i].issn +"</p>" +
            "                                                </li>" +
            "" +
            "                                                <li class='list-inline-item'>" +
            "<!--                                                    <p class='text-muted mb-0'><i class='mdi mdi-clock-outline mr-2'></i>1 Minute ago</p>-->" +
            "                                                </li>" +
            "                                            </ul>" +
            "                                        </div>" +
            "                                    </div>" +
            "                                    <div class='col-lg-3 col-md-3'>" +
            "                                        <div class='job-list-button-sm text-right'>" +
            "<!--                                            <span class='badge badge-success'>Full-Time</span>-->" +
            "" +
            "                                            <div class='mt-3'>" +
            "                                                <a href='"+ data[i].url +"' target='_blank' class='btn btn-sm btn-primary'>LetPub数据页</a>" +
            "                                            </div>" +
            "                                        </div>" +
            "                                    </div>" +
            "                                </div>" +
            "                            </div>" +
            "                        </div>" +
            "                    </div>";
    }
    // document.getElementById("showJournals").
    $("#showJournals").html(html);
}