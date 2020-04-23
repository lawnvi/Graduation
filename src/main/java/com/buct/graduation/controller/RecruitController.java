package com.buct.graduation.controller;

import com.buct.graduation.model.pojo.*;
import com.buct.graduation.model.pojo.recruit.Interview;
import com.buct.graduation.model.pojo.recruit.Resume;
import com.buct.graduation.model.pojo.recruit.Station;
import com.buct.graduation.model.vo.Apply;
import com.buct.graduation.service.ArticleService;
import com.buct.graduation.service.ResumeService;
import com.buct.graduation.service.StationService;
import com.buct.graduation.service.UserService;
import com.buct.graduation.util.GlobalName;
import com.buct.graduation.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 招聘相关
 */
@Controller
@RequestMapping("/adminUser/recruit/")
public class RecruitController {

    @Autowired
    private StationService stationService;
    @Autowired
    private ResumeService resumeService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

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
    public String postStation(Model model, HttpServletRequest request){
        Station station = new Station();
        model.addAttribute("flag", "new");
        model.addAttribute("station", station);
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("stationData", stationService.findStationData());
        return "/admin/rcb/post_station";
    }
    @RequestMapping("/post.do")
    public String postMethod(HttpServletRequest request){
        Station station = getStation(request);
        //是不是返回id
        int id = stationService.postStation(station);
        return "redirect:./stations";
    }

    //岗位修改
    @RequestMapping("/stationUpdate")
    public String updateStation(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        Station station = stationService.findStationById(id);
        model.addAttribute("flag", "update");
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("stationData", stationService.findStationData());
        model.addAttribute("station", station);
        return "/admin/rcb/post_station";
    }
    @RequestMapping("/stationUpdate.do")
    public String updateMethod(HttpServletRequest request, Model model){
        Station station = getStation(request);
        int id = Integer.parseInt(request.getParameter("id"));
        station.setId(id);
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("stationData", stationService.findStationData());
        stationService.updateStation(station);
        return "redirect:./station?id="+id;
    }

    //后台岗位展示
    //详情
    @RequestMapping("/station")
    public String showStation(Model model, HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        String status = getStatus(request.getParameter("status"));
        Station station = stationService.findStationById(id);
        model.addAttribute("status", status);
        model.addAttribute("station", station);
        model.addAttribute("resumes", resumeService.findResumesBySid(id));
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("stationData", stationService.findStationData());
        return "/admin/rcb/show_station";
    }
    //列表
    @RequestMapping("/stations")
    public String showStations(Model model, HttpServletRequest request){
        List<Station> list = stationService.findAllStations();
        model.addAttribute("stations", list);
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("stationData", stationService.findStationData());
        return "/admin/rcb/show_stations";
    }
    @RequestMapping("/stations2")
    public String showStationsOn(Model model, HttpServletRequest request){
        String status = request.getParameter("status");
        List<Station> list = stationService.findStationsByStatus(status);
        model.addAttribute("stations", list);
        model.addAttribute("status", status);
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("stationData", stationService.findStationData());
        return "/admin/rcb/show_stations_status";
    }

    private String getStatus(String str){
        String status = "";
        if(str == null){
            str = "";
        }
        switch (str){
            case "wait": status = GlobalName.resume_wait; break;
            case "interview": status = GlobalName.resume_processing; break;
            case "pass": status = GlobalName.resume_pass; break;
            case "other": status = GlobalName.resume_fail; break;
            default:status = "全部简历";break;
        }
        return status;
    }

    @RequestMapping("/resume")
    public String showResume(HttpServletRequest request, Model model){
        String str = request.getParameter("status");
        String status = getStatus(str);

        model.addAttribute("status", status);
        model.addAttribute("user", Utils.getAdmin(request));
        if(status.equals("全部简历")) {
            model.addAttribute("resumes", resumeService.findAllResumes());
        }else {
            model.addAttribute("resumes", resumeService.findResumesByStatus(status));
        }
        return "/admin/rcb/resumes";
    }

    @RequestMapping("/responseResume")
    @ResponseBody
    public String responseResume(HttpServletRequest request){
        String keyword = request.getParameter("keyword");
//        int sid = Integer.parseInt(request.getParameter("sid"));
        int rid = Integer.parseInt(request.getParameter("rid"));
        return resumeService.responseResume(rid, keyword);
    }

    @RequestMapping("/userDetail")
    public String resumeDetail(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        Resume resume = resumeService.findResumeById(id);
        if(resume == null){
            //todo 边界未知错误
        }
        model.addAttribute("resume", resume);
        model.addAttribute("user", Utils.getAdmin(request));
        return "/admin/rcb/resume_detail";
    }

    private Resume findResume(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        return resumeService.findResumeById(id);
    }

    @RequestMapping("/article")
    public String resumeArticle(HttpServletRequest request, Model model){
        Resume resume = findResume(request);
        int uid = resume.getUid();
        model.addAttribute("resume", resume);
        List<Article> articles = articleService.findByUid(uid);
        model.addAttribute("articles", articles);
        model.addAttribute("uid", uid);
        model.addAttribute("user", Utils.getAdmin(request));
        return "/admin/rcb/resumeData/article";
    }

