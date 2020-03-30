package com.buct.graduation.model.spider;

import com.buct.graduation.util.GlobalName;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class IpPort {
    private Integer id;
    private String ip = "";
    private Integer port;
    private String type = "http";
    private String status = "free";//free/busy/offline
    public volatile int times = 0;
    private int badTimes = 0;
    private int useTimes = 0;


    public IpPort(){

    }

    public IpPort(String ip, int port, String type){
        this.ip = ip;
        this.port = port;
        this.type = type;
    }

    /**
     * IP 是否可用
     * @return
     */
    public boolean isWork() {
        String reqUrl = "http://undera.cn/";
        int statusCode = 0;
        this.status = GlobalName.IP_OFFLINE;
        this.useTimes++;
        this.badTimes++;
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getHostConfiguration().setProxy(this.getIp(), this.getPort());

            // 连接超时时间（默认10秒 10000ms） 单位毫秒（ms）
            int connectionTimeout = 2000;
            // 读取数据超时时间（默认30秒 30000ms） 单位毫秒（ms）
            int soTimeout = 2000;
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
            HttpMethod method = new GetMethod(reqUrl);
            statusCode = httpClient.executeMethod(method);
        } catch (Exception e) {
            System.out.println("ip " + this.getIp() + " is not aviable");
            return false;
        }
        if(statusCode > 0){
            System.out.format("got good ip %s  %s:%s\n", statusCode, this.getIp(), this.getPort());
            if(statusCode == 200) {
                this.status = GlobalName.IP_FREE;
                this.badTimes = 0;
                return true;
            }
        }
        return false;
    }

    public int getBadTimes() {
        return badTimes;
    }

    public void setBadTimes(int badTimes) {
        this.badTimes = badTimes;
    }

    public void notwork(){
        this.badTimes++;
    }

    public void addUse(){
        this.useTimes++;
    }

    public int getUseTimes() {
        return useTimes;
    }

    public void setUseTimes(int useTimes) {
        this.useTimes = useTimes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
