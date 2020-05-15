package com.buct.graduation.controller;

import com.buct.graduation.model.pojo.*;
import com.buct.graduation.model.pojo.recruit.Resume;
import com.buct.graduation.model.spider.ProjectData;
import com.buct.graduation.model.vo.UserVData;
import com.buct.graduation.service.*;
import com.buct.graduation.util.GlobalName;
import com.buct.graduation.util.Utils;
import com.buct.graduation.util.spider.SpiderLetpubJournal;
import com.buct.graduation.util.spider.SpiderLetpubProject;
import com.buct.graduation.util.spider.SpiderWOS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/adminUser/research/")
public class ScienceController {
    @Autowired
    private ScienceService scienceService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private JournalService journalService;
    @Autowired
    private SpiderService spiderService;
    @Autowired
    private UserService userService;

    @RequestMapping("/overview")
    public String showData(HttpServletRequest request, Model model){
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("articles", scienceService.getArticles());
        model.addAttribute("papers", scienceService.getConferencePapers());
        model.addAttribute("patents", scienceService.getPatents());
        model.addAttribute("projects", scienceService.getProjects(GlobalName.teacher_flag_normal));
        return "/admin/xsb/science_data";
    }

    private String initRequest(HttpServletRequest request, Model model){
        String flag = request.getParameter("flag");
        if(!GlobalName.teacher_flag_other.equals(flag) && !GlobalName.teacher_flag_apply.equals(flag) && !GlobalName.teacher_flag_claim.equals(flag)){
            flag = GlobalName.teacher_flag_normal;
        }
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("flag", flag);
        return flag;
    }

    @RequestMapping("/article")
    public String showArticles(HttpServletRequest request, Model model){
        String flag = initRequest(request, model);
        model.addAttribute("articles", scienceService.getUserArticles(flag));
        return "/admin/xsb/articles";
    }

    @RequestMapping("/meetingPaper")
    public String showPapers(HttpServletRequest request, Model model){
        String flag = initRequest(request, model);
        model.addAttribute("papers", scienceService.getConferencePapers());
        return "/admin/xsb/papers";
    }

    @RequestMapping("/project")
    public String showProjects(HttpServletRequest request, Model model){
        String flag = initRequest(request, model);
        model.addAttribute("projects", scienceService.getProjects(flag));
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
        model.addAttribute("flag", "");
        model.addAttribute("journals", scienceService.getJournals());
        return "/admin/xsb/journals";
    }

    @RequestMapping("/passArticle")
    @ResponseBody
    public String passArticle(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        boolean pass = "1".equalsIgnoreCase(request.getParameter("msg"));
        String kind = request.getParameter("obj");
        switch (kind) {
            case "article": return scienceService.checkArticle(id, pass);
            case "project": return scienceService.checkProject(id, pass);
            case "paper": return scienceService.checkPaper(id, pass);
            case "patent": return scienceService.checkPatent(id, pass);
            default: return "error";
        }
    }

    @RequestMapping("/actionArticle")
    @ResponseBody
    public String actionArticle(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        //1 更新 0 移除
        boolean pass = "1".equalsIgnoreCase(request.getParameter("msg"));
        String kind = request.getParameter("obj");
        switch (kind) {
            case "article": return scienceService.updateArticleByFlag(id, pass);
            case "project": return scienceService.updateProjectByFlag(id, pass);
            case "paper": return scienceService.updatePaperByFlag(id, pass);
            case "patent": return scienceService.updatePatentByFlag(id, pass);
            default: return "error";
        }
    }

