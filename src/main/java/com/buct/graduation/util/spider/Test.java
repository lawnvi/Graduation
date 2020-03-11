package com.buct.graduation.util.spider;

import com.buct.graduation.model.pojo.Article;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception{
//        Test.getHtml("https://webapi.fenqubiao.com/api/user/search?year=2015&keyword=IEEE&user=BUCT_admin&password=1204705");
//        while (true){
//            Thread.sleep(3000);
//            int count = Test.pool.getActiveCount();
//            if(count == 0){
//                System.out.println("there is no tasks");
//                break;
//            }
//        }
        List<String> title = new ArrayList<>();
        title.add("论文");
        title.add("期刊");
        title.add("isTop");
        title.add("影响因子");
        title.add("他引次数");
        title.add("通讯作者");
        title.add("作者");
        List<Article> articles = new ArrayList<>();
        Article article = new Article();
        article.setName("testName");
        article.setJournalName("j");
        article.setAuthor("author");
        article.setCitation(12);
        article.setCAuthor("cauthor");
        Article article2 = new Article();
        article2.setName("testName");
        article2.setJournalName("j");
        article2.setAuthor("author");
        article2.setCitation(12);
        article2.setCAuthor("cauthor");
        articles.add(article2);
        GetArticlesByAddress.writeExcel("D:\\temp\\test\\test.xls", "test", ".xls", title, articles);
    }
}
//product=UA&parentProduct=UA&search_mode=GeneralSearch&qid=43&SID=6FZIMo2VMG1B4cQjRj6&&page=1&action=sort&sortBy=RS.D;PY.D;AU.A;SO.A;VL.D;PG.A&showFirstPage=1&isCRHidden=false
//product=UA&parentProduct=UA&search_mode=GeneralSearch&qid=43&SID=6FZIMo2VMG1B4cQjRj6&&page=1&action=sort&sortBy=RS.D;PY.D;AU.A;SO.A;VL.D;PG.A&showFirstPage=1&isCRHidden=false

//sid qid

//http://www.webofknowledge.com/
//value%28input1%29= SID
//fieldCount=1&action=search&product=UA&search_mode=GeneralSearch&SID=6FZIMo2VMG1B4cQjRj6&max_field_count=25&max_field_notice=Notice%3A+You+cannot+add+another+field.&input_invalid_notice=Search+Error%3A+Please+enter+a+search+term.&exp_notice=Search+Error%3A+Patent+search+term+could+be+found+in+more+than+one+family+%28unique+patent+number+required+for+Expand+option%29+&input_invalid_notice_limits=+%3Cbr%2F%3ENote%3A+Fields+displayed+in+scrolling+boxes+must+be+combined+with+at+least+one+other+search+field.&sa_params=UA%7C%7C6FZIMo2VMG1B4cQjRj6%7Chttp%3A%2F%2Fapps.webofknowledge.com%7C%27&formUpdated=true&value%28select1%29=TS&value%28hidInput1%29=&limitStatus=expanded&ss_lemmatization=On&ss_spellchecking=Suggest&SinceLastVisit_UTC=&SinceLastVisit_DATE=&period=Range+Selection&range=ALL&startYear=1950&endYear=2020&editions=WOS.SCI&editions=WOS.IC&editions=WOS.ISTP&editions=WOS.CCR&collections=WOS&editions=DIIDW.EDerwent&editions=DIIDW.CDerwent&editions=DIIDW.MDerwent&collections=DIIDW&editions=KJD.KJD&collections=KJD&editions=MEDLINE.MEDLINE&collections=MEDLINE&editions=RSCI.RSCI&collections=RSCI&editions=SCIELO.SCIELO&collections=SCIELO&update_back2search_link_param=yes&ssStatus=display%3Anone&ss_showsuggestions=ON&ss_query_language=auto&ss_numDefaultGeneralSearchFields=1&rs_sort_by=RS.D%3BPY.D%3BAU.A%3BSO.A%3BVL.D%3BPG.A
//fieldCount=1&action=search&product=UA&search_mode=GeneralSearch&SID=6FZIMo2VMG1B4cQjRj6&max_field_count=25&max_field_notice=&input_invalid_notice=input_invalid_notice_limits=&sa_params=UA%7C%7C6FZIMo2VMG1B4cQjRj6%7Chttp%3A%2F%2Fapps.webofknowledge.com%7C%27&formUpdated=true&value%28select1%29=TS&value%28hidInput1%29=&limitStatus=expanded&ss_lemmatization=On&ss_spellchecking=Suggest&SinceLastVisit_UTC=&SinceLastVisit_DATE=&period=Range+Selection&range=ALL&startYear=1950&endYear=2020&editions=WOS.SCI&editions=WOS.IC&editions=WOS.ISTP&editions=WOS.CCR&collections=WOS&editions=DIIDW.EDerwent&editions=DIIDW.CDerwent&editions=DIIDW.MDerwent&collections=DIIDW&editions=KJD.KJD&collections=KJD&editions=MEDLINE.MEDLINE&collections=MEDLINE&editions=RSCI.RSCI&collections=RSCI&editions=SCIELO.SCIELO&collections=SCIELO&update_back2search_link_param=yes&ssStatus=display%3Anone&ss_showsuggestions=ON&ss_query_language=auto&ss_numDefaultGeneralSearchFields=1&rs_sort_by=RS.D%3BPY.D%3BAU.A%3BSO.A%3BVL.D%3BPG.A

