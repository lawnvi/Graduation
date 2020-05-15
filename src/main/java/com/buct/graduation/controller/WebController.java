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
    public String index(Model model, HttpServletRequest request){
        List<Station> list = stationService.findAllStations();
        model.addAttribute("stations", list);
        model.addAttribute("user", getUser(request));
        return "/index2";
    }

    @RequestMapping("/jobs")
    public String showJobs(Model model, HttpServletRequest request){
        String keyword = request.getParameter("keyword");
        //当前页码
        int page = 0;
        //当前页显示数量
        int number = 10;
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
        List<Station> stations = stationService.findJobsWithPage(page, number, keyword);
        model.addAttribute("jobs", stations);
        model.addAttribute("page", page);
        model.addAttribute("kw", keyword);

//        model.addAttribute("number", number);
        model.addAttribute("user", getUser(request));
        return "/job_list";
    }

    @RequestMapping("/jobDetail")
    public String jobDetails(HttpServletRequest request, Model model){
        String sid = request.getParameter("sid");
        if(sid == null || "".equals(sid)){
            return "/errorPage";
        }
        int id = Integer.parseInt(sid);
        Station station = stationService.findStationById(id);
        model.addAttribute("job", station);
        model.addAttribute("user", getUser(request));
        return "/job_details";
    }

    @RequestMapping("/about")
    public String about(HttpServletRequest request, Model model){
        model.addAttribute("user", getUser(request));
        return "/about";
    }
}
