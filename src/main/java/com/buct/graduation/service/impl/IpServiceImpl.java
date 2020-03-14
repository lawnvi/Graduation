package com.buct.graduation.service.impl;

import com.buct.graduation.mapper.IpMapper;
import com.buct.graduation.model.spider.IpPort;
import com.buct.graduation.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class IpServiceImpl implements IpService {
    @Autowired
    private IpMapper ipMapper;

    @Override
    public int addIp(IpPort ip) {
        return ipMapper.addIP(ip);
    }

    @Override
    public Set<IpPort> findIpByStatus(String status) {
        Set<IpPort> list = ipMapper.findIPsByStatus(status);
        if(status.equals("free")) {
            ipMapper.updateStatusByStatus("free", "busy");
        }
        return list;
    }

    @Override
    public int updateIp(IpPort ip) {
        return ipMapper.updateStatusById(ip);
    }

    @Override
    public int updateIpByStatus(String status, String newStatus) {
        return ipMapper.updateStatusByStatus(status, newStatus);
    }

    @Override
    public void deleteIp(String status) {
        ipMapper.deleteIPByStatus(status);
    }
}
