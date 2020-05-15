package com.buct.graduation.util.spider;

import com.buct.graduation.model.spider.ProjectData;
import com.buct.graduation.util.ThreadPoolUtil;
import com.buct.graduation.util.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 抓取项目数据
 */
public class SpiderLetpubProject {
    private int startYear = 1997;
    private int endYear = Utils.getDate().getYear()-1;//去年
    private String person;//负责人
    private String projectName;//项目
    private String company;//单位
    private String code;
    private int currentPage = 1;
    String url = "http://www.letpub.com.cn/nsfcfund_search2020.php?mode=advanced&datakind=list&page=&addcomment_s1=&addcomment_s2=&addcomment_s3=&addcomment_s4=&money1=&money2=&subcategory=&searchsubmit=true";
//&name=&no=&person=&company=&currentpage=1
//    &startTime="+startYear+"&endTime="+endYear+"

    public int resultNumber = -1;
    List<ProjectData> buffList = Collections.synchronizedList(new ArrayList<>());
    volatile int finished = 0;
    public SpiderLetpubProject(){

    }

    public SpiderLetpubProject(String person, String projectName, String company, String code) {
        try {
            this.person = URLEncoder.encode(person, "UTF-8");
            this.projectName = URLEncoder.encode(projectName, "UTF-8");
            this.company = URLEncoder.encode(company, "UTF-8");
            this.code = URLEncoder.encode(code, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String toUTF8Code(String s){
        String utf_8 = s;
        try {
            utf_8 = URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return utf_8;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = toUTF8Code(person);
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = toUTF8Code(projectName);
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = toUTF8Code(company);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = toUTF8Code(code);
    }

    public ProjectData searchByCode(){
        if(code == null || "".equals(code)){
            System.out.println("empty project's parameters");
            return null;
        }
        String number = this.code;
        String url = this.url+"&currentpage=1&name=&person=&company=&no="+code;
        List<ProjectData> list = searchProjects(url);
        if(list == null || list.size() == 0){
            return null;
        }
        return list.get(0);
    }

    public ProjectData searchByName(){
        if(projectName == null || "".equals(projectName)){
            System.out.println("empty project's parameters");
            return null;
        }
        String url = this.url+"&currentpage=1&name="+projectName+"&person=&company=&no=";
        List<ProjectData> list = searchProjects(url);
        if(list == null || list.size() == 0){
            return null;
        }
        for(ProjectData project: list){
            if(toUTF8Code(project.getName()).equals(this.projectName)){
                System.out.println("find compare");
                return project;
            }
        }
        System.out.println("no compare");
        return null;
    }

    public List<ProjectData> searchByPerson(){
        if(person == null || "".equals(person)){
            System.out.println("empty project's parameters");
            return null;
        }
        String url = this.url+"&currentpage=1&name=&person="+this.person+"&company=&no=";
        return searchProjects(url);
    }

    public List<ProjectData> searchByCompany(){
        buffList.clear();
        finished = 0;
        if(company == null || "".equals(company)){
            System.out.println("empty project's parameters");
            return null;
        }
        int page = 1;
        String url = this.url+"&name=&person=&company="+company+"&no=&currentpage=";
        String url0 = url+page;
        List<ProjectData> list0 = searchProjects(url0);
        buffList.addAll(list0);
        countFinished();
        int allPage = resultNumber/10;
        allPage = resultNumber%10 > 0 ? allPage+1 : allPage;
        for(page = 2; page <= allPage; page++){
            String urlx = url+page;
            ThreadPoolUtil.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    List<ProjectData> list = searchProjects(urlx);
                    if(list != null && list.size() > 0){
                        buffList.addAll(list);
                    }else {
                        System.out.println("got error");
                    }
                    countFinished();
                }
            });
        }
        while (finished < allPage){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("already finished "+buffList.size());
        }
        System.out.println("already finished "+buffList.size());
        return buffList;
    }

    private synchronized void countFinished(){
        finished++;
    }

    public List<ProjectData> searchProjects(String url){
        url = url + "&startTime="+startYear+"&endTime="+endYear;
        HttpUtil util = new HttpUtil();
        String html_list_projects =  util.getHtmlWithIp(url);
//        String html_list_projects =  util.getHtml(url);
        if(html_list_projects == null || "".equals(html_list_projects)) {
            System.out.println("get html failed");
            return null;
        }
        Document d1 = Jsoup.parse(html_list_projects);// 转换为Dom树
//#dict > center > div > b

//        body > center > div > i
//        body > center > div
        Element errorE = d1.select("body > center > div > i").first();
        if(errorE != null){
            String errorMsg = d1.select("body > center > div").text();
            System.out.println("spider error:"+errorMsg);
            return null;
        }
        String resultTemp = d1.select("#dict > center > div > b").text();
        System.out.println("number:"+resultTemp);
        if(resultTemp == null || "".equals(resultTemp)){
            System.out.println("unknown error");
            return null;
        }
        if("0".equals(resultTemp)){
            System.out.println("result number 0");
            resultNumber = 0;
            return null;
        }
        resultNumber = Integer.parseInt(resultTemp);
//        #dict > table > tbody > tr:nth-child(3)
//        #dict > table > tbody > tr:nth-child(3) > td:nth-child(4)
//        #dict > table > tbody > tr:nth-child(3) > td:nth-child(1)
//        #dict > table > tbody > tr:nth-child(8) > td:nth-child(1)
//        #dict > table > tbody > tr:nth-child(4) > td:nth-child(2)
//        #dict > table > tbody > tr:nth-child(9) > td:nth-child(2)\
        Elements e1 = d1.getElementsByAttribute("s");
        System.out.println("test:"+e1.size());
        List<ProjectData> projects = new ArrayList<>();
        int op = 0;
        for(int i = 0; i < 10 && i < resultNumber; i++){
            ProjectData project = new ProjectData();
            int temp = i*5 + 3 + op;
            String person = d1.select("#dict > table > tbody > tr:nth-child("+temp+") > td:nth-child(1)").text();
            String address = d1.select("#dict > table > tbody > tr:nth-child("+temp+") > td:nth-child(2)").text();
            String money = d1.select("#dict > table > tbody > tr:nth-child("+temp+") > td:nth-child(3)").text();
            String number = d1.select("#dict > table > tbody > tr:nth-child("+temp+") > td:nth-child(4)").text();
            String fund = d1.select("#dict > table > tbody > tr:nth-child("+temp+") > td:nth-child(5)").text();
            String belong = d1.select("#dict > table > tbody > tr:nth-child("+temp+") > td:nth-child(6)").text();
            String allowTime = d1.select("#dict > table > tbody > tr:nth-child("+temp+") > td:nth-child(7)").text();

            String name = d1.select("#dict > table > tbody > tr:nth-child("+(temp+1)+") > td:nth-child(2)").text();
            String subclass = d1.select("#dict > table > tbody > tr:nth-child("+(temp+2)+") > td:nth-child(2)").text();
            String subCode = d1.select("#dict > table > tbody > tr:nth-child("+(temp+3)+") > td:nth-child(2)").text();
            String runtime = d1.select("#dict > table > tbody > tr:nth-child("+(temp+4)+") > td:nth-child(2)").text();

            int nextName = (i+1)*5 + 4;
            while (i < 9 && i < resultNumber-1){
                String nameTag = d1.select("#dict > table > tbody > tr:nth-child("+(nextName+op)+") > td:nth-child(1)").text();
                if(nameTag == null || "".equals(nameTag)){
                    break;
                }

//                System.out.println("while:"+i+" "+nameTag);
                if("题目".equals(nameTag)){
                    break;
                }
                op++;
            }


            project.setCharge(person);
            project.setCompany(address);
            project.setFunds(Double.parseDouble(money));
            project.setNumber(number);
            project.setFund(fund);
            project.setSubjectClass(subclass);
            project.setBelongSubject(belong);
            project.setAuthorizeYear(Integer.parseInt(allowTime));
            project.setSubjectCode(subCode);
            project.setRuntime(runtime);
            project.setName(name);

            String u = this.url+"&name=&person=&company=&no="+number;
            project.setUrl(u);
            projects.add(project);

//            System.out.println("name:"+project.getCharge()+"name:"+project.getName()+" number:"+ project.getNumber()+"subcode:"+subCode);
        }
        return projects;
    }

    public static void main(String[] args){
        SpiderLetpubProject spider = new SpiderLetpubProject();
        spider.setCompany("北京化工大学");
        spider.setStartYear(2015);
        spider.setEndYear(2015);
        spider.searchByCompany();
    }
}
