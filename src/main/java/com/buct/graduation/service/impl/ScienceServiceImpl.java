package com.buct.graduation.service.impl;

import com.buct.graduation.mapper.*;
import com.buct.graduation.model.pojo.*;
import com.buct.graduation.model.pojo.science.Teacher;
import com.buct.graduation.model.vo.AnalysisScience;
import com.buct.graduation.service.ScienceService;
import com.buct.graduation.util.GlobalName;
import com.buct.graduation.util.Utils;
import com.buct.graduation.util.excel.Excel2Excel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScienceServiceImpl implements ScienceService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserArticleMapper userArticleMapper;
    @Autowired
    private ConferencePaperMapper conferencePaperMapper;
    @Autowired
    private PatentMapper patentMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private JournalMapper journalMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public List<Article> getArticles() {
        return articleMapper.findByAddress(GlobalName.belongSchool);
    }

    @Override
    public List<UserArticle> getUserArticles(String flag) {
        return userArticleMapper.findByFlag(flag);
    }

    @Override
    public List<ConferencePaper> getConferencePapers() {
        return conferencePaperMapper.findByBelong(GlobalName.belongSchool);
    }

    @Override
    public List<Project> getProjects(String flag) {
        return projectMapper.findByBelongFlag(GlobalName.belongSchool, flag);
    }

    @Override
    public List<Patent> getPatents() {
        return patentMapper.findByBelong(GlobalName.belongSchool);
    }

    @Override
    public List<Journal> getJournals() {
        return journalMapper.findAll();
    }

    @Override
    public String checkArticle(int aid, boolean pass) {
        UserArticle article = userArticleMapper.findByOwnId(aid);
        String temp = pass ? GlobalName.teacher_flag_normal : GlobalName.teacher_flag_other;
        article.setFlag(temp);
        String msg = pass ? "已通过": "已拒绝";
        if(userArticleMapper.update(article) > 0){
            return msg;
        }
        return "未知错误";
    }

    @Override
    public String checkProject(int pid, boolean pass) {
        Project project = projectMapper.findById(pid);
        String temp = pass ? GlobalName.teacher_flag_normal : GlobalName.teacher_flag_other;
        project.setFlag(temp);
        if(projectMapper.update(project) > 0){
            return pass ? "已通过": "已拒绝";
        }
        return "未知错误";
    }

    @Override
    public String checkPaper(int pid, boolean pass) {
        ConferencePaper paper = conferencePaperMapper.findById(pid);
        String temp = pass ? GlobalName.teacher_flag_normal : GlobalName.teacher_flag_other;
        paper.setFlag(temp);
        if(conferencePaperMapper.update(paper) > 0){
            return pass ? "已通过": "已拒绝";
        }
        return "未知错误";
    }

    @Override
    public String checkPatent(int pid, boolean pass) {
        Patent patent = patentMapper.findById(pid);
        String temp = pass ? GlobalName.teacher_flag_normal : GlobalName.teacher_flag_other;
        patent.setFlag(temp);
        if(patentMapper.update(patent) > 0){
            return pass ? "已通过": "已拒绝";
        }
        return "未知错误";
    }

    @Override
    public String updateArticleByFlag(int aid, boolean action) {
        String msg = "操作失败";
        if(action){
            //todo update article
            msg = "更新成功";
        }else {
            userArticleMapper.deleteById(aid);
            msg = "删除成功";
        }
        return msg;
    }

    @Override
    public String updateProjectByFlag(int pid, boolean action) {
        String msg = "操作失败";
        if(action){
            //todo update article
            msg = "更新成功";
        }else {
            projectMapper.delete(pid);
            msg = "删除成功";
        }
        return msg;
    }

    @Override
    public String updatePaperByFlag(int pid, boolean action) {
        String msg = "操作失败";
        if(action){
            //todo update article
            msg = "更新成功";
        }else {
            conferencePaperMapper.delete(pid);
            msg = "删除成功";
        }
        return msg;
    }

    @Override
    public String updatePatentByFlag(int pid, boolean action) {
        String msg = "操作失败";
        if(action){
            //todo update article
            msg = "更新成功";
        }else {
            patentMapper.delete(pid);
            msg = "删除成功";
        }
        return msg;
    }

    @Override
    @Transactional
    public String insertArticle(Article article) {
        if(articleMapper.findByName(article.getName()) != null){
            Article a = articleMapper.findByName(article.getName());
            article.setJid(a.getJid());
            article.setId(a.getId());
            article.setUploadEmail(a.getUploadEmail());
            article.setAddWay(a.getAddWay());
            articleMapper.update(article);
            return "论文已存在";
        }
        Journal journal = article.getJournal() == null? null :journalMapper.findByKeyword(article.getJournal().getISSN());
        if(journal != null){
            article.setJid(journal.getId());
        }
        else if(article.getJournal() != null){
            journalMapper.addJournal(article.getJournal());
            journal = journalMapper.findByKeyword(article.getJournal().getISSN());
            article.setJid(journal.getId());
        }
        article.setAddress(GlobalName.belongSchool);
        int r1 = articleMapper.insertArticle(article);
        if(article.getCAuthor() != null && !"".equals(article.getCAuthor()) || article.getAuthor() != null && !"".equals(article.getAuthor())) {
            List<Teacher> teachers = teacherMapper.findAll();
            //可能是作者的老师
            teachers.removeIf(teacher -> teacher.getPhonetic() == null || "".equals(teacher.getPhonetic()) || !(article.getAuthor() + article.getCAuthor()).toUpperCase().contains(teacher.getPhonetic().toUpperCase()));
            //添加userArticle
            for (Teacher teacher : teachers) {
                UserArticle userArticle = new UserArticle();
                userArticle.setAid(article.getId());
                userArticle.setUid(teacher.getUid());
                //todo 可根据角标识别，但我没保存
                userArticle.setRole(GlobalName.article_role_contact);
                userArticle.setFlag(GlobalName.teacher_flag_claim);
                userArticleMapper.add(userArticle);
            }
        }
        return r1 > 0 ? "论文保存成功" : "操作失败";
    }

    @Override
    public String insertJournal(Journal journal) {
        if(journalMapper.findByKeyword(journal.getName()) != null){
            return "期刊已存在";
        }
        return journalMapper.addJournal(journal) > 0 ? "论文保存成功" : "操作失败";
    }

    @Override
    public String addProject(Project project) {
        if(projectMapper.findByNumber(project.getNumber()) != null){
            return "此项目已存在";
        }
        if(project.getCharge() != null && !"".equals(project.getCharge())) {
            List<User> users = userMapper.findUserByName(project.getCharge());
            for(User user: users){
                project.setUid(user.getId());
                project.setFlag(GlobalName.teacher_flag_claim);
                projectMapper.addProject(project);
            }
        }
        return "操作成功";
    }

    @Override
    public AnalysisScience analysisUser(int uid) {
        AnalysisScience analysis = new AnalysisScience();
        User user = userMapper.findUserById(uid);

        List<ConferencePaper> papers = conferencePaperMapper.findByUid(uid);
        List<Project> projects = projectMapper.findByUid(uid);
        List<Patent> patents = patentMapper.findByUid(uid);
        List<Article> articles = articleMapper.findByIds(uid);
        List<User> users = new ArrayList<>();
        users.add(user);
        analysis = Utils.analysisScience(users, articles, patents, papers, projects);
        Reporter reporter = new Reporter();
        reporter.setUid(uid);
        reporter.setName(user.getName());
        reporter.setTitle(user.getTitle());
        reporter.setEducation(user.getEducation());
        reporter.setFund(user.getFund());
        Utils.getScore(reporter, projects, patents, articles, papers);

        reporter.setTimestamp(Utils.getDate().toString());
        analysis.setReporter(reporter);
        analysis.setUser(user);
        return analysis;
    }

    @Override
    public AnalysisScience analysisSchool() {
        List<User> users = userMapper.findUserByLevel(GlobalName.user_type_teacher);
        List<ConferencePaper> papers = conferencePaperMapper.findByBelong(GlobalName.belongSchool);
        List<Project> projects = projectMapper.findByBelong(GlobalName.belongSchool);
        List<Patent> patents = patentMapper.findByBelong(GlobalName.belongSchool);
        List<Article> articles = articleMapper.findByAddress(GlobalName.belongSchool);
        return Utils.analysisScience(users, articles, patents, papers, projects);
    }

    @Override
    public String downloadExcelReporter(int uid) {
        User user = userMapper.findUserById(uid);
        List<ConferencePaper> papers = conferencePaperMapper.findByUid(uid);
        List<Project> projects = projectMapper.findByUid(uid);
        List<Patent> patents = patentMapper.findByUid(uid);
        List<Article> articles = articleMapper.findByIds(uid);
        Reporter reporter = new Reporter();
        reporter.setUid(uid);
        reporter.setName(user.getName());
        reporter.setTitle(user.getTitle());
        reporter.setEducation(user.getEducation());
        reporter.setFund(user.getFund());
        Utils.getScore(reporter, projects, patents, articles, papers);
        return Excel2Excel.outputReporter(reporter, articles, papers, projects, patents);
    }

    @Override
    public String updateArticlesByAddress(List<Article> articles) {
        int update = 0, insert = 0;
        for(Article article: articles){
            Article a = articleMapper.findByName(article.getName());
            if(a == null){
                insertArticle(article);
                insert++;
            }else {
                article.setId(a.getId());
                if(a.getJid() != null && a.getJid() != 0) {
                    article.setJid(a.getJid());
                }
                else {
                    journalMapper.addJournal(article.getJournal());
                }
                articleMapper.update(article);
                update++;
            }
        }
        return "本次更新共找到"+articles.size()+"篇期刊论文，其中更新"+update+"篇，"+"新加入"+insert+"篇";
    }

    @Override
    public String handleObj(String obj, boolean accept, int id) {
        int result = 0;
        switch (obj){
            case "project": {
                Project project = projectMapper.findById(id);
                project.setChecked(accept);
                if(accept){
                    project.setFlag(GlobalName.teacher_flag_normal);
                }else {

                    project.setFlag(GlobalName.teacher_flag_other);
                }
                result = projectMapper.update(project);
                break;
            }
            case "article":{
                UserArticle userArticle = userArticleMapper.findByOwnId(id);
                String flag = accept ? GlobalName.teacher_flag_normal : GlobalName.teacher_flag_other;
                userArticle.setFlag(flag);
                result = userArticleMapper.update(userArticle);
                break;
            }
        }
        return result > 0 ? "操作成功": "操作失败";
    }

    @Override
    public List<UserArticle> findByUid(int uid) {
        return userArticleMapper.findByUid(uid);
    }
}