    @RequestMapping("/searchArticle")
    @ResponseBody
    public Article searchArticle(HttpServletRequest request){
        String name = request.getParameter("title");
        if(name == null || "".equals(name)){
            return null;
        }
        String code = Utils.getAdmin(request).getId()+name;
        HttpSession session = request.getSession();
        Article article1 =  (Article)session.getAttribute(code);
        if(article1 != null){
            return article1;
        }
        Article dbA = articleService.findArticleByName(name);
        if(dbA != null){
            dbA.setJournal(spiderService.getJournal(dbA.getJournalIssn()));
            return dbA;
        }
        SpiderWOS wos = new SpiderWOS();
        //todo 状态
        Article article = wos.getArticleByTitle(name);
        if (article == null) {
            System.out.println("back");
            return null;
        }
        if (!article.getName().startsWith("没有找到")) {
            article.setJournal(new SpiderLetpubJournal().getJournal(article.getJournalIssn()));
        }
        session.setAttribute(Utils.getAdmin(request).getId()+article.getName(), article);
            //时限600s
        session.setMaxInactiveInterval(600);
        return article;
    }

    @RequestMapping("/addArticle")
    public String addArticle(Model model, HttpServletRequest request){
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("flag", "");
        model.addAttribute("title", request.getParameter("title"));
        Article article = searchArticle(request);
        if(article == null){
            article = new Article();
        }
        model.addAttribute("article", article);
        return "/admin/xsb/add_article";
    }

    @RequestMapping("/addArticle.do")
    @ResponseBody
    public String addArticle(HttpServletRequest request){
        String code = Utils.getAdmin(request).getId()+request.getParameter("code");
        HttpSession session = request.getSession();
        Article article =  (Article)session.getAttribute(code);
        session.removeAttribute(code);
        return scienceService.insertArticle(article);
    }

    @RequestMapping("/articleData")
    public String articleData(Model model, HttpServletRequest request){
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("flag", "");
        return "/admin/xsb/articles_data";
    }

    @RequestMapping("/searchProject")
    @ResponseBody
    public Project searchProject(HttpServletRequest request){
        String name = request.getParameter("title");
        String way = request.getParameter("way");
        if(name == null || "".equals(name) || way == null){
            return null;
        }
        String code = Utils.getAdmin(request).getId()+name;
        HttpSession session = request.getSession();
        Project project = (Project) session.getAttribute(code);
        if(project != null){
            return project;
        }
        SpiderLetpubProject spider = new SpiderLetpubProject();
        ProjectData p;
        if("code".equals(way)){
            spider.setCode(name);
            p = spider.searchByCode();
        }else {
            spider.setProjectName(name);
            p = spider.searchByName();
        }

        if (p == null) {
            System.out.println("back");
            return null;
        }
        project = p.toProject();
        session.setAttribute(Utils.getAdmin(request).getId()+project.getName(), project);
        //时限600s
        session.setMaxInactiveInterval(600);
        return project;
    }

    @RequestMapping("/addProject")
    public String addProject(Model model, HttpServletRequest request){
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("flag", "");
        model.addAttribute("title", request.getParameter("title"));
        String way = request.getParameter("way");
        if("code".equals(way)){
            way = "code";
        }
        else {
            way = "name";
        }
        model.addAttribute("way", way);
        Project project = searchProject(request);
        if(project == null){
            project = new Project();
        }
        model.addAttribute("project", project);
        return "/admin/xsb/add_project";
    }

    @RequestMapping("/addProject.do")
    @ResponseBody
    public String addProjectMethod(Model model, HttpServletRequest request){
        String code = Utils.getAdmin(request).getId()+request.getParameter("code");
        HttpSession session = request.getSession();
        Project project = (Project) session.getAttribute(code);
        session.removeAttribute(code);
        project.setRole(GlobalName.project_leader);
        project.setFlag(GlobalName.teacher_flag_claim);
        project.setBelong(GlobalName.belongSchool);
        return userService.addProject(project);
    }

    @RequestMapping("/addPatent")
    public String addPatent(Model model, HttpServletRequest request){
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("flag", "");
        model.addAttribute("title", "");
        return "/admin/xsb/add_patent";
    }

    private Patent getPatent(HttpServletRequest request){
        Patent patent = new Patent();
        String title = request.getParameter("title");
        String kind = request.getParameter("kind");
        String role = request.getParameter("role");
        String notes = request.getParameter("notes");
        patent.setBelong(GlobalName.belongSchool);
        patent.setFlag(GlobalName.teacher_flag_normal);
        patent.setCategory(kind);
        patent.setNotes(notes);
        patent.setRole(role);
        patent.setName(title);
        return patent;
    }

