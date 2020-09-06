package com.buct.graduation.controller;

import com.buct.graduation.service.IpService;
import com.buct.graduation.service.SpiderService;
import com.buct.graduation.util.ThreadPoolUtil;
import com.buct.graduation.util.spider.IpPoolUtil;
import com.buct.graduation.util.spider.SpiderLetpubJournal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ApiController {
    @Autowired
    private SpiderService spiderService;
    @Autowired
    private IpService ipService;

    @RequestMapping("/test")
    @ResponseBody
    public void test(){
        long tempTime = System.currentTimeMillis();
        long start = tempTime;
        SpiderLetpubJournal letpub = new SpiderLetpubJournal();
        List<String> kw = new ArrayList<>();
        kw.add("0378-4371");
        kw.add("FOREST ECOLOGY AND MANAGEMENT");
        for(String keyword: kw){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    letpub.getJournal(keyword);
                    System.out.println("耗时：" + (System.currentTimeMillis() - start) / 1000);
                }
            }).start();
        }
    }
    @RequestMapping("/add")
    @ResponseBody
    public void add(){
        long tempTime = System.currentTimeMillis();
        for(int i = 1; i < 200; i += 100) {
            int finalI = i;
            ThreadPoolUtil.getThreadPool().submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(IpPoolUtil.addIps(finalI, finalI + 99)+"tagtagtag");
                }
            });
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/revive")
    @ResponseBody
    public String revive(){
        System.out.println("revive");
        int op = 0;
        for(int i = 0; i < 10; i++) {
            op += IpPoolUtil.reviveIps(10);
        }
        return "bring back to life "+ op;
    }
}
