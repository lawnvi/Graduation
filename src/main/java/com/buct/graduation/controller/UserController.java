package com.buct.graduation.controller;

import com.buct.graduation.model.pojo.*;
import com.buct.graduation.model.pojo.recruit.Interview;
import com.buct.graduation.model.spider.ProjectData;
import com.buct.graduation.model.vo.Apply;
import com.buct.graduation.service.IpService;
import com.buct.graduation.service.SpiderService;
import com.buct.graduation.service.UserService;
import com.buct.graduation.util.EmailUtil;
import com.buct.graduation.util.GlobalName;
import com.buct.graduation.util.Utils;
import com.buct.graduation.util.spider.SpiderConfig;
import com.buct.graduation.util.spider.SpiderLetpubProject;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
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
        HttpSession session = request.getSession();
        return ((User)session.getAttribute(GlobalName.session_user)).getId();
    }

    private User getUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        return (User)session.getAttribute(GlobalName.session_user);
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
     * 密码找回
     */
    @RequestMapping("/resetPsw")
    public String resetPsw(){
        return "/user/recovery_password";
    }
    //验证码
    @RequestMapping("/resetPsw.code")
    @ResponseBody
    public String getCode2ResetPsw(HttpServletRequest request){
        String email = request.getParameter("email");
        if(userService.findUserByEmail(email) == null){
            return "!existed";
        }
        String code = Utils.getCode();
        if(EmailUtil.sendMail(email, EmailUtil.resetPsw(code))){
            Utils.saveSession(email, code, request);
            return GlobalName.success;
        }
        return GlobalName.fail;
    }
    @RequestMapping("/resetPsw.do")
    @ResponseBody
    public String resetPswMethod(HttpServletRequest request){
        String code = request.getParameter("checkCode");
        String email = request.getParameter("email");
        String psw = request.getParameter("psw");
        //验证码检验
        if(!code.equals(Utils.getSession(request, email))){
            return GlobalName.fail;
        }
        if(psw.length() < 6){
            return GlobalName.fail;
        }
        User user = userService.findUserByEmail(email);
        user.setPsw(psw);
        userService.changePsw(user);
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
        String sid = request.getParameter("sid");
        if(email == null || email.equals("") || psw == null || psw.equals("")){
            return GlobalName.fail;
        }
        User user = userService.login(email, psw);
        if(user == null){
            return GlobalName.fail;
        }
        //Utils.saveSession(GlobalName.session_userId, user.getId()+"", request);
        HttpSession session = request.getSession();
        user.setPsw("");
        session.setAttribute(GlobalName.session_user, user);
        //时限600s
        session.setMaxInactiveInterval(600);
        SpiderConfig.setSessionSID(sid);
        return GlobalName.success;
    }

    @RequestMapping("/logout")
    public String logoutMethod(HttpServletRequest request){
        Utils.removeSession(request, GlobalName.session_user);
        //todo 去首页
        return "redirect: ../index";
    }

    @RequestMapping("/changePsw")
    public String changePsw(){
        return "redirect:./resetPsw";
    }

    /**
     * 信息展示，填写
     */
    //基本信息
    @RequestMapping("/information")
    public String showBasicData(Model model, HttpServletRequest request){
        int id = getThisId(request);
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "/user/data/basicInfo";
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
    @RequestMapping("/updateBasicInfo.do")
    public String fillBasicDataMethod(HttpServletRequest request, Model model, MultipartFile file, MultipartFile picture){
        int id = getThisId(request);
        User user = userService.findUserById(id);
        user.setName(request.getParameter("name"));
        user.setSex(request.getParameter("sex"));
        user.setBirthday(request.getParameter("birthday"));
        user.setContactAddress(request.getParameter("contactAddress"));
        user.setMajor(request.getParameter("major"));
        user.setNotes(request.getParameter("notes"));
        user.setStatus(request.getParameter("status"));
        user.setTel(request.getParameter("tel"));
        user.setEducation(request.getParameter("education"));
        user.setResumePath(request.getParameter("resumePath"));
        String[] titles = request.getParameterValues("titles");
        user.setTitle(Utils.checkboxToString(titles));
        String[] funds = request.getParameterValues("funds");
        user.setFund(Utils.checkboxToString(funds));
        //System.out.println("funds:"+ Arrays.toString(request.getParameterValues("funds")));
        //System.out.println("titles:"+ Arrays.toString(request.getParameterValues("titles")));
        if(file != null && !file.isEmpty()){
            String fileName = file.getOriginalFilename();
            String path = GlobalName.RESUME_PATH + Utils.getTodayPath();
            path = Utils.saveFileRelativePath(file, path);
            if(path.equals("")){
                System.out.println("upload error");
            }
            else {
                System.out.println("upload good");
                user.setResumePath(path);
            }
        }
        if(picture != null && !picture.isEmpty()){
            String fileName = picture.getOriginalFilename();
            String path = GlobalName.IMAGE_PATH + Utils.getTodayPath();
            path = Utils.saveFileRelativePath(picture, path);
            if(path.equals("")){
                System.out.println("upload error");
            }
            else {
                System.out.println("upload good");
                user.setPicPath(path);
            }
        }
        userService.updateUser(user);
        User user2 = userService.findUserById(id);

        Utils.removeSession(request, GlobalName.session_user);
        HttpSession session = request.getSession();
        user.setPsw("");
        session.setAttribute(GlobalName.session_user, user2);
        //时限600s
        session.setMaxInactiveInterval(600);
        model.addAttribute("user", user2);
        return "/user/data/basicInfo";
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

    @RequestMapping("/projects")
    public String myProjects(Model model, HttpServletRequest request){
        int id = getThisId(request);
        List<Project> list = userService.showProjects(id);
        model.addAttribute("projects", list);
        model.addAttribute("user", getUser(request));
        return "/user/data/projects";
    }
    //添加
    @RequestMapping("/addProject")
    @ResponseBody
    public String addProject(HttpServletRequest request){
        Project project = getProject(request);
        Utils.checkProject(project);
        String msg = "";
        if(project.getCharge() != null && !getUser(request).getName().equals(project.getCharge())){
            msg =  " 负责人与用户姓名不匹配，请确认归属";
        }
        return userService.addProject(project)+msg;
    }
    //修改
    @RequestMapping("/updateProject")
    @ResponseBody
    public String updateProject(HttpServletRequest request){
        Project project = getProject(request);
        project.setId(Integer.parseInt(request.getParameter("id")));
        Utils.checkProject(project);
        String msg = "";
        if(project.getCharge() != null && !getUser(request).getName().equals(project.getCharge())){
            msg =  " 负责人与用户不匹配，请确认归属";
        }
        if(userService.updateProject(project) > 0)
            return GlobalName.success;
        return GlobalName.fail;
    }
    //删除
    @RequestMapping("/deleteProjects")
    @ResponseBody
    public String deleteProject(HttpServletRequest request){
        String[] str = request.getParameterValues("id[]");
        if(str == null ||str.length == 0)
            return GlobalName.fail;
        for(String s: str){
            System.out.println(s);
            if(!userService.deleteProject(Integer.parseInt(s), getThisId(request))){
                return GlobalName.fail;
            }
        }
        return GlobalName.success;
    }

    //期刊论文
    @RequestMapping("/articles")
    public String myArticle(Model model, HttpServletRequest request){
        int id = getThisId(request);
        List<UserArticle> list = userService.showArticles(id);
        model.addAttribute("myArticles", list);
        model.addAttribute("user", getUser(request));
        return "/user/data/articles";
    }

    private static int ops = 0;
    private static synchronized void countOps(){
        ops++;
    }
    @RequestMapping("/addArticle")
    @ResponseBody
    public synchronized String addArticles(Model model, HttpServletRequest request){
        int uid = getThisId(request);
        String notes = request.getParameter("notes");
        String role = request.getParameter("role");
        String title = request.getParameter("title");
        Article a = new Article();
        a.setName(title);
        a.setId(-1);
        Article article = spiderService.searchPaper(a);
        UserArticle userArticle = new UserArticle();
        userArticle.setUid(uid);
        userArticle.setNotes(notes);
        userArticle.setRole(role);
        userArticle.setFlag(GlobalName.teacher_flag_other);
        if(userService.addUserArticle(article, userArticle) > 0){
            return GlobalName.success;
        }
        return GlobalName.fail;
    }

    //手动录入 管理员检查 手动就不必了
//    @RequestMapping("/addArticleByHand")
//    @ResponseBody
    public String addArticleWithHand(Model model, HttpServletRequest request){
        int uid = getThisId(request);
        String notes = request.getParameter("notes");
        String role = request.getParameter("role");
        String title = request.getParameter("title");
        boolean isEsi = request.getParameter("isEsi").equalsIgnoreCase("true");
        int citation = Integer.parseInt(request.getParameter("citation"));
        String journal = request.getParameter("journal");
        Article a = new Article();
        a.setName(title);
        a.setNotes(notes);
        a.setJournalIssn(journal);
        a.setCitation(citation);
        a.setESI(isEsi);
        Article article = spiderService.searchPaper(a);
        UserArticle userArticle = new UserArticle();
        userArticle.setUid(uid);
        userArticle.setNotes(notes);
        userArticle.setRole(role);
        if(userService.addUserArticle(article, userArticle) > 0){
            return GlobalName.success;
        }
        return GlobalName.fail;
    }

    @RequestMapping("/deleteUserArticles")
    @ResponseBody
    public String deleteUserArticle(HttpServletRequest request){
        String[] str = request.getParameterValues("id[]");
        if(str == null ||str.length == 0)
            return GlobalName.fail;
        for(String s: str){
            System.out.println(s);
            if(!userService.deleteUserArticle(Integer.parseInt(s), getThisId(request))){
                return GlobalName.fail;
            }
        }
        return GlobalName.success;
    }
    //todo 手动添加/修改
    @RequestMapping("/updateArticle")
    @ResponseBody
    public synchronized String updateArticles(Model model, HttpServletRequest request){
        int uid = getThisId(request);
        String notes = request.getParameter("notes");
        String role = request.getParameter("role");
        String title = request.getParameter("title");
        int id = Integer.parseInt(request.getParameter("id"));
        String journal = request.getParameter("journal");
        Article a = new Article();
        a.setName(title);
        a.setId(id);
        a.setJournalIssn(journal);
        System.out.println(uid+" "+title);
        Article article = spiderService.searchPaper(a);
        System.out.println(article.getJid()+ " "+article.getId());
        if(!article.getAddWay().equals(GlobalName.addWay_System)){
            article.setJournalIssn(journal);
            Journal journal1 = spiderService.getJournal(journal);
            if(journal1 != null) {
                article.setJournal(journal1);
                article.setJid(journal1.getId());
            }
        }

        if(article.getId() == null){
            //论文名也能拿到id
            article.setId(id);
        }
        UserArticle userArticle = new UserArticle();
        userArticle.setUid(uid);
        userArticle.setNotes(notes);
        userArticle.setRole(role);
        userArticle.setAid(article.getId());

        if(userService.updateUserArticle(id, article, userArticle) > 0){
            return GlobalName.success;
        }
        return GlobalName.fail;
    }
    
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
    @RequestMapping("/patents")
    public String myPatents(Model model, HttpServletRequest request){
        int id = getThisId(request);
        List<Patent> list = userService.showPatents(id);
        model.addAttribute("patents", list);
        model.addAttribute("user", getUser(request));
        return "/user/data/patents";
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
        patent.setId(Integer.parseInt(request.getParameter("id")));
        if(userService.updatePatent(patent) > 0)
            return GlobalName.success;
        return GlobalName.fail;
    }

    @RequestMapping("/deletePatents")
    @ResponseBody
    public String deletePatent(HttpServletRequest request){
        String[] str = request.getParameterValues("id[]");
        if(str == null ||str.length == 0)
            return GlobalName.fail;
        for(String s: str){
            System.out.println(s);
            if(!userService.deletePatent(Integer.parseInt(s), getThisId(request))){
                return GlobalName.fail;
            }
        }
        return GlobalName.success;
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
        paper.setEsi(request.getParameter("isEsi").equalsIgnoreCase("true"));
        paper.setUid(getThisId(request));
        return paper;
    }

    @RequestMapping("/papers")
    public String myConferencePapers(Model model, HttpServletRequest request){
        int id = getThisId(request);
        List<ConferencePaper> list = userService.showConferencePapers(id);
        model.addAttribute("papers", list);
        model.addAttribute("user", getUser(request));
        return "/user/data/papers";
    }

    @RequestMapping("/addConferencePaper")
    @ResponseBody
    public String addConferencePaper(HttpServletRequest request){
        ConferencePaper paper = getConferencePaper(request);
        paper.setBelong(GlobalName.belongApply);
        if(userService.addConferencePaper(paper) > 0)
            return GlobalName.success;
        return GlobalName.fail;
    }

    @RequestMapping("/updateConferencePaper")
    @ResponseBody
    public String updateConferencePaper(HttpServletRequest request){
        ConferencePaper patent = getConferencePaper(request);
        patent.setId(Integer.parseInt(request.getParameter("id")));
        if(userService.updateConferencePaper(patent) > 0)
            return GlobalName.success;
        return GlobalName.fail;
    }

    @RequestMapping("/deleteConferencePaper")
    @ResponseBody
    public String deleteConferencePaper(HttpServletRequest request){
        String[] str = request.getParameterValues("id[]");
        if(str == null ||str.length == 0)
            return GlobalName.fail;
        for(String s: str){
            System.out.println(s);
            if(!userService.deleteConferencePaper(Integer.parseInt(s), getThisId(request))){
                return GlobalName.fail;
            }
        }
        return GlobalName.success;
    }

    /**
     * 投简历
     */
    @RequestMapping("/postResume.do")
    @ResponseBody
    public String postResumeMethod(HttpServletRequest request){
        if(getUser(request) == null){
            return "login";
        }
        int id = getThisId(request);
        int sid = Integer.parseInt(request.getParameter("sid"));//岗位id
        return userService.postResume(id, sid);
    }

    @RequestMapping("/interview.coming")
    public String getInterviewComing(HttpServletRequest request, Model model){
        List<Apply> applies = userService.findComingInterview(getThisId(request));
        model.addAttribute("user", getUser(request));
        model.addAttribute("applies", applies);
        return "/user/interview_coming";
    }

    @Deprecated
    @RequestMapping("/interviewLog")
    public String getInterviewLog(HttpServletRequest request){
        return "/errorPage";
    }

    @RequestMapping("/apply")
    public String getApply(HttpServletRequest request, Model model){
        List<Apply> applies = userService.findApply(getThisId(request));
        model.addAttribute("applies", applies);
        model.addAttribute("user", getUser(request));
        return "/user/apply";
    }


    /**
     * api for login
     */
    @RequestMapping("/login.api")
    public String loginApi(HttpServletRequest request, Model model){
        String email = request.getParameter("email");
        String psw = request.getParameter("psw");
        String sid = request.getParameter("sid");
        String result = "未知错误";
        if(email == null || email.equals("") || psw == null || psw.equals("")){
            result = "请完整填写账号信息";
            model.addAttribute("result", result);
            return "/user/simple_index";
        }
        User user = userService.login(email, psw);
        if(user == null){
            result = "请核对账号信息";
            model.addAttribute("result", result);
            return "/user/simple_index";
        }
        if(sid == null){
            result = "请重新获取SID";
            model.addAttribute("result", result);
            return "/user/simple_index";
        }
        //Utils.saveSession(GlobalName.session_userId, user.getId()+"", request);
        HttpSession session = request.getSession();
        user.setPsw("");
        session.setAttribute(GlobalName.session_user, user);
        //时限600s
        session.setMaxInactiveInterval(600);
        SpiderConfig.setSessionSID(sid);
        result = "登录成功，当前sid为:"+sid +" 已使用"+ SpiderConfig.getSIDTimes() + "次";

        return "redirect:./login.api.awsl";
    }

    @RequestMapping("/login.api.awsl")
    public String loginApiPage(HttpServletRequest request, Model model){
        User user = getUser(request);
        String sid = SpiderConfig.getSID();
//        result = "当前sid为:"+sid +" 已使用"+ SpiderConfig.getSIDTimes() + "次";
        model.addAttribute("sid", sid);
        model.addAttribute("user", user);
        model.addAttribute("sid_times", SpiderConfig.getSIDTimes()-1);
        return "/user/simple_index";
    }
}
