package com.buct.graduation.util.spider.ip;

import com.buct.graduation.model.spider.IpPort;
import com.buct.graduation.util.ThreadPoolUtil;
import com.buct.graduation.util.spider.HttpUtil;
import com.buct.graduation.util.spider.IpPoolUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.concurrent.*;

public class IPNetNima implements IPNet {
    private String uri = "http://www.nimadaili.com/";
    private String type;//http https gaoni
    private HttpUtil httpUtil;
    private int maxPage = 925;

    public IPNetNima(String type) {
        httpUtil = new HttpUtil();
        this.type = type;
    }

    @Override
    public HashSet<IpPort> findIPs(String url) {
        HashSet<IpPort> list = new HashSet<>();
        String html = httpUtil.getHtml(url);
        if (html == null || html.equals(""))
            return list;
        Document d1 = Jsoup.parse(html);// 转换为Dom树
        Element table = d1.getElementsByClass("fl-table").first();
        if (table == null) {
            return list;
        }
        Elements tr = table.select("tbody > tr");
        for (Element e : tr) {
            Elements td = e.select("td");
            if (td.size() == 0)
                continue;
            String type = td.get(1).text();
            int lo = (td.get(0).text()).lastIndexOf(":");
            String IP = td.get(0).text().substring(0, lo);
            int port = Integer.parseInt(td.get(0).text().substring(lo + 1));
            int score = Integer.parseInt(td.get(6).text());
            System.out.println("score:"+score);
            //todo biubiubiu check ip by score first
            IpPort ip = new IpPort(IP, port, type);
            list.add(ip);
        }
        return list;
    }

    @Override
    public HashSet<IpPort> MopUp() {
        ExecutorService cachedThreadPool = new ThreadPoolExecutor(0, 32,    //核心大小=0，最大数量不限，存活时间为60s（若长时间没有任务则该线程池为空）,使用SynchronousQueue作为workeQueue
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        Set<IpPort> ips = Collections.synchronizedSet(new HashSet<>());
        for (int i = 1; i <= maxPage; i++) {
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

        return (HashSet<IpPort>) ips;
//        return IpPoolUtil.checkIPs(ips);
    }

    public static void main(String[] args){
        new IPNetNima("http").findIPs("http://www.nimadaili.com/http/");
    }
}
