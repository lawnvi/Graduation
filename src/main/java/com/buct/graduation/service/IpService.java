package com.buct.graduation.service;

import com.buct.graduation.model.spider.IpPort;
import com.sun.corba.se.impl.orbutil.concurrent.Sync;

import java.util.Set;

public interface IpService {
    int addIp(IpPort ip);

    Set<IpPort> findIpByStatus(String status);

    int updateIp(IpPort ip);

    int updateIpByStatus(String status, String newStatus);

    void deleteIp(String status);

    //获得可用ip
    IpPort findFreeIP();

    //ip不够 添加
    int addIPs(int s, int e);

    //offline 复活赛 挂了i次的死啦死啦的
    int revive(int i);
}