//list.add(new BasicNameValuePair("instanceId", "1b0acfbb-d7f2-4f0d-a026-734c686ee4ba"));
//list.add(new BasicNameValuePair("fieldCount", "1"));
//list.add(new BasicNameValuePair("action", "search"));
//list.add(new BasicNameValuePair("product", "UA"));
//list.add(new BasicNameValuePair("search_mode", "GeneralSearch"));
//list.add(new BasicNameValuePair("SID", "7ChckPdiyItvEnCHXcV"));
//list.add(new BasicNameValuePair("max_field_count", "25"));
//list.add(new BasicNameValuePair("max_field_notice", "注意", "无法添加另一字段。"));
//list.add(new BasicNameValuePair("input_invalid_notice", "检索错误", "请输入检索词。"));
//list.add(new BasicNameValuePair("exp_notice", "检索错误", "专利检索词可以在多个家族中找到 ("));
//list.add(new BasicNameValuePair("input_invalid_notice_limits", " <br/>注意", "滚动框中显示的字段必须至少与一个其他检索字段相组配。"));
//list.add(new BasicNameValuePair("sa_params", "UA||7ChckPdiyItvEnCHXcV|http://apps.webofknowledge.com|'"));
//list.add(new BasicNameValuePair("formUpdated", "true"));
//list.add(new BasicNameValuePair("value(input1)", "Controlling Complex Networks", "How Much Energy Is Needed"));
//list.add(new BasicNameValuePair("value(select1)", "TS"));
//list.add(new BasicNameValuePair("value(hidInput1)", ""));
//list.add(new BasicNameValuePair("limitStatus", "expanded"));
//list.add(new BasicNameValuePair("ss_lemmatization", "On"));
//list.add(new BasicNameValuePair("ss_spellchecking", "Suggest"));
//list.add(new BasicNameValuePair("SinceLastVisit_UTC", ""));
//list.add(new BasicNameValuePair("SinceLastVisit_DATE", ""));
//list.add(new BasicNameValuePair("period", "Range Selection"));
//list.add(new BasicNameValuePair("range", "ALL"));
//list.add(new BasicNameValuePair("startYear", "1950"));
//list.add(new BasicNameValuePair("endYear", "2020"));
//list.add(new BasicNameValuePair("editions", "WOS.SCI"));
//list.add(new BasicNameValuePair("editions", "WOS.IC"));
//list.add(new BasicNameValuePair("editions", "WOS.ISTP"));
//list.add(new BasicNameValuePair("editions", "WOS.CCR"));
//list.add(new BasicNameValuePair("collections", "WOS"));
//list.add(new BasicNameValuePair("editions", "DIIDW.EDerwent"));
//list.add(new BasicNameValuePair("editions", "DIIDW.CDerwent"));
//list.add(new BasicNameValuePair("editions", "DIIDW.MDerwent"));
//list.add(new BasicNameValuePair("collections", "DIIDW"));
//list.add(new BasicNameValuePair("editions", "KJD.KJD"));
//list.add(new BasicNameValuePair("collections", "KJD"));
//list.add(new BasicNameValuePair("editions", "MEDLINE.MEDLINE"));
//list.add(new BasicNameValuePair("collections", "MEDLINE"));
//list.add(new BasicNameValuePair("editions", "RSCI.RSCI"));
//list.add(new BasicNameValuePair("collections", "RSCI"));
//list.add(new BasicNameValuePair("editions", "SCIELO.SCIELO"));
//list.add(new BasicNameValuePair("collections", "SCIELO"));
//list.add(new BasicNameValuePair("update_back2search_link_param", "yes"));
//list.add(new BasicNameValuePair("ssStatus", "display:none"));
//list.add(new BasicNameValuePair("ss_showsuggestions", "ON"));
//list.add(new BasicNameValuePair("ss_query_language", "auto"));
//list.add(new BasicNameValuePair("ss_numDefaultGeneralSearchFields", "1"));
//list.add(new BasicNameValuePair("rs_sort_by", "RS.D;PY.D;AU.A;SO.A;VL.D;PG.A"));