package com.buct.graduation.service;

import com.buct.graduation.model.spider.IpPort;

import java.util.Set;

public interface IpService {
    int addIp(IpPort ip);

    Set<IpPort> findIpByStatus(String status);

    int updateIp(IpPort ip);

    int updateIpByStatus(String status, String newStatus);

    void deleteIp(String status);
}