    @RequestMapping("/addPatent.do")
    @ResponseBody
    public String addPatentMethod(Model model, HttpServletRequest request){
        Patent patent = getPatent(request);
        String email = request.getParameter("email");
        User user = userService.findUserByEmail(email);
        if(user == null){
            return "邮箱不存在";
        }
        patent.setUid(user.getId());
        return userService.addPatent(patent) > 0 ? "保存成功": "保存失败";
    }

    @RequestMapping("/updatePatent.do")
    @ResponseBody
    public String updatePatentMethod(Model model, HttpServletRequest request){
        Patent patent = getPatent(request);
        int id = Integer.parseInt(request.getParameter("id"));
        int uid = Integer.parseInt(request.getParameter("uid"));
        patent.setId(id);
        patent.setUid(uid);
        return userService.updatePatent(patent) > 0 ? "保存成功": "保存失败";
    }

    @RequestMapping("/addPaper")
    public String addPaper(Model model, HttpServletRequest request){
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("flag", "");
        model.addAttribute("title", "");
        String s = request.getParameter("id");
        int id = -1;
        if(s != null && !s.equals("")){
            id = Integer.parseInt(s);
        }
        ConferencePaper paper = id > 0 ? userService.findPaperById(id) : new ConferencePaper();
        model.addAttribute("paper", paper);
        return "/admin/xsb/add_paper";
    }

    private ConferencePaper getPaper(HttpServletRequest request){
        ConferencePaper paper= new ConferencePaper();
        String title = request.getParameter("title");
        String section = request.getParameter("section");
        String role = request.getParameter("role");
        String meeting = request.getParameter("meeting");
        String notes = request.getParameter("notes");
        boolean esi = request.getParameter("esi").equalsIgnoreCase("esi");
        int n = Integer.parseInt(request.getParameter("citation"));
        paper.setBelong(GlobalName.belongSchool);
        paper.setFlag(GlobalName.teacher_flag_normal);
        paper.setSection(section);
        paper.setNotes(notes);
        paper.setRole(role);
        paper.setName(title);
        paper.setConference(meeting);
        paper.setEsi(esi);
        paper.setCitation(n);
        return paper;
    }

    @RequestMapping("/addPaper.do")
    @ResponseBody
    public String addPaperMethod(Model model, HttpServletRequest request){
        String email = request.getParameter("email");
        ConferencePaper paper = getPaper(request);
        User user = userService.findUserByEmail(email);
        if(user == null || !user.getLevel().equals(GlobalName.user_type_teacher)){
            return "邮箱还未注册到学院系统";
        }

        System.out.println("role:"+paper.getRole());
        paper.setUid(user.getId());
        return userService.addConferencePaper(paper) > 0 ? "保存成功": "保存失败";
    }

    @RequestMapping("/updatePaper.do")
    @ResponseBody
    public String updatePaperMethod(Model model, HttpServletRequest request){
        int uid = Integer.parseInt(request.getParameter("uid"));
        int id = Integer.parseInt(request.getParameter("id"));
        ConferencePaper paper = getPaper(request);
        paper.setId(id);
        paper.setUid(uid);
        return userService.updateConferencePaper(paper) > 0 ? "保存成功": "保存失败";
    }

    @RequestMapping("/addJournal")
    public String addJournal(HttpServletRequest request, Model model){
        String title = request.getParameter("title");
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("title", title);
        Journal journal;
        if(title == null || title.equals("")){
            journal = new Journal();
        }else {
            journal = journalService.findJournalByName(title);
            if(journal == null) {
                journal = spiderService.getJournal(title);
            }
        }
        if(journal == null){
            journal = new Journal();
        }else {
            HttpSession session = request.getSession();
            session.setAttribute(Utils.getAdmin(request).getId()+journal.getName(), journal);
            //时限600s
            session.setMaxInactiveInterval(600);
        }
        model.addAttribute("journal", journal);
        return "/admin/xsb/add_journal";
    }

