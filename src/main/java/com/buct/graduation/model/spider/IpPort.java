package com.buct.graduation.model.spider;

public class IpPort {
    private Integer id;
    private String ip = "";
    private Integer port;
    private String type = "http";
    private String status = "free";//free/busy/offline
    public volatile int times = 0;

    public IpPort(){

    }

    public IpPort(String ip, int port, String type){
        this.ip = ip;
        this.port = port;
        this.type = type;
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
