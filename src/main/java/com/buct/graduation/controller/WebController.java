package com.buct.graduation.controller;

import com.buct.graduation.model.pojo.User;
import com.buct.graduation.model.pojo.recruit.Station;
import com.buct.graduation.service.StationService;
import com.buct.graduation.util.GlobalName;
import com.buct.graduation.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.GapContent;
import java.util.List;

@Controller
public class WebController {
    @Autowired
    private StationService stationService;

    private User getUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(GlobalName.session_user);
        if(user == null){
            user = new User();
            user.setName("登录");
            user.setPicPath(GlobalName.PIC_PATH);
        }
        return user;
    }

    @RequestMapping("/index")
    public String index(Model model){
        List<Station> list = stationService.findAllStations();
        model.addAttribute("stations", list);
        return "/index";
    }

    @RequestMapping("/jobs")
    public String showJobs(Model model, HttpServletRequest request){
        //当前页码
        int page = 0;
        //当前页显示数量
        int number = 20;
        String s1 = request.getParameter("page");
        String s2 = request.getParameter("action");
        if(!(s1 == null || s1.length() == 0)){
            page = Integer.parseInt(s1);
        }
        if(s2 == null || s2.length() == 0 ||!s2.equals("next")){
            page--;
        }else {
            page++;
        }
        int max = stationService.findAllStations().size();
        if(page <= 0 || (page-1)*10 > max){
            page = 1;
        }

        //给自己挖坑 睡觉了
        List<Station> stations = stationService.findJobsWithPage(page, number);
        model.addAttribute("jobs", stations);
        model.addAttribute("page", page);

//        model.addAttribute("number", number);
        model.addAttribute("user", getUser(request));
        return "/job_list";
    }
}