    @RequestMapping("/addJournal.do")
    @ResponseBody
    public String addJournal(HttpServletRequest request){
        String code = Utils.getAdmin(request).getId()+request.getParameter("code");
        HttpSession session = request.getSession();
        Journal journal =  (Journal)session.getAttribute(code);
        session.removeAttribute(code);
        return scienceService.insertJournal(journal);
    }

    @RequestMapping("/researcher")
    public String showTeachers(HttpServletRequest request, Model model){
        List<UserVData> list = userService.findUserByLevel(GlobalName.user_type_teacher);
        model.addAttribute("list", list);
        model.addAttribute("user", Utils.getAdmin(request));
        return "/admin/xsb/teachers";
    }

    @RequestMapping("/userDetail")
    public String resumeDetail(HttpServletRequest request, Model model){
        int uid = Integer.parseInt(request.getParameter("id"));
        User user = userService.findUserById(uid);
        model.addAttribute("researcher", user);
        model.addAttribute("user", Utils.getAdmin(request));
        return "/admin/xsb/researcherData/researcher_detail";
    }

    @RequestMapping("/user_article")
    public String resumeArticle(HttpServletRequest request, Model model){
        int uid = Integer.parseInt(request.getParameter("id"));
        User user = userService.findUserById(uid);
        model.addAttribute("researcher", user);
        List<Article> articles = articleService.findByUid(uid);
        model.addAttribute("articles", articles);
        model.addAttribute("uid", uid);
        model.addAttribute("user", Utils.getAdmin(request));
        return "/admin/xsb/researcherData/article";
    }

    @RequestMapping("/user_article_wait")
    public String resumeArticleWait(HttpServletRequest request, Model model){
        int uid = Integer.parseInt(request.getParameter("id"));
        User user = userService.findUserById(uid);
        model.addAttribute("researcher", user);
        List<Article> articles = articleService.findByUidStatus(uid, GlobalName.addWay_missing_c);
        model.addAttribute("articles", articles);
        model.addAttribute("uid", uid);
        model.addAttribute("user", Utils.getAdmin(request));
        return "/admin/xsb/researcherData/article_wait";
    }

    @RequestMapping("/user_meeting")
    public String resumePaper(HttpServletRequest request, Model model){
        int uid = Integer.parseInt(request.getParameter("id"));
        User user = userService.findUserById(uid);
        model.addAttribute("researcher", user);
        List<ConferencePaper> papers = userService.showConferencePapers(uid);
        model.addAttribute("papers", papers);
        model.addAttribute("uid", uid);
        model.addAttribute("user", Utils.getAdmin(request));
        return "/admin/xsb/researcherData/paper";
    }

    @RequestMapping("/user_project")
    public String resumeProject(HttpServletRequest request, Model model){
        int uid = Integer.parseInt(request.getParameter("id"));
        User user = userService.findUserById(uid);
        model.addAttribute("researcher", user);
        List<Project> projects = userService.showProjects(uid);
        model.addAttribute("projects", projects);
        model.addAttribute("uid", uid);
        model.addAttribute("user", Utils.getAdmin(request));
        return "/admin/xsb/researcherData/project";
    }

    @RequestMapping("/user_project_wait")
    public String resumeProjectWait(HttpServletRequest request, Model model){
        int uid = Integer.parseInt(request.getParameter("id"));
        User user = userService.findUserById(uid);
        model.addAttribute("researcher", user);
        List<Project> projects = userService.showProjectsByStatus(uid, false);
        model.addAttribute("projects", projects);
        model.addAttribute("uid", uid);
        model.addAttribute("user", Utils.getAdmin(request));
        return "/admin/xsb/researcherData/project_wait";
    }

    @RequestMapping("/user_patent")
    public String resumePatent(HttpServletRequest request, Model model){
        int uid = Integer.parseInt(request.getParameter("id"));
        User user = userService.findUserById(uid);
        model.addAttribute("researcher", user);
        List<Patent> patents = userService.showPatents(uid);
        model.addAttribute("patents", patents);
        model.addAttribute("uid", uid);
        model.addAttribute("user", Utils.getAdmin(request));
        return "/admin/xsb/researcherData/patent";
    }

}