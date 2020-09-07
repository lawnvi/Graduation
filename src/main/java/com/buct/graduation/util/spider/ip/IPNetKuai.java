package com.buct.graduation.util.spider.ip;

import com.buct.graduation.model.spider.IpPort;
import com.buct.graduation.util.Utils;
import com.buct.graduation.util.spider.HttpUtil;
import com.buct.graduation.util.spider.IpPoolUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 反爬虫有点难受
 * 频率
 * 质量貌似也不好
 */
public class IPNetKuai implements IPNet{
    private String uri = "https://www.kuaidaili.com/free/";
    private HttpUtil httpUtil;
    private String type;//inha intr
    private int maxPage = 3620;

    public IPNetKuai(String type){
        httpUtil = new HttpUtil();
        this.type = type;
    }

    @Override
    public HashSet<IpPort> findIPs(String url) {
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<IpPort> set = new HashSet<>();
        String html = httpUtil.getHtml(url);
        if ("".equals(html)) {
            return set;
        }
        Document document = Jsoup.parse(html);
        Elements elements = document.select("#list > table > tbody > tr");
        for(Element e: elements){
            String ip = e.select("#td").get(0).text();
            String port = e.select("#td").get(1).text();
            String t = e.select("#td").get(3).text();
            IpPort ipPort = new IpPort(ip, Integer.parseInt(port), t);
            set.add(ipPort);
        }
        return set;
    }

    @Override
    public HashSet<IpPort> MopUp() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        Set<IpPort> ips = Collections.synchronizedSet(new HashSet<>());
        for (int i = 1; i <= 2; i++) {
            int finalI = i;
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    ips.addAll(findIPs(uri + type +"/"+ finalI));
                }
            });
        }
        cachedThreadPool.shutdown();
        try {
            while (!cachedThreadPool.awaitTermination(2, TimeUnit.SECONDS)) {
                System.out.println("线程池没有关闭" + ips.size());
                System.out.println("isTerminated:" + cachedThreadPool.isTerminated());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("going to check "+ips.size());

        return IpPoolUtil.checkIPs(ips);
    }
    public static void main(String[] args){
        IPNet ip = new IPNetKuai("inha");
        IpPort ipPort = new IpPort("113.252.222.73", 80, "http");
        ipPort.isWork();
    }
}
