package com.buct.graduation.controller;

import com.buct.graduation.model.pojo.*;
import com.buct.graduation.model.pojo.science.Teacher;
import com.buct.graduation.model.spider.ProjectData;
import com.buct.graduation.service.SpiderService;
import com.buct.graduation.service.TeacherService;
import com.buct.graduation.service.UserService;
import com.buct.graduation.util.EmailUtil;
import com.buct.graduation.util.GlobalName;
import com.buct.graduation.util.Utils;
import com.buct.graduation.util.spider.SpiderLetpubProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserService userService;
    @Autowired
    private SpiderService spiderService;

    /**
     * 注册
     */
    @RequestMapping("/register")
    public String register(){
        return "/teacher/register";
    }
    //验证码

    @RequestMapping("/register.code")
    @ResponseBody
    public String getCode(HttpServletRequest request){
        String email = request.getParameter("email");
        if(teacherService.findByEmail(email) != null){
            return "existed";
        }
        String code = Utils.getCode();
        //加上teacher前缀，放置user混淆
        if(EmailUtil.sendMail(email, EmailUtil.register(code))){
            Utils.saveSession(GlobalName.type_teacher+email, code, request);
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
        if(!code.equals(Utils.getSession(request, GlobalName.type_teacher+email))){
            System.out.println(Utils.getSession(request, GlobalName.type_teacher+email));
            System.out.println(code);
            return "codeError";
        }
        Teacher teacher = new Teacher();
        User user = new User();
        user.setEmail(email);
        user.setPsw(psw);
        teacher.setUser(user);
        if(teacherService.register(teacher) <= 0)
            return GlobalName.fail;
        Utils.removeSession(request, GlobalName.type_teacher+email);
        return GlobalName.success;
    }

    /**
     * 找回密码
     */
    @RequestMapping("/resetPsw")
    public String resetPsw(){
        return "/teacher/recovery_password";
    }
    //验证码
    @RequestMapping("/resetPsw.code")
    @ResponseBody
    public String getCode2ResetPsw(HttpServletRequest request){
        String email = request.getParameter("email");
        if(teacherService.findByEmail(email) == null){
            return "!existed";
        }
        String code = Utils.getCode();
        if(EmailUtil.sendMail(email, EmailUtil.resetPsw(code))){
            Utils.saveSession(GlobalName.type_teacher+email, code, request);
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
        if(!code.equals(Utils.getSession(request, GlobalName.type_teacher+email))){
            return GlobalName.fail;
        }
        if(psw.length() < 6){
            return GlobalName.fail;
        }
        Teacher teacher = teacherService.findByEmail(email);
        teacher.getUser().setPsw(psw);
        teacherService.resetPsw(teacher);
        Utils.removeSession(request, GlobalName.type_teacher+email);
        return GlobalName.success;
    }

    /**
     * 登录
     */
    @RequestMapping("/login")
    public String login(){
        return "/teacher/login";
    }

    @RequestMapping("/login.do")
    @ResponseBody
    public String loginMethod(HttpServletRequest request){
        String email = request.getParameter("email");
        String psw = request.getParameter("psw");
        if(email == null || email.equals("") || psw == null || psw.equals("")){
            return GlobalName.fail;
        }
        Teacher teacher = new Teacher();
        User user = new User();
        user.setEmail(email);
        user.setPsw(psw);
        teacher.setUser(user);
        teacher = teacherService.login(teacher);
        if(teacher == null){
            return GlobalName.fail;
        }
        //Utils.saveSession(GlobalName.session_userId, user.getId()+"", request);
        HttpSession session = request.getSession();
        teacher.getUser().setPsw("");
        session.setAttribute(GlobalName.session_teacher, teacher);
        //时限600s
        session.setMaxInactiveInterval(600);
        return GlobalName.success;
    }

    @RequestMapping("/logout")
    public String logoutMethod(HttpServletRequest request){
        Utils.removeSession(request, GlobalName.session_teacher);
        //todo 去首页
        return "redirect:./login";
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
        Teacher teacher = Utils.getTeacher(request);
        model.addAttribute("user", teacher);
        return "/teacher/data/basicInfo";
    }

    @RequestMapping("/updateBasicInfo.do")
    public String fillBasicDataMethod(HttpServletRequest request, Model model){
        Teacher teacher = Utils.getTeacher(request);
        User user = teacher.getUser();
        user.setName(request.getParameter("name"));
        user.setSex(request.getParameter("sex"));
        user.setBirthday(request.getParameter("birthday"));
        user.setContactAddress(request.getParameter("contactAddress"));
        user.setMajor(request.getParameter("major"));
        user.setNotes(request.getParameter("notes"));
        user.setStatus(request.getParameter("status"));
        user.setTel(request.getParameter("tel"));
        user.setEducation(request.getParameter("education"));
        String[] titles = request.getParameterValues("titles[]");
        user.setTitle(Utils.checkboxToString(titles));
        String[] funds = request.getParameterValues("funds[]");
        user.setFund(Utils.checkboxToString(funds));
        teacher.setUser(user);
        teacherService.update(teacher);
        Teacher user2 = teacherService.findByEmail(teacher.getUser().getEmail());
        Utils.removeSession(request, GlobalName.session_teacher);
        HttpSession session = request.getSession();
        user.setPsw("");
        session.setAttribute(GlobalName.session_teacher, user2);
        model.addAttribute("user", user2);
        return "/teacher/data/basicInfo";
    }

    //主持的项目
    private Project getProject(HttpServletRequest request){
        Project project = new Project();
        project.setName(request.getParameter("name"));
        project.setFunds(Double.parseDouble(request.getParameter("funds")));
        project.setRole(request.getParameter("role"));
        project.setNotes(request.getParameter("notes"));
        project.setUid(Utils.getTeacher(request).getUid());
        return project;
    }

    @RequestMapping("/projects")
    public String myProjects(Model model, HttpServletRequest request){
        int id = Utils.getTeacher(request).getUid();
        List<Project> list = userService.showProjects(id);
        list.removeIf(project -> !project.getFlag().equals(GlobalName.teacher_flag_normal));
        model.addAttribute("projects", list);
        model.addAttribute("user", Utils.getTeacher(request));
        return "/teacher/data/projects";
    }
    //添加
    @RequestMapping("/addProject")
    @ResponseBody
    public String addProject(HttpServletRequest request){
        Project project = getProject(request);
        Utils.checkProject(project);
        project.setFlag(GlobalName.teacher_flag_apply);
        project.setBelong(GlobalName.belongSchool);
//        project.setChecked(false);
        String msg = "";
        if(project.getCharge() != null && !Utils.getTeacher(request).getUser().getName().equals(project.getCharge())){
            msg =  " 负责人与用户不匹配，请确认归属";
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
        if(project.getCharge() != null && !Utils.getTeacher(request).getUser().getName().equals(project.getCharge())){
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
            if(!userService.deleteProject(Integer.parseInt(s), Utils.getTeacher(request).getUid())){
                return GlobalName.fail;
            }
        }
        return GlobalName.success;
    }

    //期刊论文
    @RequestMapping("/articles")
    public String myArticle(Model model, HttpServletRequest request){
        Teacher teacher = Utils.getTeacher(request);
        List<UserArticle> list = userService.showArticles(teacher.getUid());
        list.removeIf(article -> !article.getFlag().equals(GlobalName.teacher_flag_normal));
        model.addAttribute("myArticles", list);
        model.addAttribute("user", teacher);
        return "/teacher/data/articles";
    }

    private static int ops = 0;
    private static synchronized void countOps(){
        ops++;
    }
    @RequestMapping("/addArticle")
    @ResponseBody
    public String addArticles(Model model, HttpServletRequest request){
        Teacher teacher = Utils.getTeacher(request);
        String notes = request.getParameter("notes");
        String role = request.getParameter("role");
        String title = request.getParameter("title");
        Article a = new Article();
        a.setName(title);
        a.setId(-1);
        Article article = spiderService.searchPaper(a);
        article.setAddress(GlobalName.belongSchool);
        UserArticle userArticle = new UserArticle();
        userArticle.setUid(teacher.getUid());
        userArticle.setNotes(notes);
        userArticle.setRole(role);
        userArticle.setFlag(GlobalName.teacher_flag_apply);
        if(userService.addUserArticle(article, userArticle) > 0){
            return GlobalName.success;
        }
        return GlobalName.fail;
    }

    //手动录入 管理员检查 手动就不必了
//    @RequestMapping("/addArticleByHand")
//    @ResponseBody
    public String addArticleWithHand(Model model, HttpServletRequest request){
        Teacher teacher = Utils.getTeacher(request);
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
        userArticle.setUid(teacher.getUid());
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
        Teacher teacher = Utils.getTeacher(request);
        if(str == null ||str.length == 0)
            return GlobalName.fail;
        for(String s: str){
            System.out.println(s);
            if(!userService.deleteUserArticle(Integer.parseInt(s), teacher.getUid())){
                return GlobalName.fail;
            }
        }
        return GlobalName.success;
    }
    //todo 手动添加/修改
    @RequestMapping("/updateArticle")
    @ResponseBody
    public String updateArticles(Model model, HttpServletRequest request){
        Teacher teacher = Utils.getTeacher(request);
        String notes = request.getParameter("notes");
        String role = request.getParameter("role");
        String title = request.getParameter("title");
        int id = Integer.parseInt(request.getParameter("id"));
        String journal = request.getParameter("journal");
        Article a = new Article();
        a.setName(title);
        a.setId(id);
        a.setJournalIssn(journal);
        System.out.println(teacher.getUid()+" "+title);
        Article article = spiderService.searchPaper(a);
        System.out.println(article.getJid()+ " "+article.getId());
        if(!article.getAddWay().equals(GlobalName.addWay_System)){
            article.setJournalIssn(journal);
            Journal journal1 = spiderService.getJournal(journal);
            if(journal != null) {
                article.setJournal(journal1);
                article.setJid(journal1.getId());
            }
        }

        if(article.getId() == null){
            //论文名也能拿到id
            article.setId(id);
        }
        UserArticle userArticle = new UserArticle();
        userArticle.setUid(teacher.getUid());
        userArticle.setNotes(notes);
        userArticle.setRole(role);
        userArticle.setAid(article.getId());

        if(userService.updateUserArticle(article, userArticle) > 0){
            return GlobalName.success;
        }
        return GlobalName.fail;
    }

    //专利
    private Patent getPatent(HttpServletRequest request){
        Teacher teacher = Utils.getTeacher(request);
        Patent patent = new Patent();
        patent.setName(request.getParameter("name"));
        patent.setUid(teacher.getUid());
        patent.setCategory(request.getParameter("category"));
        patent.setNotes(request.getParameter("notes"));
        patent.setRole(request.getParameter("role"));
        patent.setBelong(GlobalName.belongSchool);
        return patent;
    }
    @RequestMapping("/patents")
    public String myPatents(Model model, HttpServletRequest request){
        Teacher teacher = Utils.getTeacher(request);
        List<Patent> list = userService.showPatents(teacher.getUid());
        model.addAttribute("patents", list);
        model.addAttribute("user", teacher);
        return "/teacher/data/patents";
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
        Teacher teacher = Utils.getTeacher(request);
        if(str == null ||str.length == 0)
            return GlobalName.fail;
        for(String s: str){
            System.out.println(s);
            if(!userService.deletePatent(Integer.parseInt(s), teacher.getUid())){
                return GlobalName.fail;
            }
        }
        return GlobalName.success;
    }

    //会议论文
    private ConferencePaper getConferencePaper(HttpServletRequest request){
        Teacher teacher = Utils.getTeacher(request);
        ConferencePaper paper = new ConferencePaper();
        paper.setName(request.getParameter("name"));
        paper.setConference(request.getParameter("conference"));
        paper.setCitation(Integer.parseInt(request.getParameter("citation")));
        paper.setNotes(request.getParameter("notes"));
        paper.setRole(request.getParameter("role"));
        paper.setSection(request.getParameter("section"));
        paper.setEsi(request.getParameter("isEsi").equalsIgnoreCase("true"));
        paper.setUid(teacher.getUid());
        paper.setBelong(GlobalName.belongSchool);
        return paper;
    }

    @RequestMapping("/papers")
    public String myConferencePapers(Model model, HttpServletRequest request){
        Teacher teacher = Utils.getTeacher(request);
        List<ConferencePaper> list = userService.showConferencePapers(teacher.getUid());
        model.addAttribute("papers", list);
        model.addAttribute("user", teacher);
        return "/teacher/data/papers";
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
        patent.setId(Integer.parseInt(request.getParameter("id")));
        if(userService.updateConferencePaper(patent) > 0)
            return GlobalName.success;
        return GlobalName.fail;
    }

    @RequestMapping("/deleteConferencePaper")
    @ResponseBody
    public String deleteConferencePaper(HttpServletRequest request){
        String[] str = request.getParameterValues("id[]");
        Teacher teacher = Utils.getTeacher(request);
        if(str == null ||str.length == 0)
            return GlobalName.fail;
        for(String s: str){
            System.out.println(s);
            if(!userService.deleteConferencePaper(Integer.parseInt(s), teacher.getUid())){
                return GlobalName.fail;
            }
        }
        return GlobalName.success;
    }

    @RequestMapping("/analysis")
    public String analysisTeacher(HttpServletRequest request, Model model){
        return "/teacher/data/analysis";
    }

    @RequestMapping("/articles_check")
    public String articlesCheck(HttpServletRequest request, Model model){
        Teacher teacher = Utils.getTeacher(request);
        List<UserArticle> list = userService.showArticles(teacher.getUid());
        list.removeIf(article -> article.getFlag().equals(GlobalName.teacher_flag_normal) || article.getFlag().equals(GlobalName.teacher_flag_other));
        model.addAttribute("myArticles", list);
        model.addAttribute("user", teacher);
        return "/teacher/data/articles_check";
    }

    @RequestMapping("/projects_check")
    public String projectsCheck(Model model, HttpServletRequest request){
        Teacher teacher = Utils.getTeacher(request);
        List<Project> list = userService.showProjects(teacher.getUid());
        list.removeIf(project -> project.getFlag().equals(GlobalName.teacher_flag_normal)  || project.getFlag().equals(GlobalName.teacher_flag_other));
        model.addAttribute("projects", list);
        model.addAttribute("user", Utils.getTeacher(request));
        return "/teacher/data/projects_check";
    }

    @RequestMapping("/claimArticle")
    @ResponseBody
    public String claimArticle(HttpServletRequest request){
        int uAid = Integer.parseInt(request.getParameter("uAid"));
        String action = request.getParameter("action");
        boolean claim = false;
        if(action.equals("accept")){
            claim = true;
        }
        return teacherService.claimArticle(uAid, claim);
    }

    @RequestMapping("/claimProject")
    @ResponseBody
    public String claimProject(HttpServletRequest request){
        int pid = Integer.parseInt(request.getParameter("pid"));
        String action = request.getParameter("action");
        boolean claim = false;
        if(action.equals("accept")){
            claim = true;
        }
        return teacherService.claimProject(pid, claim);
    }
}