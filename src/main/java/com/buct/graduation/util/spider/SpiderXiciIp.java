package com.buct.graduation.util.spider;

import com.buct.graduation.model.spider.IpPort;
import com.buct.graduation.util.ThreadPoolUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class SpiderXiciIp {
    private static String url = "http://www.nimadaili.com/http/";
    private Set<IpPort> ipList = new HashSet<>();
    private volatile int all = 0;
    private volatile int checked = 0;

    public Set<IpPort> findUsefulIp(int s, int end){
        ipList.clear();
        all = 0;
        checked = 0;
        for(int i = s; i <= end; i++){
                    HttpUtil util = new HttpUtil();
                    String html = util.getHtml(url+i);
                    if(html == null || html.equals(""))
                        return null;
                    Document d1 = Jsoup.parse(html);// 转换为Dom树
//                    Element table = d1.getElementById("ip_list");
//      #ip_list > tbody > tr:nth-child(2) > td:nth-child(2)
                    Element table = d1.getElementsByClass("fl-table").first();
                    if(table == null){
                        return null;
                    }
                    Elements tr = table.select("tbody > tr");

                    List<IpPort> list = new ArrayList<>();
                    for(Element e: tr){
//            #ip_list > tbody > tr:nth-child(3) > td:nth-child(2)
                        Elements td = e.select("td");
//            System.out.println("fdgfgh"+td.size());
                        if(td.size() == 0)
                            continue;
                        String type = td.get(1).text();
//                        ip.substring(0, op)+ "-" +ip.substring(op+1)
                        int lo = findPort(td.get(0).text());
                        String IP = td.get(0).text().substring(0, lo);
                        int port = Integer.parseInt(td.get(0).text().substring(lo+1));
//            System.out.println("type"+td.text()+" ip:"+td.get(1).text()+" port:"+td.get(2).text());
                        IpPort ip = new IpPort(IP, port, type);
                        list.add(ip);
                        addAll();
                    }

                    for(IpPort ip: list){
                        ThreadPoolUtil.getThreadPool().submit(new Runnable() {
                            @Override
                            public void run() {
                                if(ip.isWork()){
                                    System.out.println(ip.getIp()+":"+ip.getPort());
                                    ipList.add(ip);
                                }
                                addChecked();
                            }
                        });
                    }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                }


        while (checked < all || all == 0){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("finished good ip has:"+ipList.size());
        return ipList;
    }

    private synchronized void addChecked(){
        checked++;
        System.out.println("all:"+all+" checked:"+checked);
    }

    private synchronized void addAll(){
        all++;
    }

    private int findPort(String ip){
        for(int i = ip.length()-1; i >= 0; i--){
            if(ip.charAt(i) == ':')
                return i;
        }
        return -1;
    }

    public void main(String[] args) {
        String ip = "85.187.245.115:53281";
        int op = findPort(ip);
        System.out.println(ip.substring(0, op)+ "-" +ip.substring(op+1));
    }
}