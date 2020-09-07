package com.buct.graduation.util.spider.ip;

import com.buct.graduation.model.spider.IpPort;

import java.util.HashSet;
import java.util.Set;

/**
 * 找不要钱的ip的接口
 */
public interface IPNet {
    //获取链接中的ip
    HashSet<IpPort> findIPs(String url);
    //扫荡这个网站
    HashSet<IpPort> MopUp();
}
