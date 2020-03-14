package com.buct.graduation.controller;

import com.buct.graduation.model.pojo.recruit.Station;
import com.buct.graduation.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class WebController {
    @Autowired
    private StationService stationService;

    @RequestMapping("/index")
    public String index(Model model){
        List<Station> list = stationService.findAllStations();
        model.addAttribute("stations", list);
        return "/index";
    }
}
