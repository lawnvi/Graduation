package com.buct.graduation.util.spider;

import com.buct.graduation.model.spider.IpPort;
import com.buct.graduation.service.IpService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.*;
import java.util.Set;

/**
 * ip池
 * 获取可用ip
 */
@Component
public class IpPoolUtil {
    private static volatile int free = 0;//可用ip数
    private static volatile int busy = 0;//可用ip数
    private static volatile int offline = 0;
    private static volatile int all = 0;//可用ip数
    private static volatile String status = "off";
    private Set<IpPort> ipList;
    private int ops = 1;
    private static EatIP IPtimer;
    private static IpPoolUtil ipPoolUtil;

/*
    //创建 IpPoolUtil 的一个对象
    private static IpPoolUtil instance = new IpPoolUtil();

    //让构造函数为 private，这样该类就不会被实例化
    private IpPoolUtil(){}

    //获取唯一可用的对象
    public static IpPoolUtil getInstance(){
        return instance;
    }
*/

    @Autowired
    private IpService ipService;

    @PostConstruct
    public void init(){
        ipPoolUtil = this;
    }

    public static IpPort getIP(){
        IpPort ip = ipPoolUtil.ipService.findFreeIP();
        if(ip == null){
            addIps(0, 16);
            try {
                System.out.println("wait for ip");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getIP();
/*            new Thread(new Runnable() {
                @Override
                public void run() {
                    addIps(16, 100);
                }
            }).start();*/
        }
        return ip;
    }

    public static void releaseIP(IpPort ipPort){
        ipPort.setStatus("free");
        ipPoolUtil.ipService.updateIp(ipPort);
    }

    public static int reviveIps(int t){
        return ipPoolUtil.ipService.revive(t);
    }

    public static synchronized int addIps(int s, int e){
        int op = ipPoolUtil.ipService.addIPs(s, e);
        System.out.println("发现"+op+"个可用ip");
        return op;
    }

    //下面的貌似。。。

    public void initOld(Set<IpPort> list){

        if(!status.equals("block")){
            IPtimer = new EatIP();
            IPtimer.start();
        }
        this.ipList = list;
        this.free = 0;
        this.busy = 0;
        this.offline = 0;

//        for(IpPort ip: ipList){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    if(!IpPoolUtil.checkProxyIp(ip)){
//                        ip.setStatus("offline");
//                        offline++;
//                    }
//                    else {
//                        free++;
//                    }
//                }
//            }).start();
//        }

        this.free=ipList.size();
//        this.ipList = Collections.synchronizedList(this.ipList);
        this.status = "on";
        this.all = this.ipList.size();
        while (free+busy+offline < all){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("free:"+free+" off:"+offline+" all:"+all);
    }

    /**
     * 获取可用ip
     * @return
     */
    public synchronized IpPort getIp(){
        while (status.equals("block")){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(IpPort ip: ipList){
            if(ip.getStatus().equals("free") && ip.times < ops){
                if(!checkProxyIp(ip)) {
                    ip.setStatus("offline");
                    continue;
                }
                ip.setStatus("busy");
                ip.times++;
                free--;
                busy++;
                return ip;
            }
        }
        ops++;
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(IpPort ip: ipList){
            if(ip.getStatus().equals("free") && ip.times < ops){
                if(!checkProxyIp(ip)) {
                    ip.setStatus("offline");
                    continue;
                }
                ip.setStatus("busy");
                ip.times++;
                free--;
                busy++;
                return ip;
            }
        }
        return null;
    }

    /**
     * 释放ip
     * @param ip
     */
    public synchronized void freeIp(IpPort ip){
        ip.setStatus("free");
        busy--;
        free++;
    }

    /**
     * 关闭ip池
     */
    public Set<IpPort> closeIpPool(){
        if(free+busy+offline != all){
//            return false;
        }
        this.status = "off";

        all = 0;
        free = 0;
        offline = 0;
        busy = 0;
//        ipList.clear();
        System.out.println("ip pool close");
        return ipList;
    }

    /**
     * 检测代理ip是否有效
     *
     * @param ip
     * @return
     */
    public static boolean testIp(IpPort ip) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip.getIp(), ip.getPort()));
//        System.out.println("fgghfggjkdfhg "+ip.getType()+" "+ip.getIp()+":"+ip.getPort());
        try {
            URLConnection httpCon = new URL("http://undera.cn/").openConnection(proxy);
            httpCon.setConnectTimeout(5000);
            httpCon.setReadTimeout(5000);
            int code = ((HttpURLConnection) httpCon).getResponseCode();
            System.out.println("code"+code+" "+ip.getIp()+":"+ip.getPort());
            return code == 200;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkProxyIp(IpPort ip) {
        String reqUrl = "http://undera.cn/";
            int statusCode = 0;
            try {
                HttpClient httpClient = new HttpClient();
                httpClient.getHostConfiguration().setProxy(ip.getIp(), ip.getPort());

                // 连接超时时间（默认10秒 10000ms） 单位毫秒（ms）
                int connectionTimeout = 5000;
                // 读取数据超时时间（默认30秒 30000ms） 单位毫秒（ms）
                int soTimeout = 5000;
                httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
                httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

                HttpMethod method = new GetMethod(reqUrl);

                statusCode = httpClient.executeMethod(method);
            } catch (Exception e) {
                System.out.println("ip " + ip.getIp() + " is not aviable");
                return false;
            }
            if(statusCode>0){
                System.out.format("got good ip %s  %s:%s\n", statusCode, ip.getIp(), ip.getPort());
                return statusCode == 200;
            }
            return false;
    }

    public Set<IpPort> refreshPool(int s, int e){
        SpiderXiciIp xiciIp = new SpiderXiciIp();
        return xiciIp.findUsefulIp(s, e);
    }

    public static void main(String[] args){
        IpPort ip = new IpPort();
        ip.setIp("89.232.202.108");
        ip.setPort(3128);
        checkProxyIp(ip);
//        testIp(ip);
//        HttpUtil util = new HttpUtil();
//        util.getHtmlWithIp("https://www.linqiuqiu.cn/");
//        System.out.println(util.getHtmlWithIp("http://www.letpub.com.cn/index.php?page=journalapp&view=search&searchissn=&searchfield=&searchimpactlow=&searchimpacthigh=&searchscitype=&view=search&searchcategory1=&searchcategory2=&searchjcrkind=&searchopenaccess=&searchsort=relevance&searchname=SYSTEMS+%26+CONTROL+LETTERS"));
    }

    private class EatIP extends Thread{

        @Override
        public void run(){
            while (status.equals("on")){
                if(free + busy < 64){
                    status = "block";
                    System.out.println("ip不够用了");
                    ipList.addAll(refreshPool(1, 64));
                    status = "on";
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
