package com.buct.graduation.util.spider;

import com.buct.graduation.model.pojo.Journal;
import com.buct.graduation.model.spider.Periodical;
import com.buct.graduation.model.spider.PeriodicalTable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * letPub 使用Journal保存数据
 */

public class SpiderLetpubJournal {

    public static void main(String[] args) {
        SpiderLetpubJournal letpub = new SpiderLetpubJournal();
//        letpub.getPeriodicals("IEEE ACCESS");
//        getIF("0169-7439");
//        String url = "http://www.letpub.com.cn/index.php?journalid=3407&page=journalapp&view=detail";
//        String url = "http://www.letpub.com.cn/index.php?journalid=10566&page=journalapp&view=detail";
//        String url = "http://www.letpub.com.cn/index.php?journalid=10017&page=journalapp&view=detail";
//        String url = "http://www.letpub.com.cn/index.php?journalid=6658&page=journalapp&view=detail";
        String url = "http://www.letpub.com.cn/index.php?journalid=3329&page=journalapp&view=detail";
        letpub.getJournal("0378-4371");
        List<String> kw = new ArrayList<>();
        kw.add("0378-4371");
        kw.add("FOREST ECOLOGY AND MANAGEMENT");
        for(String keyword: kw){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    letpub.getJournal(keyword);
                }
            }).start();
        }
    }

    public static String urlSearch = "http://www.letpub.com.cn/index.php?page=journalapp&view=search&searchissn=&searchfield=&searchimpactlow=&searchimpacthigh=&searchscitype=&view=search&searchcategory1=&searchcategory2=&searchjcrkind=&searchopenaccess=&searchsort=relevance&searchname=";
    public static String urlSearchISSN = "http://www.letpub.com.cn/index.php?page=journalapp&view=search&searchname=&searchfield=&searchimpactlow=&searchimpacthigh=&searchscitype=&view=search&searchcategory1=&searchcategory2=&searchjcrkind=&searchopenaccess=&searchsort=relevance&searchissn=";
    public static String urlIndex = "http://www.letpub.com.cn/";
    public static String urlProject = "http://www.letpub.com.cn/index.php?page=grant";

    public PeriodicalTable getPeriodicals(String keyword){
        PeriodicalTable table = new PeriodicalTable();
        String kw = null;
        try {
            kw = URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url2 = urlSearchISSN + keyword;
        String url1 = urlSearch + kw;
        table = searchPeriodical(url1, keyword);
        if((table == null || table.getList().size() == 0) && keyword.length() == 9 && keyword.charAt(4) == '-'){
            table = searchPeriodical(url2, keyword);
        }
        return table;
    }

    private PeriodicalTable searchPeriodical(String u, String keyword) {
        PeriodicalTable table = new PeriodicalTable();
        HttpUtil util = new HttpUtil();
        //todo 测试
        String html = util.getHtmlWithIp(u);

//        String html = util.getHtml(u);
        if(html == null || html.equals(""))
            return null;
        Document d1 = Jsoup.parse(html);// 转换为Dom树
//        List<Element> et = d1.select("#border:1px #DDD solid; border-collapse:collapse; text-align:left; padding:8px 8px 8px 8px;");
        int i = 0;
        String issn = "";
        String url = "";
        String title1 = "";
        String title2 = "";
//        Elements tr = d1.select("#yxyz_content > table.table_yjfx > tbody>tr");
        if(d1.select("#yxyz_content > table.table_yjfx > tbody > tr:nth-child(3) > td").text().startsWith("暂无匹配结果")){
            System.out.println("no match");
            return table;
        }
//        title1 = tr
        for (Element e : d1.getAllElements()) {
            if (e.attr("style").equals("border:1px #DDD solid; border-collapse:collapse; text-align:left; padding:8px 8px 8px 8px;")) {
                if (i % 12 == 0) {
                    System.out.println(e.text());
                    issn = e.text();//issn
                } else if (i % 12 == 1) {
                    for (Element element : e.getAllElements()) {
                        //color:#0099FF; font-size:12px; font-weight:bold; text-decoration:line-through;
                        if (element.attr("style").equals("color:#0099FF; font-size:12px; font-weight:bold; text-decoration:line-through;") || element.attr("style").equals("color:#0099FF; font-size:12px; font-weight:bold; text-decoration:underline;")) {
                            System.out.println(element.text());
                            url = urlIndex + element.attr("href");
                            title1 = element.text();
                        } else if (element.hasAttr("color")) {
                            System.out.println(element.text());
                            title2 = element.text();
                        }
                    }
                }
                i++;
                if (i % 12 == 0) {
                    System.out.println("sdg:" + i);
                    Periodical periodical = new Periodical();
                    periodical.setISSN(issn);//issn
                    periodical.setUrl(url);
                    periodical.setTitle(title1);
                    periodical.setAbbrTitle(title2);

                    if(issn.toUpperCase().equals(keyword.toUpperCase()) || title1.equalsIgnoreCase(keyword) || title2.equalsIgnoreCase(keyword)){
                        periodical.setMatch(true);
                    }else {
                        periodical.setMatch(false);
                    }
                    table.getList().add(periodical);
                    issn = "";
                    url = "";
                    title1 = "";
                    title2 = "";
                }
            }
            if (i >= 120) {
                break;
            }
        }
        table.setNumber(i / 12);
        System.out.println(table.getNumber());
//        for (int op = 0; op < table.getNumber(); op++) {
//            System.out.println("ISSN:" + table.getList().get(op).getISSN() +
//                    " Title:" + table.getList().get(op).getTitle() +
//                    " abbrTitle:" + table.getList().get(op).getAbbrTitle()+
//                    "ismatch:"+table.getList().get(op).isMatch());
//        }
        return table;
    }

    public Journal getJournalData(String url) {
        Journal journal = new Journal();
        journal.setUrl(url);
        HttpUtil util = new HttpUtil();
        //todo test
        String html = util.getHtmlWithIp(url);
//        String html = util.getHtml(url);

        if(html == null || html.equals("")){
            System.out.println("get joural data failing "+url);
            return getJournal(url);
        }
        Document document = Jsoup.parse(html);// 转换为Dom树
        Elements tr = document.select(" #yxyz_content > table.table_yjfx > tbody > tr");
//        if(tr == null){
//            tr = document.select("#yxyz_content > table.table_yjfx > tbody > tr");
//        }
        for (Element element : tr) {
            String text = element.select("td:nth-child(1)").text();
            if(text.equals("期刊名字")){
                journal.setName(element.select("tr:nth-child(2) > td:nth-child(2) > span:nth-child(1) > a").text());
                journal.setAbbrTitle(element.select("tr:nth-child(2) > td:nth-child(2) > span:nth-child(1) > font").text());
                String str = element.select("tr:nth-child(2) > td:nth-child(2) > span:nth-child(1) > span").text();
                if(str.contains("此期刊未被最新的JCR期刊引证报告收录")) {
                    journal.setNotes(str.substring(0, 21));
                }
            }
            else if(text.equals("期刊ISSN")){
                journal.setISSN(element.select("td:nth-child(2)").text());
                journal.setIF(getIF(journal.getISSN()));
            }
            else if (text.contains("中科院SCI期刊分区")) {

                //todo 未来可能变化
                String isNull = element.select("td:nth-child(2)").text();
                if(isNull.contains("（没有被最新的JCR基础版收录，仅供参考）")){
                    journal.setNotes(journal.getNotes()+isNull);
                    journal.setSection("not-JCR");
                }
                else {
                    String year = text.substring(13, 17);
                    String section = element.select("td:nth-child(2) > table > tbody > tr:nth-child(2) > td:nth-child(1) > span:nth-child(2)").text();
                    String isTop = element.select("td:nth-child(2) > table > tbody > tr:nth-child(2) > td:nth-child(3)").text();
                    if(!section.equals("")){
                        journal.setNotes(text.substring(0, 27));
                        journal.setYear(Integer.parseInt(year));
                        journal.setSection("JCR-"+Integer.parseInt(section.substring(0, section.length()-1)));
                        journal.setTop(isTop.equals("是"));
//                        System.out.println("s:" + journal.getYear() + " 分区：" + journal.getSection() + "istop:" + journal.getTop());
                    }
                }
            }
        }
        return journal;
    }

    /**
     * 通过issn获取最新IF
     *
     * @param issn
     * @return
     */
    public float getIF(String issn) {
        String url = "https://www.greensci.net/search?kw=" + issn;
        String IF = "";
        String year = "";
        HttpUtil httpUtil = new HttpUtil();
        String html = httpUtil.getHtml(url);
        if(html == null || html.equals(""))
            return 0;
        Document document = Jsoup.parse(html);// 转换为Dom树
        Element element = document.select("table.result-table-data").first();
        Elements th = element.select("tr>th");
        Elements td = element.select("tr>td");
        int size = td.size();
        int thSize = th.size();
        if (size > 2) {
            year = th.get(thSize - 1).text();
            IF = td.get(thSize - 1).text();
        }else {
            return 0;
        }
        System.out.println("size:" + size + "year:" + year + " IF:" + IF);
        return Float.parseFloat(IF);
    }

    /**
     * 通过刊名/简称/ISSN获取期刊详情
     * @param keyword
     * @return
     */
    public Journal getJournal(String keyword){
        System.out.println("this is journal:"+keyword);
        PeriodicalTable table = getPeriodicals(keyword);
        if(table == null){
            return null;
        }
        Journal journal = new Journal();
        int match = 0;
        for(Periodical periodical: table.getList()){
            if(periodical.isMatch()){
                match++;
                journal = getJournalData(periodical.getUrl());
            }
        }
        if(match == 0) {
            System.out.println("not compare " + keyword);
            return null;
        }
        if(match > 1){
            journal.setNotes("发现多个匹配结果, 建议手动确认, "+journal.getNotes());
        }
        return journal;
    }
}