    @RequestMapping("/article_wait")
    public String resumeArticleWait(HttpServletRequest request, Model model){
        Resume resume = findResume(request);
        int uid = resume.getUid();
        model.addAttribute("resume", resume);
        List<Article> articles = articleService.findByUidStatus(uid, GlobalName.addWay_missing_c);
        model.addAttribute("articles", articles);
        model.addAttribute("uid", uid);
        model.addAttribute("user", Utils.getAdmin(request));
        return "/admin/rcb/resumeData/article_wait";
    }

    @RequestMapping("/meeting")
    public String resumePaper(HttpServletRequest request, Model model){
        Resume resume = findResume(request);
        int uid = resume.getUid();
        model.addAttribute("resume", resume);
        List<ConferencePaper> papers = userService.showConferencePapers(uid);
        model.addAttribute("papers", papers);
        model.addAttribute("uid", uid);
        model.addAttribute("user", Utils.getAdmin(request));
        return "/admin/rcb/resumeData/paper";
    }

    @RequestMapping("/project")
    public String resumeProject(HttpServletRequest request, Model model){
        Resume resume = findResume(request);
        int uid = resume.getUid();
        model.addAttribute("resume", resume);
        List<Project> projects = userService.showProjects(uid);
        model.addAttribute("projects", projects);
        model.addAttribute("uid", uid);
        model.addAttribute("user", Utils.getAdmin(request));
        return "/admin/rcb/resumeData/project";
    }

    @RequestMapping("/project_wait")
    public String resumeProjectWait(HttpServletRequest request, Model model){
        Resume resume = findResume(request);
        int uid = resume.getUid();
        model.addAttribute("resume", resume);
        List<Project> projects = userService.showProjectsByStatus(uid, false);
        model.addAttribute("projects", projects);
        model.addAttribute("uid", uid);
        model.addAttribute("user", Utils.getAdmin(request));
        return "/admin/rcb/resumeData/project_wait";
    }

    @RequestMapping("/patent")
    public String resumePatent(HttpServletRequest request, Model model){
        Resume resume = findResume(request);
        int uid = resume.getUid();
        model.addAttribute("resume", resume);
        List<Patent> patents = userService.showPatents(uid);
        model.addAttribute("patents", patents);
        model.addAttribute("uid", uid);
        model.addAttribute("user", Utils.getAdmin(request));
        return "/admin/rcb/resumeData/patent";
    }

    @RequestMapping("/userApplies")
    public String getApply(HttpServletRequest request, Model model){
        Resume resume = findResume(request);
        int uid = resume.getUid();
        model.addAttribute("resume", resume);
        List<Apply> applies = userService.findApply(resume.getUid());
        model.addAttribute("applies", applies);
        model.addAttribute("uid", uid);
        model.addAttribute("user", Utils.getAdmin(request));
        return "/admin/rcb/resumeData/user_applies";
    }

    private Interview getInterview(HttpServletRequest request){
        Interview interview = new Interview();
        int id = 0;
        if(request.getParameter("id") != null && !request.getParameter("id").equals("")){
            id = Integer.parseInt(request.getParameter("id"));
        }
        interview.setId(id);
        interview.setRid(Integer.parseInt(request.getParameter("rid")));
        interview.setTime(request.getParameter("date"));
        interview.setPlace(request.getParameter("place"));
        interview.setAdvice(request.getParameter("advice"));
        int score = 0;
        if(request.getParameter("score") != null && !request.getParameter("score").equals("")){
            score = Integer.parseInt(request.getParameter("score"));
        }
        interview.setScore(score);
        switch (request.getParameter("status")){
            case "通过":interview.setStatus(GlobalName.interview_pass);break;
            case "未通过":interview.setStatus(GlobalName.interview_fail);break;
            default:interview.setStatus(GlobalName.interview_wait);break;
        }
        return interview;
    }

    @RequestMapping("/addInterviewPlan")
    @ResponseBody
    public String addInterview(HttpServletRequest request){
        Interview interview = getInterview(request);
        return resumeService.addInterview(interview) > 0 ? "添加成功" : "添加失败";
    }

    @RequestMapping("/updateInterview")
    @ResponseBody
    public String updateInterview(HttpServletRequest request){
        Interview interview = getInterview(request);
        return resumeService.updateInterview(interview) > 0 ? "更新成功" : "更新失败";
    }

    @RequestMapping("/pass")
    @ResponseBody
    public String acceptResume(HttpServletRequest request){
        int rid = Integer.parseInt(request.getParameter("rid"));
        boolean isPass = request.getParameter("flag").equals("pass");
        String msg = isPass ? "已接收": "已拒绝";
        return resumeService.isAcceptResume(rid, isPass) > 0 ? msg : "未知错误";
    }
}
