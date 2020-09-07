package com.buct.graduation.util.spider;

import com.buct.graduation.model.pojo.Article;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GetArticlesByAddress {
    public String keyword = "Beijing+Univ+Chem+Technol%2C+Coll+Informat+Sci+%26+Technol";
    public String url_1 = "WOS_GeneralSearch.do?fieldCount=1&action=search&product=WOS&search_mode=GeneralSearch&max_field_count=25&max_field_notice=%E6%B3%A8%E6%84%8F%3A+%E6%97%A0%E6%B3%95%E6%B7%BB%E5%8A%A0%E5%8F%A6%E4%B8%80%E5%AD%97%E6%AE%B5%E3%80%82&input_invalid_notice=%E6%A3%80%E7%B4%A2%E9%94%99%E8%AF%AF%3A+%E8%AF%B7%E8%BE%93%E5%85%A5%E6%A3%80%E7%B4%A2%E8%AF%8D%E3%80%82&exp_notice=%E6%A3%80%E7%B4%A2%E9%94%99%E8%AF%AF%3A+%E4%B8%93%E5%88%A9%E6%A3%80%E7%B4%A2%E8%AF%8D%E5%8F%AF%E4%BB%A5%E5%9C%A8%E5%A4%9A%E4%B8%AA%E5%AE%B6%E6%97%8F%E4%B8%AD%E6%89%BE%E5%88%B0+%28&input_invalid_notice_limits=+%3Cbr%2F%3E%E6%B3%A8%E6%84%8F%3A+%E6%BB%9A%E5%8A%A8%E6%A1%86%E4%B8%AD%E6%98%BE%E7%A4%BA%E7%9A%84%E5%AD%97%E6%AE%B5%E5%BF%85%E9%A1%BB%E8%87%B3%E5%B0%91%E4%B8%8E%E4%B8%80%E4%B8%AA%E5%85%B6%E4%BB%96%E6%A3%80%E7%B4%A2%E5%AD%97%E6%AE%B5%E7%9B%B8%E7%BB%84%E9%85%8D%E3%80%82&sa_params=WOS%7C%7C5DJLtMKTUZFzkyOi7Da%7Chttp%3A%2F%2Fapps.webofknowledge.com%7C%27&formUpdated=true&value%28input1%29="+ keyword +"&value%28select1%29=AD&value%28hidInput1%29=&limitStatus=collapsed&ss_lemmatization=On&ss_spellchecking=Suggest&SinceLastVisit_UTC=&SinceLastVisit_DATE=&period=Range+Selection&range=ALL&startYear=2000&endYear=2020&editions=SCI&editions=ISTP&editions=CCR&editions=IC&update_back2search_link_param=yes&ssStatus=display%3Anone&ss_showsuggestions=ON&ss_numDefaultGeneralSearchFields=1&ss_query_language=&rs_sort_by=PY.D%3BLD.D%3BSO.A%3BVL.D%3BPG.A%3BAU.A&SID=";
    public String wosUrl = "http://apps.webofknowledge.com/";
    public static String path = "/image/";
    public volatile static int ops = 0;
    public List<Article> articleList = new ArrayList<>();
    public static List<String> title = new ArrayList<>();
    public static List<String> journal10 = new ArrayList<>();
    //    public static int now = 2017;
    public synchronized void addPaper(){
        ops++;
        System.out.println("####"+ops+"####");
    }

    public static void main(String[] args){
//        title.add("论文");
//        title.add("期刊");
//        title.add("isTop");
//        title.add("影响因子");
//        title.add("他引次数");
//        title.add("通讯作者");
//        title.add("作者");
//        title.add("isESI");
//        title.add("url");
//        title.add("year");
//        title.add("分区");
//        title.add("备注");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    getArticles(url_1, 2019);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                long s = System.currentTimeMillis();
//                while (ops < 357){
//                    try {
//                        Thread.sleep(10000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                long m = System.currentTimeMillis();
//                System.out.println("spider time:"+(m-s)/1000+"s");
//                try {
////                    writeExcel("D:\\temp\\excel\\2017年olig.xls", "2017年olig", ".xls", title, list17);
////                    writeExcel("D:\\temp\\excel\\2018年olig.xls", "2018年olig", ".xls", title, list18);
////                    writeExcel("D:\\temp\\excel\\2019年olig.xls", "2019年olig", ".xls", title, list19);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                long e = System.currentTimeMillis();
//                System.out.println("all time:"+(e-s)/1000+"s");
//            }
//        }).start();
    }


    public GetArticlesByAddress(){
        this.articleList = Collections.synchronizedList(this.articleList);
        title.add("论文");
        title.add("期刊");
        title.add("isTop");
        title.add("影响因子");
        title.add("他引次数");
        title.add("通讯作者");
        title.add("作者");
        title.add("isESI");
        title.add("url");
        title.add("year");
        title.add("分区");
        title.add("备注");

        journal10.add("SCIENCE ROBOTICS");
        journal10.add("IEEE TRANSACTIONS ON PATTERN ANALYSIS AND MACHINE INTELLIGENCE");
        journal10.add("ARTIFICIAL INTELLIGENCE");
        journal10.add("INTERNATIONAL JOURNAL OF COMPUTER VISION");
        journal10.add("JOURNAL OF MACHINE LEARNING RESEARCH");
        journal10.add("IEEE TRANSACTIONS ON IMAGE PROCESSING");
        journal10.add("IEEE TRANSACTIONS ON CYBERNETICS");
        journal10.add("IEEE TRANSACTIONS ON NEURAL NETWORKS AND LEARNING SYSTEMS");
        journal10.add("IEEE TRANSACTIONS ON INTELLIGENT TRANSPORTATION SYSTEMS");
        journal10.add("PATTERN RECOGNITION");
    }

    //todo add threadPool & listPool 待定
    public List<Article> getArticles(String keyword, int year){
        this.keyword = keyword;
        ops=0;
        List<Article> articles = new ArrayList<>();
        String SID = SpiderConfig.getSID();
        HttpUtil httpUtil = new HttpUtil();
        String html_1 = httpUtil.getHtml(wosUrl + url_1 + SID);
        if(html_1 == null || html_1.equals(""))
            return null;
//        DocumentType_1
        Document d1 = Jsoup.parse(html_1);// 转换为Dom树
        String qid = "";
        for(Element e : d1.getAllElements()){
            if(e.attr("name").equals("summary_navigation.sort")){
                for(Element element : e.getAllElements()) {
                    if(element.attr("name").equals("qid")) {
                        qid = element.attr("value");
                        System.out.println("qid:" + qid);
                        break;
                    }
                }
                break;
            }
        }
        System.out.println("qidBreak:" + qid);

        //年份 文献类型
        String url_2 = wosUrl+"Refine.do?update_back2search_link_param=yes&parentQid="+qid+"&SID="+SID+"&product=WOS&databaseId=WOS&colName=WOS&service_mode=Refine&search_mode=GeneralSearch&action=search&clickRaMore=%E5%A6%82%E6%9E%9C%E7%BB%A7%E7%BB%AD%E4%BD%BF%E7%94%A8+&openCheckboxes=%E5%A6%82%E6%9E%9C%E9%9A%90%E8%97%8F%E5%B7%A6%E4%BE%A7%E9%9D%A2%E6%9D%BF%EF%BC%8C%E5%88%99%E5%85%B6%E4%B8%AD%E7%9A%84%E7%B2%BE%E7%82%BC%E9%80%89%E6%8B%A9%E5%B0%86%E4%B8%8D%E4%BC%9A%E4%BF%9D%E5%AD%98%E3%80%82&refineSelectAtLeastOneCheckbox=%E8%AF%B7%E8%87%B3%E5%B0%91%E9%80%89%E4%B8%AD%E4%B8%80%E4%B8%AA%E5%A4%8D%E9%80%89%E6%A1%86%E6%9D%A5%E7%B2%BE%E7%82%BC%E6%A3%80%E7%B4%A2%E7%BB%93%E6%9E%9C%E3%80%82&queryOption%28sortBy%29=PY.D%3BLD.D%3BSO.A%3BVL.D%3BPG.A%3BAU.A&queryOption%28ss_query_language%29=auto&sws=&defaultsws=%E5%9C%A8%E5%A6%82%E4%B8%8B%E7%BB%93%E6%9E%9C%E9%9B%86%E5%86%85%E6%A3%80%E7%B4%A2...&swsFields=TS&swsHidden=%E5%9C%A8%E5%89%8D+100%2C000+%E6%9D%A1%E7%BB%93%E6%9E%9C%E5%86%85%3Cbr%3E%E6%A3%80%E7%B4%A2&exclude=&exclude=&exclude=&refineSelection=PublicationYear_"+year+"&exclude=&exclude=&refineSelection=DocumentType_ARTICLE&exclude=&exclude=&exclude=&exclude=&exclude=&exclude=&exclude=&exclude=&exclude=&exclude=&exclude=&exclude=&exclude=&mode=refine";
        String html_2 = httpUtil.getHtml(url_2);
        if(html_2 == null || html_2.equals(""))
            return null;
//        refine_form
        Document d2 = Jsoup.parse(html_2);// 转换为Dom树
        Element parentIdForm = d2.getElementById("refine_form");
        String parentQid = "";
        for(Element e : parentIdForm.getAllElements()){
            if(e.attr("name").equals("parentQid")){
                parentQid = e.attr("value");
                System.out.println("parentQid:"+parentQid);
                break;
            }
        }
        String qid2 = "";
        for(Element e : d2.getAllElements()){
            if(e.attr("name").equals("summary_navigation.sort")){
                for(Element element : e.getAllElements()) {
                    if(element.attr("name").equals("qid")) {
                        qid2 = element.attr("value");
                        System.out.println("qid:" + qid2);
                        break;
                    }
                }
                break;
            }
        }
        //换pagesize=50
//        String url_pageSize = wosUrl+"summary.do?product=WOS&parentProduct=WOS&search_mode=GeneralSearch&qid="+qid+"&SID="+SID+"&&page=1&action=changePageSize&pageSize=50";
//        httpUtil.getHtml(url_pageSize);
        System.out.println("开始");
        for(int page = 1; ; page++) {
            String url_page = wosUrl + "summary.do?product=WOS&parentProduct=WOS&search_mode=GeneralSearch&parentQid="+parentQid+"&qid=" + qid2 + "&SID=" + SID + "&colName=WOS&&&update_back2search_link_param=yes&page=" + page;
            String pageHtml = httpUtil.getHtml(url_page);
            if(pageHtml == null || pageHtml.equals(""))
                return null;
            Document d3 = Jsoup.parse(pageHtml);// 转换为Dom树
            List<Element> e3 = d3.getElementsByClass("smallV110 snowplow-full-record");
            System.out.println("pageSize:" + e3.size());
            List<String> urls = new ArrayList<>();
            for (Element e : e3) {
                if (e.attr("href").startsWith("http:"))
                    urls.add(e.attr("href"));
                else
                    urls.add(wosUrl + e.attr("href"));
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getDetail(urls, year);
                }
            }).start();
//            articles.addAll(getDetail(urls, year));
            if (e3.size()%10 != 0) {
                //todo 开始写excel 不用具体数值或者爬下论文总数作为停止数值
                while (ops != 10 * (page-1) + e3.size()){
                    System.out.println(10 * (page-1) + e3.size()+ " "+ops);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                /*
                for(Article article: articleList){
                    SpiderLetpub letpub = new SpiderLetpub();
                    PeriodicalTable table = letpub.getPeriodicals(article.getJournal());
                    if(table.getNumber() == 0){
                        System.out.println("not find journal "+ article.getName()+"\n论文"+article.getJournal());
                    }
                    for(Periodical p : table.getList()){
                        if(p.isMatch()){
                            Journal journal = letpub.getJournalData(p.getUrl());
//                articles.setJid();
                            article.setUrl(journal.getUrl());
                            article.setJournal(journal.getName());
                            article.setIF(journal.getIF());
                            article.setTop(journal.getTop());
                            article.setSection(journal.getSection());
                            article.setNotes(journal.getNotes());
                        }
                    }
                }
                try {
                    write2Excel(year, articleList);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                return articleList;
            }
        }

//        return articles;
    }

    //    public static List<Article> getDetail(List<String> urls, int nowYear){
    public void getDetail(List<String> urls, int nowYear){
        HttpUtil util = new HttpUtil();
        List<Article> articles = new ArrayList<>();
        for (String url: urls) {
/*            String html3 = util.getHtml(url);
            if(html3 == null || html3.equals(""))
                return;
            int number = 0;
            boolean isESI = false;
            String year = "";
            List<String> authors = new ArrayList<>();
            String author = "";
            String Journal = "";
            String name = "";
            String CAuthor = "";
            System.out.println("搜索完成3");
            Document d3 = Jsoup.parse(html3);// 转换为Dom树
            name = d3.select("div.title").first().text();
            System.out.println("title:"+name);
            List<Element> elements = d3.getElementsByClass("FR_field");
            for(Element element : elements){
                if(element.children().size() > 0){
                    if(element.child(0).text().equals("出版年:") || element.child(0).text().equals("Published:")){
                        year = element.child(1).text();
                        System.out.println("find year"+year);
                    }
                    else if(element.child(0).text().equals("通讯作者地址:") || element.child(0).text().equals("Reprint Address:")){
                        CAuthor = element.text().substring(17, element.text().length() - 16);
                        System.out.println("find year"+CAuthor);
                        break;
                    }
                }
            }
            if(year.equals("")){
                Element element = d3.getElementsByClass("hitHilite").first();
                if(element != null){
                    year = element.text();
                }
            }
            List<Element> authorsE = d3.getElementsByClass("FR_field");
            author = authorsE.get(0).text();

            Element information = d3.getElementsByClass("block-record-info block-record-info-source").first();
            for(Element element: information.getAllElements()){
                if(element.attr("class").equals("sourceTitle")){
                    Journal = element.text();
                    System.out.println("find j:"+Journal);
                    break;
                }
            }

            List<Element> e3 = d3.select("#sidebar-column1");
            for (Element e3e : d3.getAllElements()) {
                if(e3e.attr("class").equals("flex-row-partition1")){
                    for(Element e3ee : e3e.getAllElements()){
                        if(e3ee.attr("class").equals("large-number")){
                            number = Integer.parseInt(e3ee.text());
                            System.out.println("被引次数:"+e3ee.text());
                            break;
                        }
                    }
                }
                else if(e3e.attr("class").equals("flex-row-partition2")){
                    System.out.println("isESI:true");
                    isESI = true;
                    break;
                }
            }

//            reprint author
            year = year.substring(year.length()-4);
            Article article = new Article();
            article.setName(name);//论文
            article.setCitation(number);//被引
            article.setESI(isESI);//esi
            article.setYear(year);//年份
            article.setCAuthor(CAuthor);
            author = Utils.getNames(author);
            article.setAuthor(author);
            article.setJournalIssn(Journal);
            article.setUrl(url);*/

//            if(!journal10.contains(Journal.toUpperCase())){
//                addPaper();
//                System.out.println("not jour10");
//                continue;
//            }
            SpiderWOS wos = new SpiderWOS();
            Article article = wos.getArticle(url);
            SpiderAPI api = new SpiderAPI();
            //todo biubiubiu search by database, if null use spider
            SpiderLetpubJournal letpub = new SpiderLetpubJournal();
            article.setJournal(letpub.getJournal(article.getJournalIssn()));
            /*PeriodicalTable table = letpub.getPeriodicals(Journal);
            if(table.getNumber() == 0){
                System.out.println("not find journal "+ article.getName()+"\n论文"+article.getJournal().getName());
            }
            for(Periodical p : table.getList()){
                if(p.isMatch()){
                    Journal journal = letpub.getJournalData(p.getUrl());
                    article.setJournal(journal);
//                articles.setJid();
//                    article.setUrl(journal.getUrl());
//                    article.setJournalN(journal.getName());
//                    article.setIF(journal.getIF());
//                    article.setTop(journal.getTop());
//                    article.setSection(journal.getSection());
//                    article.setNotes(journal.getNotes());
                }
            }*/
//            System.out.println("year:"+year+"被引次数:"+number+"Top:"+article.getJournal().getTop()+"IF:"+article.getJournal().getIF()+"T:"+name+"J:"+Journal);
            articles.add(article);
            addPaper();
//            System.out.println("in jour10");
            articleList.add(article);
        }
//        return articles;
    }

    /**
     * 写操作 -- 参数为封装的实体对象
     * @param pathName 文件路径
     * @param sheetName 表格名称
     * @param style .xls/.xlsx文件类别
     * @param titles 表头信息
     * @param datas 表格信息
     * @return
     */
    public static synchronized Workbook writeExcel(
            String pathName, String sheetName, String style, List<String> titles, List<Article> datas
    ) throws Exception {
        Workbook workbook;
        if (".XLS".equals(style.toUpperCase())) {
            workbook = new HSSFWorkbook();
        } else {
            workbook = new XSSFWorkbook();
        }
        // 生成一个表格
        Sheet sheet = workbook.createSheet(sheetName);
        Row row = sheet.createRow(0);
        /**
         * 创建表头信息
         */
        for (int i = 0; i < titles.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(titles.get(i));
        }
        /**
         * 创建表格信息
         */
        Iterator<Article> iterator = datas.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            index++;
            row = sheet.createRow(index);
            Article aArticle = iterator.next();
            //实体对象属性个数
//            title.add("论文");
//            title.add("期刊");
//            title.add("isTop");
//            title.add("影响因子");
//            title.add("他引次数");
//            title.add("通讯作者");
//            title.add("作者");
            int length = aArticle.getClass().getDeclaredFields().length;
            System.out.println("Article类属性数量为："+length);
            for (int i = 0; i < length ; i++) {
                Cell cell = row.createCell(i);
                //依次对应实体对象的属性
                switch (i){
                    case 0 :
                        cell.setCellValue(aArticle.getName());
                        break;
                    case 1 :
                        cell.setCellValue(aArticle.getJournalIssn());
                        break;
                    case 2 :
                        cell.setCellValue(aArticle.getJournal().getIs_top());
                        break;
                    case 3 :
                        cell.setCellValue(aArticle.getJournal().getIF());
                        break;
                    case 4 :
                        cell.setCellValue(aArticle.getCitation());
                        break;
                    case 5 :
                        cell.setCellValue(aArticle.getCAuthor());
                        break;
                    case 6 :
                        cell.setCellValue(aArticle.getAuthor());
                        break;
                    case 7 :
                        cell.setCellValue(aArticle.getESI());
                        break;
                    case 8 :
                        cell.setCellValue(aArticle.getUrl());
                        break;
                    case 9 :
                        cell.setCellValue(aArticle.getYear());
                        break;
                    case 10 :
                        cell.setCellValue(aArticle.getJournal().getSection());
                        break;
                    case 11 :
                        cell.setCellValue(aArticle.getNotes()+aArticle.getJournal().getNotes());
                        break;
                    default:
                        System.out.println("【异常】Article类属性数量为："+length+" | i="+i);
                        break;
                }
            }
        }
        /**
         * 写入到文件中
         */
        /*
        boolean isCorrect = false;
        File file = new File(pathName);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            isCorrect = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != outputStream) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        */

        return workbook;
    }

    public static List<Article> articleList7 = new ArrayList<>();
    public static List<Article> articleList8 = new ArrayList<>();
    public static List<Article> articleList9 = new ArrayList<>();

    public synchronized static void add2List(List<Article> articles, int year){
        switch (year){
            case 2017 : articleList7.addAll(articles);break;
            case 2018 : articleList8.addAll(articles);break;
            case 2019 : articleList9.addAll(articles);break;
            default:break;
        }

    }

}
