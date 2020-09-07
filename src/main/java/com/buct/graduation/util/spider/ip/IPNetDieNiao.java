package com.buct.graduation.util.spider.ip;

import com.buct.graduation.model.spider.IpPort;
import com.buct.graduation.util.ThreadPoolUtil;
import com.buct.graduation.util.spider.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.nio.ch.ThreadPool;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.buct.graduation.util.spider.IpPoolUtil.checkIPs;
import static com.buct.graduation.util.spider.IpPoolUtil.decodePort;

public class IPNetDieNiao implements IPNet{
    private String uri = "https://www.dieniao.com/FreeProxy/";
    private HttpUtil httpUtil;

    public IPNetDieNiao(){
        httpUtil = new HttpUtil();
    }

    @Override
    public HashSet<IpPort> findIPs(String url) {
        String html = httpUtil.getHtml(url);
        if("".equals(html)){
            return new HashSet<>();
        }
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByClass("f-list col-lg-12 col-md-12 col-sm-12 col-xs-12");
        HashSet<IpPort> set = new HashSet<>();
        for(Element e: elements){
            String ip = e.getElementsByClass("f-address").text();
            String port = e.getElementsByClass("f-port").text();
            int p = decodePort(port);
            if(p == -1){
                System.out.println("decode port error: "+port);
                continue;
            }
            IpPort ipPort = new IpPort(ip, p, "http");
            set.add(ipPort);
        }
        return set;
    }

    @Override
    public HashSet<IpPort> MopUp() {
        String u = "https://www.dieniao.com/FreeProxy.html";
        String html = httpUtil.getHtml(u);
        Document document = Jsoup.parse(html);
        int page = document.getElementsByClass("a-page page-active").size()/2 + 1;
        System.out.println("all pages:"+ page);
        HashSet<IpPort> sets = new HashSet<>();
        for(int i = 1; i <= page; i++){
            String url = uri+ i +".html";
            sets.addAll(findIPs(url));
        }
        System.out.println("going to check "+sets.size());
        return sets;
//        return checkIPs(sets);
    }
}
