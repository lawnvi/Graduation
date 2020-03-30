package com.buct.graduation.service.impl;

import com.buct.graduation.mapper.IpMapper;
import com.buct.graduation.model.spider.IpPort;
import com.buct.graduation.service.IpService;
import com.buct.graduation.util.GlobalName;
import com.buct.graduation.util.ThreadPoolUtil;
import com.buct.graduation.util.spider.SpiderXiciIp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public synchronized IpPort findFreeIP() {
        IpPort ipPort = new IpPort();
        while (true){
            ipPort = ipMapper.findIPByStatus(GlobalName.IP_FREE);
            if(ipPort == null){
                return null;//快加ip
            }
            if(ipPort.isWork()){
                ipPort.setStatus(GlobalName.IP_BUSY);
                ipMapper.update(ipPort);
                return ipPort;
            }else {
                ipPort.setStatus(GlobalName.IP_OFFLINE);
                ipPort.setBadTimes(ipPort.getBadTimes() + 1);
                ipMapper.update(ipPort);
            }
        }
    }

    @Override
    public synchronized int addIPs(int s, int e) {
        SpiderXiciIp xiciIp = new SpiderXiciIp();
        Set<IpPort> set = xiciIp.findUsefulIp(s, e);
        for(IpPort ip: set){
            IpPort ipPort = ipMapper.findIPsByIp(ip.getIp());
            if(ipPort == null) {
                ipMapper.addIP(ip);
            }else {
                if(ipPort.getStatus().equals(GlobalName.IP_OFFLINE)){
                    ip.setUseTimes(ipPort.getUseTimes());
                    ip.setId(ipPort.getId());
                    ipMapper.update(ip);
                }
            }
        }
        return set.size();
    }

    @Override
    @Transactional
    public int revive(int i){
        Set<IpPort> ips = ipMapper.findIPsByStatus(GlobalName.IP_OFFLINE);
//        int op = 0;
        for(IpPort ip: ips){
            ThreadPoolUtil.getThreadPool().submit(new Runnable() {
                @Override
                public void run() {
                    if(ip.isWork()){
                        ip.setStatus(GlobalName.IP_FREE);
                        ip.setBadTimes(0);
//                        op++;
                    }
                    if(ip.getBadTimes() > i) {
                        ipMapper.deleteIP(ip);
                    }else {
                        ipMapper.update(ip);
                    }
                }
            });
        }
        return 0;
    }
}
