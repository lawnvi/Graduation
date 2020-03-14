package com.buct.graduation.controller;

import com.buct.graduation.model.pojo.*;
import com.buct.graduation.service.IpService;
import com.buct.graduation.service.SpiderService;
import com.buct.graduation.service.UserService;
import com.buct.graduation.util.EmailUtil;
import com.buct.graduation.util.GlobalName;
import com.buct.graduation.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SpiderService spiderService;
    @Autowired
    private IpService ipService;

    private int getThisId(HttpServletRequest request){
        return Integer.parseInt(Utils.getSession(request, GlobalName.session_userId));
    }

    /**
     * 注册流程，注册页面，邮箱验证码，注册
     * @return
     */
    @RequestMapping("/register")
    public String register(){
        return "/user/register";
    }
    //验证码
    @RequestMapping("/register.code")
    @ResponseBody
    public String getCode(HttpServletRequest request){
        String email = request.getParameter("email");
        if(userService.findUserByEmail(email) != null){
            return "existed";
        }
        String code = Utils.getCode();
        if(EmailUtil.sendMail(email, EmailUtil.register(code))){
            Utils.saveSession(email, code, request);
            return GlobalName.success;
        }
        return GlobalName.fail;
    }
    @RequestMapping("/register.do")
    @ResponseBody
    public String registerMethod(HttpServletRequest request){
        String code = request.getParameter("checkCode");
        String email = request.getParameter("email");
        String psw = request.getParameter("psw");
        //验证码检验
        if(!code.equals(Utils.getSession(request, email))){
            return GlobalName.fail;
        }
        User user = new User();
        user.setEmail(email);
        user.setPsw(psw);
        userService.register(user);
        Utils.removeSession(request, email);
        return GlobalName.success;
    }


    /**
     * 登录相关
     */
    @RequestMapping("/login")
    public String login(){
        return "/user/login";
    }

    @RequestMapping("/login.do")
    @ResponseBody
    public String loginMethod(HttpServletRequest request){
        String email = request.getParameter("email");
        String psw = request.getParameter("psw");
        User user = userService.login(email, psw);
        if(user == null){
            return GlobalName.fail;
        }
        Utils.saveSession(GlobalName.session_userId, user.getId()+"", request);
        return GlobalName.success;
    }

    /**
     * 信息展示，填写
     */
    //基本信息
    @RequestMapping("/myBasicData")
    public String showBasicData(Model model, HttpServletRequest request){
        int id = getThisId(request);
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "/user/data/myBasic";
    }

    @RequestMapping("/updateBasicData")
    public String fillBasicData(Model model, HttpServletRequest request){
        int id = getThisId(request);
        System.out.println("id"+id);
        User user = userService.findUserById(id);
        List<String> titles = Utils.toCheckbox(user.getTitle());
        List<String> funds = Utils.toCheckbox(user.getFund());
        HashMap<String, Boolean> title = new HashMap<>();
        HashMap<String, Boolean> fund = new HashMap<>();
        title.put("院士", titles.contains("院士"));
        title.put("千人", titles.contains("千人"));
        title.put("杰青", titles.contains("杰青"));
        title.put("优青", titles.contains("优青"));

        fund.put("NSFC青年基金", funds.contains("NSFC青年基金"));
        fund.put("NSFC面上基金", funds.contains("NSFC面上基金"));
        fund.put("NSFC重点基金", funds.contains("NSFC重点基金"));
        model.addAttribute("title", titles);
        model.addAttribute("fund", funds);
        model.addAttribute("user", user);
        return "/user/data/updateBasic";
    }
    @RequestMapping("/updateBasicData.do")
    public String fillBasicDataMethod(HttpServletRequest request, Model model){
        int id = getThisId(request);
        User user = userService.findUserById(id);
        user.setName(request.getParameter("name"));
        user.setSex(request.getParameter("sex"));
        user.setBirthday(request.getParameter("birthday"));
        user.setContactAddress(request.getParameter("contactAddress"));
        user.setMajor(request.getParameter("major"));
        user.setNotes(request.getParameter("notes"));
        user.setTel(request.getParameter("tel"));
        user.setEducation(request.getParameter("education"));
        String[] titles = request.getParameterValues("title");
        user.setTitle(Utils.checkboxToString(titles));
        String[] funds = request.getParameterValues("fund");
        user.setFund(Utils.checkboxToString(funds));
        userService.updateUser(user);
        User user2 = userService.findUserById(id);
        model.addAttribute("user", user2);
        return "/user/data/myBasic";
    }

    //主持的项目
    private Project getProject(HttpServletRequest request){
        Project project = new Project();
        project.setName(request.getParameter("name"));
        project.setFunds(Double.parseDouble(request.getParameter("funds")));
        project.setRole(request.getParameter("role"));
        project.setNotes(request.getParameter("notes"));
        project.setUid(getThisId(request));
        return project;
    }

    @RequestMapping("/myProjects")
    public String myProjects(Model model, HttpServletRequest request){
        int id = getThisId(request);
        List<Project> list = userService.showProjects(id);
        model.addAttribute("projects", list);
        return "";
    }
    //添加
    @RequestMapping("/addProject")
    @ResponseBody
    public String addProject(HttpServletRequest request){
        Project project = getProject(request);
        if(userService.addProject(project) > 0)
            return GlobalName.success;
        return GlobalName.fail;
    }
    //修改
    @RequestMapping("/updateProject")
    @ResponseBody
    public String updateProject(HttpServletRequest request){
        Project project = getProject(request);
        if(userService.updateProject(project) > 0)
            return GlobalName.success;
        return GlobalName.fail;
    }
    //删除
    @RequestMapping("/deleteProject")
    @ResponseBody
    public String deleteProject(HttpServletRequest request){
        int pid = Integer.parseInt(request.getParameter("pid"));
        if(userService.deleteProject(pid, getThisId(request)))
            return GlobalName.success;
        return GlobalName.fail; 
    }

    //期刊论文
    @RequestMapping("/myArticle")
    public String myArticle(Model model, HttpServletRequest request){
        int id = getThisId(request);
        List<Article> list = userService.showArticles(id);
        model.addAttribute("articles", list);
        return "";
    }

    private static int ops = 0;
    private static synchronized void countOps(){
        ops++;
    }
    @RequestMapping("/addArticle")
    @ResponseBody
    public String addArticles(Model model, HttpServletRequest request){
        Utils.ipPool.init(ipService.findIpByStatus("free"));
        int uid = getThisId(request);
        String notes = request.getParameter("notes");
        String role = request.getParameter("role");
        String title = request.getParameter("title");
        Article article = spiderService.searchPaperByName(title);

        UserArticle userArticle = new UserArticle();
        userArticle.setUid(uid);
        userArticle.setNotes(notes);
        userArticle.setRole(role);
        if(userService.addUserArticle(article, userArticle) > 0){
            return GlobalName.success;
        }
        Utils.ipPool.closeIpPool();
        return GlobalName.fail;
    }

    @RequestMapping("/deleteUserArticle")
    @ResponseBody
    public String deleteUserArticle(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        if(userService.deleteUserArticle(id, getThisId(request)))
            return GlobalName.success;
        return GlobalName.fail;
    }
    //todo 手动添加/修改
    
    //专利
    private Patent getPatent(HttpServletRequest request){
        Patent patent = new Patent();
        patent.setName(request.getParameter("name"));
        patent.setUid(getThisId(request));
        patent.setCategory(request.getParameter("category"));
        patent.setNotes(request.getParameter("notes"));
        patent.setRole(request.getParameter("role"));
        return patent;
    }
    @RequestMapping("/myPatents")
    public String myPatents(Model model, HttpServletRequest request){
        int id = getThisId(request);
        List<Project> list = userService.showProjects(id);
        model.addAttribute("patents", list);
        return "";
    }

    @RequestMapping("/addPatent")
    @ResponseBody
    public String addPatent(HttpServletRequest request){
        Patent patent = getPatent(request);
        if(userService.addPatent(patent) > 0)
            return GlobalName.success;
        return GlobalName.fail;
    }
    
    @RequestMapping("/updatePatent")
    @ResponseBody
    public String updatePatent(HttpServletRequest request){
        Patent patent = getPatent(request);
        if(userService.updatePatent(patent) > 0)
            return GlobalName.success;
        return GlobalName.fail;
    }

    @RequestMapping("/deletePatent")
    @ResponseBody
    public String deletePatent(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        if(userService.deletePatent(id, getThisId(request)))
            return GlobalName.success;
        return GlobalName.fail;
    }
    
    //会议论文
    private ConferencePaper getConferencePaper(HttpServletRequest request){
        ConferencePaper paper = new ConferencePaper();
        paper.setName(request.getParameter("name"));
        paper.setConference(request.getParameter("conference"));
        paper.setCitation(Integer.parseInt(request.getParameter("citation")));
        paper.setNotes(request.getParameter("notes"));
        paper.setRole(request.getParameter("role"));
        paper.setSection(request.getParameter("section"));
        paper.setUid(getThisId(request));
        return paper;
    }

    @RequestMapping("/myConferencePapers")
    public String myConferencePapers(Model model, HttpServletRequest request){
        int id = getThisId(request);
        List<ConferencePaper> list = userService.showConferencePapers(id);
        model.addAttribute("ConferencePapers", list);
        return "";
    }

    @RequestMapping("/addConferencePaper")
    @ResponseBody
    public String addConferencePaper(HttpServletRequest request){
        ConferencePaper patent = getConferencePaper(request);
        if(userService.addConferencePaper(patent) > 0)
            return GlobalName.success;
        return GlobalName.fail;
    }

    @RequestMapping("/updateConferencePaper")
    @ResponseBody
    public String updateConferencePaper(HttpServletRequest request){
        ConferencePaper patent = getConferencePaper(request);
        if(userService.updateConferencePaper(patent) > 0)
            return GlobalName.success;
        return GlobalName.fail;
    }

    @RequestMapping("/deleteConferencePaper")
    @ResponseBody
    public String deleteConferencePaper(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        if(userService.deleteConferencePaper(id, getThisId(request)))
            return GlobalName.success;
        return GlobalName.fail;
    }

    /**
     * 投简历
     */
    @RequestMapping("/postResume.do")
    @ResponseBody
    public String postResumeMethod(HttpServletRequest request){
        int id = getThisId(request);
        int sid = Integer.parseInt(request.getParameter("sid"));//岗位id
        if(userService.postResume(id, sid) > 0)
            return GlobalName.success;
        return GlobalName.fail;
    }
}
