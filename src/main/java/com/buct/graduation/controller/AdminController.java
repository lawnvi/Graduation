package com.buct.graduation.controller;

import com.buct.graduation.model.pojo.recruit.Station;
import com.buct.graduation.service.StationService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private StationService stationService;


    /**
     * 注册
     */

    /**
     * 登录
     */

    /**
     * 招聘相关
     */
    private Station getStation(HttpServletRequest request){
        Station station = new Station();
        station.setTitle(request.getParameter("title"));
        station.setNumber(Integer.parseInt(request.getParameter("number")));//需求数量
        station.setStatus(request.getParameter("status"));//状态
        station.setStart(request.getParameter("start"));//发布时间
        station.setEnd(request.getParameter("end"));//截至时间
        station.setTreatment(request.getParameter("treatment"));//待遇
        station.setConditions(request.getParameter("conditions"));//任职条件
        station.setNotes(request.getParameter("notes"));//任职描述 做什么
        station.setProcess(request.getParameter("process"));//招聘流程
        station.setContacts(request.getParameter("contacts"));//联系人
        station.setTel(request.getParameter("tel"));//联系电话
        station.setEmail(request.getParameter("email"));//联系邮箱
        station.setContactAddress(request.getParameter("contactAddress"));//通讯地址
        station.setMajor(request.getParameter("major"));
        station.setMaxAge(Integer.parseInt(request.getParameter("maxAge")));
        station.setEducation(request.getParameter("education"));
        return station;
    }
    //岗位发布
    @RequestMapping("/post")
    public String postStation(Model model){
        Station station = new Station();
        model.addAttribute("station", station);
        return "/admin/rcb/post";
    }
    @RequestMapping("/post.do")
    public String postMethod(HttpServletRequest request){
        Station station = getStation(request);
        //是不是返回id
        int id = stationService.postStation(station);
        return "redirect:./showStations";
    }

    //岗位修改
    @RequestMapping("/update")
    public String updateStation(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        Station station = stationService.findStationById(id);
        model.addAttribute("station", station);
        return "/admin/rcb/post";
    }
    @RequestMapping("/update.do")
    public String updateMethod(HttpServletRequest request){
        Station station = getStation(request);
        int id = Integer.parseInt(request.getParameter("id"));
        station.setId(id);
        stationService.updateStation(station);
        return "redirect:./showStation?id="+id;
    }

    //后台岗位展示
    //详情
    @RequestMapping("/showStation")
    public String showStation(Model model, HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        Station station = stationService.findStationById(id);
        model.addAttribute("station", station);
        return "/admin/rcb/station";
    }
    //列表
    @RequestMapping("/showStations")
    public String showStations(Model model){
        List<Station> list = stationService.findAllStations();
        model.addAttribute("stations", list);
        return "/admin/rcb/stations";
    }

}
