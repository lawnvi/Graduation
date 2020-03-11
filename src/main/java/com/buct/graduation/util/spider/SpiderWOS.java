package com.buct.graduation.util.spider;

import com.buct.graduation.model.pojo.Article;
import com.buct.graduation.util.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpiderWOS {
    public static void main(String[] args) {
        long tempTime = System.currentTimeMillis();
        String sid = SpiderConfig.getSID();
        List<String> keyword = new ArrayList<>();
        //keyword.add("Controlling Complex Networks: How Much Energy Is Needed");
        keyword.add("SIHR rumor spreading model in social networks");
        //keyword.add("Phenyl-gamma-valerolactones and phenylvaleric acids, the main colonic metabolites of flavan-3-ols: synthesis, analysis, bioavailability, and bioactivity Electronic supplementary information (ESI) available. See DOI: 10.1039/c8np00062j");
        //keyword.add("A sequence of a plinian eruption preceded by dome destruction at Kelud volcano, Indonesia, on February 13, 2014, revealed from tephra fallout and pyroclastic density current deposits");
        SpiderWOS wos = new SpiderWOS();
        for(String s : keyword) {
            wos.getESIandtimes(sid, s);
            System.out.println("耗时："+(System.currentTimeMillis()-tempTime)/1000+"s");
            tempTime = System.currentTimeMillis();
        }
    }

    /**
     * 校园网下获取SID
     * @return
     */
    public static String getSID(){
        String SID = "";
        String url0 = "http://www.webofknowledge.com/";//校园网下
        HttpUtil util = new HttpUtil();
        String html0 = util.getHtml(url0);
        HttpUtil.writeFile(html0, HttpUtil.tempPath+"/"+System.currentTimeMillis()+(new Random().nextInt(1000))+".html");
        Document d0 = Jsoup.parse(html0);// 转换为Dom树
        SID = d0.select("#SID").attr("value");
        System.out.println("SID:"+SID);
        if(SID.equals(""))
            System.out.println("getSidFailed, please checkout your network");
        return SID;
    }

    /**
     * 获取论文被引次数、isESI、期刊名、发表年份
     * @param SID
     * @param kw2
     * @return
     */
    public Article getESIandtimes(String SID, String kw2){
        HttpUtil util = new HttpUtil();
        String kw = kw2.replace(" ", "+");
        String url = "http://apps.webofknowledge.com/UA_GeneralSearch.do?fieldCount=1&action=search&product=UA&search_mode=GeneralSearch&SID="+SID+"&max_field_count=25&max_field_notice=%E6%B3%A8%E6%84%8F%3A+%E6%97%A0%E6%B3%95%E6%B7%BB%E5%8A%A0%E5%8F%A6%E4%B8%80%E5%AD%97%E6%AE%B5%E3%80%82&input_invalid_notice=%E6%A3%80%E7%B4%A2%E9%94%99%E8%AF%AF%3A+%E8%AF%B7%E8%BE%93%E5%85%A5%E6%A3%80%E7%B4%A2%E8%AF%8D%E3%80%82&exp_notice=%E6%A3%80%E7%B4%A2%E9%94%99%E8%AF%AF%3A+%E4%B8%93%E5%88%A9%E6%A3%80%E7%B4%A2%E8%AF%8D%E5%8F%AF%E4%BB%A5%E5%9C%A8%E5%A4%9A%E4%B8%AA%E5%AE%B6%E6%97%8F%E4%B8%AD%E6%89%BE%E5%88%B0+%28&input_invalid_notice_limits=+%3Cbr%2F%3E%E6%B3%A8%E6%84%8F%3A+%E6%BB%9A%E5%8A%A8%E6%A1%86%E4%B8%AD%E6%98%BE%E7%A4%BA%E7%9A%84%E5%AD%97%E6%AE%B5%E5%BF%85%E9%A1%BB%E8%87%B3%E5%B0%91%E4%B8%8E%E4%B8%80%E4%B8%AA%E5%85%B6%E4%BB%96%E6%A3%80%E7%B4%A2%E5%AD%97%E6%AE%B5%E7%9B%B8%E7%BB%84%E9%85%8D%E3%80%82&sa_params=UA%7C%7C6FZIMo2VMG1B4cQjRj6%7Chttp%3A%2F%2Fapps.webofknowledge.com%7C%27&formUpdated=true&value%28select1%29=TS&value%28hidInput1%29=&limitStatus=expanded&ss_lemmatization=On&ss_spellchecking=Suggest&SinceLastVisit_UTC=&SinceLastVisit_DATE=&period=Range+Selection&range=ALL&startYear=1950&endYear=2020&editions=WOS.SCI&editions=WOS.IC&editions=WOS.ISTP&editions=WOS.CCR&collections=WOS&editions=DIIDW.EDerwent&editions=DIIDW.CDerwent&editions=DIIDW.MDerwent&collections=DIIDW&editions=KJD.KJD&collections=KJD&editions=MEDLINE.MEDLINE&collections=MEDLINE&editions=RSCI.RSCI&collections=RSCI&editions=SCIELO.SCIELO&collections=SCIELO&update_back2search_link_param=yes&ssStatus=display%3Anone&ss_showsuggestions=ON&ss_query_language=auto&ss_numDefaultGeneralSearchFields=1&rs_sort_by=PY.D%3BLD.D%3BSO.A%3BVL.D%3BPG.A%3BAU.A&value%28input1%29=";
        System.out.println(kw+"\n"+kw2);
        String html = util.getHtml(url+kw);
        int number = 0;
        boolean isESI = false;
        String year = "";
        List<String> authors = new ArrayList<>();
        String author = "";
        String Journal = "";
        String volume = "";
        String stage = "";
        String page = "";
        String name = kw2;
        System.out.println(kw+"搜索完成1");
        HttpUtil.writeFile(html, HttpUtil.tempPath+"/"+System.currentTimeMillis()+(new Random().nextInt(1000))+".html");
        String qid = "";
        Document d1 = Jsoup.parse(html);// 转换为Dom树
        for(Element e : d1.getAllElements()){
            if(e.attr("name").equals("summary_navigation.sort")){
                for(Element element : e.getAllElements()) {
                    if(element.attr("name").equals("qid")) {
                        qid = element.attr("value");
                        SpiderConfig.visitSIDTimes(Integer.parseInt(qid));
                        System.out.println("qid:" + qid);
                        break;
                    }
                }
                break;
            }
        }
        String url2 = "http://apps.webofknowledge.com/summary.do?product=UA&parentProduct=UA&search_mode=GeneralSearch&qid="+qid+"&SID="+SID+"&&page=1&action=sort&sortBy=RS.D;PY.D;AU.A;SO.A;VL.D;PG.A&showFirstPage=1&isCRHidden=false";
        String html2 = util.getHtml(url2);
        System.out.println("搜索完成2");
        HttpUtil.writeFile(html2, HttpUtil.tempPath+"/"+System.currentTimeMillis()+(new Random().nextInt(1000))+".html");
        Document d2 = Jsoup.parse(html2);// 转换为Dom树
        List<Element> et = d2.select("#summaryRecordsTable");
        for (Element e : d2.getAllElements()) {
//            System.out.println(e.attr("class"));
            if (e.attr("class").equals("smallV110 snowplow-full-record")) {
                System.out.println(e.text() + "\n"+kw2+"\nherf:"+ e.attr("href"));
                if(e.text().toLowerCase().contains(kw2.toLowerCase())){
                    System.out.println("find detail href");
                    name = e.text();
                    System.out.println("开始搜索完成3");
                    //String html3 = util.getHtml("http://apps.webofknowledge.com"+e.attr("href"));
                    String url3 = "";
                    if(e.attr("href").startsWith("http:"))
                        url3 = e.attr("href");
                    else
                        url3 ="http://apps.webofknowledge.com"+e.attr("href");
                    String html3 = util.getHtml(url3);
                    System.out.println("搜索完成3");
                    HttpUtil.writeFile(html3, HttpUtil.tempPath+"/"+System.currentTimeMillis()+(new Random().nextInt(1000))+".html");
                    Document d3 = Jsoup.parse(html3);// 转换为Dom树
                    //flex-row flex-justify-start flex-align-start box-div
                    List<Element> elements = d3.getElementsByClass("FR_field");
                    for(Element element : elements){
//                        System.out.println(element.attr("value")+" text:"+element.text());
                        if(element.child(0).text().equals("出版年:") || element.child(0).text().equals("Published:")){
                            year = element.child(1).text();
                            System.out.println("find year"+year);
                            break;
                        }
                    }
                    List<Element> authorsE = d3.getElementsByClass("FR_field");
                    author = authorsE.get(0).text();

                    Element information = d3.getElementsByClass("block-record-info block-record-info-source").first();
                    for(Element element: information.getAllElements()){
                        if(element.attr("class").equals("sourceTitle")){
                            Journal = element.text();
                            System.out.println("find j:"+Journal);
                        }else if(element.attr("class").equals("block-record-info-source-values")){
                            Elements el = element.select("p");
                            System.out.println("sdjf"+element.text());
                            for(Element ele: el){
                                switch (ele.select("span").text()){
                                    case "卷:":
                                    case "Volume:":
                                        volume = ele.select("value").text(); break;
                                    case "期:":
                                    case "Issue:":
                                        stage = ele.select("value").text();break;
                                    case "页:":
                                    case "Pages:":
                                        page = ele.select("value").text(); break;
                                    default:break;
                                }
                            }
//                            volume = element.text();
                            System.out.println("find 卷页:"+volume+" "+stage+"=="+page);
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
                    //卷号volume 页码page
//#records_form > div > div > div > div.l-column-content > div > div.block-record-info.block-record-info-source > div > p:nth-child(1) > value
//#records_form > div > div > div > div.l-column-content > div > div.block-record-info.block-record-info-source > div > p:nth-child(2) > value


                    System.out.println("J:"+Journal);
                    System.out.println("author:"+author);
                    System.out.println("被引次数:"+number);
                    System.out.println("isESI:"+isESI);
//                    year = year.substring(year.length()-4);
////                    Integer.valueOf(year);
                    System.out.println("year:"+year);
                    year = Utils.formatTime(year);

                    Article articles = new Article();
                    articles.setName(name);
                    articles.setCitation(number);
                    articles.setESI(isESI);
                    articles.setYear(year);
                    articles.setJournalName(Journal);
                    articles.setUrl(url3);
                    articles.setPage(page);
                    articles.setIssue(stage);
                    articles.setVolume(volume);
                    return articles;
                }
                else {
                    System.out.println("不匹配");
                }
            }
        }
        return null;
    }
}