package com.buct.graduation.controller;

import com.buct.graduation.service.ScienceService;
import com.buct.graduation.util.Utils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/adminUser/research/")
public class ScienceController {
    @Autowired
    private ScienceService scienceService;

    @RequestMapping("/overview")
    public String showData(HttpServletRequest request, Model model){
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("articles", scienceService.getArticles());
        model.addAttribute("papers", scienceService.getConferencePapers());
        model.addAttribute("patents", scienceService.getPatents());
        model.addAttribute("projects", scienceService.getProjects());
        return "/admin/xsb/science_data";
    }

    @RequestMapping("/article")
    public String showArticles(HttpServletRequest request, Model model){
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("articles", scienceService.getArticles());
        return "/admin/xsb/articles";
    }

    @RequestMapping("/meetingPaper")
    public String showPapers(HttpServletRequest request, Model model){
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("papers", scienceService.getConferencePapers());
        return "/admin/xsb/papers";
    }

    @RequestMapping("/project")
    public String showProjects(HttpServletRequest request, Model model){
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("projects", scienceService.getProjects());
        return "/admin/xsb/projects";
    }

    @RequestMapping("/patent")
    public String showPatents(HttpServletRequest request, Model model){
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("patents", scienceService.getPatents());
        return "/admin/xsb/patents";
    }

    @RequestMapping("/journal")
    public String showJournals(HttpServletRequest request, Model model){
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("patents", scienceService.getPatents());
        return "/admin/xsb/journals";
    }
}
