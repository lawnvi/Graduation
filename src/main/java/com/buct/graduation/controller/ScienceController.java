package com.buct.graduation.controller;

import com.buct.graduation.mapper.ProjectMapper;
import com.buct.graduation.model.pojo.Admin;
import com.buct.graduation.model.pojo.Article;
import com.buct.graduation.model.pojo.Project;
import com.buct.graduation.service.ArticleService;
import com.buct.graduation.service.ScienceService;
import com.buct.graduation.service.SpiderService;
import com.buct.graduation.service.UserService;
import com.buct.graduation.util.GlobalName;
import com.buct.graduation.util.Utils;
import com.buct.graduation.util.spider.SpiderLetpub;
import com.buct.graduation.util.spider.SpiderWOS;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/adminUser/research/")
public class ScienceController {
    @Autowired
    private ScienceService scienceService;
    @Autowired
    private ArticleService articleService;
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
            article.setJournal(new SpiderLetpub().getJournal(article.getJournalIssn()));
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
        Project project = new Project();
        project.setName("上帝智障");
        project.setFund("NSFC重点基金");
        project.setFunds(12.0);
        return project;
    }

    @RequestMapping("/addProject")
    public String addProject(Model model, HttpServletRequest request){
        model.addAttribute("user", Utils.getAdmin(request));
        model.addAttribute("flag", "");
        model.addAttribute("title", request.getParameter("title"));
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
        return userService.addProject(project) > 0 ? "'保存成功'": "保存失败";
    }
}
