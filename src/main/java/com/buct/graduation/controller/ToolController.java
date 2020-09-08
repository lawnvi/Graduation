package com.buct.graduation.controller;

import com.buct.graduation.model.pojo.Article;
import com.buct.graduation.model.pojo.Journal;
import com.buct.graduation.model.pojo.User;
import com.buct.graduation.model.spider.Periodical;
import com.buct.graduation.model.spider.PeriodicalTable;
import com.buct.graduation.service.ArticleService;
import com.buct.graduation.service.SpiderService;
import com.buct.graduation.service.JournalService;
import com.buct.graduation.util.GlobalName;
import com.buct.graduation.util.Utils;
import com.buct.graduation.util.excel.Excel2Excel;
import com.buct.graduation.util.spider.GetArticlesByAddress;
import com.buct.graduation.util.spider.SpiderConfig;
import com.buct.graduation.util.spider.SpiderLetpubJournal;
import com.buct.graduation.util.spider.SpiderWOS;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tools")
public class ToolController {
    @Autowired
    private JournalService journalService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private SpiderService spiderService;

    private User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(GlobalName.session_user);
        if (user == null) {
            user = new User();
            user.setName("登录");
            user.setPicPath(GlobalName.PIC_PATH);
        }
        return user;
    }

    @RequestMapping("/visitor")
    public String search(Model model, HttpServletRequest request) {
//        model.addAttribute("isNull", "none");
//        model.addAttribute("journal", new Journal());
        model.addAttribute("user", getUser(request));
        return "/tool/tool_visit";
    }

    /**
     * 查询期刊数据
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/searchJournal.do")
    @ResponseBody
    public List<Journal> searchJournal(Model model, HttpServletRequest request) {
        String name = request.getParameter("title");
        Journal dbJ = journalService.findJournalByName(name);
        List<Journal> journals = new ArrayList<>();
        if(dbJ != null){
            journals.add(dbJ);
            return journals;
        }
        SpiderLetpubJournal letpub = new SpiderLetpubJournal();
        PeriodicalTable table = letpub.getPeriodicals(name);
        if(table == null){
            return null;
        }
        Journal journalMatch = new Journal();
        for (Periodical periodical : table.getList()) {
            Journal journal = new Journal();
            journal.setName(periodical.getTitle());
            journal.setISSN(periodical.getISSN());
            journal.setAbbrTitle(periodical.getAbbrTitle());
            journal.setUrl(periodical.getUrl());
            if (periodical.isMatch()) {
                journal = letpub.getJournalData(periodical.getUrl());
//                continue;
            }
            journals.add(journal);
        }
//        model.addAttribute("name", name);
//        model.addAttribute("journal", journalMatch);
//        model.addAttribute("list", journals);
        return journals;
    }

    /**
     * 查询期刊论文数据
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/searchArticle.do")
    @ResponseBody
    public Article searchArticle(Model model, HttpServletRequest request) {
        String name = request.getParameter("title");
        Article dbA = articleService.findArticleByName(name);
        if(dbA != null){
            dbA.setJournal(spiderService.getJournal(dbA.getJournalIssn()));
            return dbA;
        }
        SpiderWOS wos = new SpiderWOS();
        if("".equals(SpiderConfig.getSID())){
            return null;
        }
        //todo 状态
        Article article = wos.getArticleByTitle(name);
        if (article == null) {
            System.out.println("back");
            return null;
        }
        if (!article.getName().startsWith("没有找到")) {
            article.setJournal(new SpiderLetpubJournal().getJournal(article.getJournalIssn()));
        }
//        model.addAttribute("journal", article.getJournal());
//        model.addAttribute("isNull", "inline");
        return article;
    }

    //todo 下载文件

    @RequestMapping("/admin")
    public String getReporter(HttpServletRequest request, Model model){
        model.addAttribute("user", getUser(request));
        return "/tool/tool_admin";
    }

    @RequestMapping("/getReporter.do")
    @ResponseBody
    public String getReporterMethod(HttpServletRequest request, HttpServletResponse response, @RequestParam("excel") MultipartFile file){
        String way = request.getParameter("data_status");
        System.out.println(way);
        if (file.isEmpty()) {
            return "请选择文件";
        }
        if(way == null || way.equals("")){
            return "选择方式";
        }
        String fileName = file.getOriginalFilename();
        String path = "D:\\schoolHelper\\upload\\" + fileName;
        path = GlobalName.EXCEL_PATH+GlobalName.EXCEL_BUFFER + Utils.getTodayPath();
        path = Utils.saveFile(file, path);
        if(path.equals("")){
            System.out.println("bad");
        }
/*        }
        File dest = new File(path);
        try {
            file.transferTo(dest);*/
        else{
            System.out.println("good");
            try {
                String newPath = null;
                if(way.equals("half")){
                    newPath = Excel2Excel.getReporter(path);
                }else if(way.equals("all")){
                    newPath = Excel2Excel.getReporterNoData(path);
                }
                else {
                    return "error";
                }
                System.out.println(newPath);
                InputStream is = new FileInputStream(new File(newPath));
                Workbook wb = WorkbookFactory.create(is);
                is.close();
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=reporter"+fileName);
                OutputStream outputStream = response.getOutputStream();
                wb.write(outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "上传成功";
        }
//        catch (IOException e) {
//        }
        System.out.println("bad");
        //reporter
        return "";
    }

    @RequestMapping("/awsl")
    public String getArticlesByAddress(HttpServletRequest request, Model model){
        model.addAttribute("user", getUser(request));
        return "/tool/tool_admin_school";
    }

    @RequestMapping("/getArticlesByAddress.do")
    @ResponseBody
    public void getArticlesByAddressMethod(Model model, HttpServletRequest request, HttpServletResponse response){
        int year = Integer.parseInt(request.getParameter("year"));
        String keyword = request.getParameter("keyword");
        //todo init
        List<Article> list = new ArrayList<>();
        GetArticlesByAddress op = new GetArticlesByAddress();
        try {
            list.addAll(op.getArticles(keyword, year));
            for(Article a: list){
                a.setJournal(spiderService.getJournal(a.getJournalIssn()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("list", list);
        Workbook wb = null;
        try {
            wb = GetArticlesByAddress.writeExcel("",year+"年", ".xls", GetArticlesByAddress.title, list);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename="+year+".xls");
            OutputStream ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return "/admin/showArticle";
    }

    @RequestMapping("/awsl2")
    @ResponseBody
    public String refreshJournal(HttpServletRequest request){
        List<Journal> journals = journalService.getAllOldJournals("2020-09-07");
        SpiderLetpubJournal letpub = new SpiderLetpubJournal();
        int op = 0;
        for(Journal j: journals){
            if("not-JCR".equals(j.getSection())){
                continue;
            }
            Journal jn = letpub.getJournal(j.getISSN());
            if(jn != null && !"404".equals(jn.getName())) {
                jn.setId(j.getId());
                jn.setUpdateDate(Utils.getDate().toDate());
                journalService.updateJournal(jn);
                op++;
                System.out.println("find journal "+ journals.indexOf(j) +" success:"+j.getISSN());
            }else {
                System.out.println("find journal "+ journals.indexOf(j) +" error:"+j.getISSN());
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "update success "+op;
    }
}
