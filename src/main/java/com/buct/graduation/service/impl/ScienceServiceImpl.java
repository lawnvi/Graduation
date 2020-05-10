package com.buct.graduation.service.impl;

import com.buct.graduation.mapper.*;
import com.buct.graduation.model.pojo.*;
import com.buct.graduation.service.ScienceService;
import com.buct.graduation.util.GlobalName;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String msg = pass ? "已通过": "已拒绝";
        if(projectMapper.update(project) > 0){
            return msg;
        }
        return "未知错误";
    }

    @Override
    public String checkPaper(int pid, boolean pass) {
        ConferencePaper paper = conferencePaperMapper.findById(pid);
        String temp = pass ? GlobalName.teacher_flag_normal : GlobalName.teacher_flag_other;
        paper.setFlag(temp);
        String msg = pass ? "已通过": "已拒绝";
        if(conferencePaperMapper.update(paper) > 0){
            return msg;
        }
        return "未知错误";
    }

    @Override
    public String checkPatent(int pid, boolean pass) {
        Patent patent = patentMapper.findById(pid);
        String temp = pass ? GlobalName.teacher_flag_normal : GlobalName.teacher_flag_other;
        patent.setFlag(temp);
        String msg = pass ? "已通过": "已拒绝";
        if(patentMapper.update(patent) > 0){
            return msg;
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
            return "论文已存在";
        }
        int r1 = articleMapper.insertArticle(article);
        if(journalMapper.findByKeyword(article.getJournal().getISSN()) != null){
            return r1 > 0 ? "论文保存成功，期刊已存在": "保存失败，期刊已存在";
        }
        int r2 = journalMapper.addJournal(article.getJournal());
        return r2 > 0 && r1 > 0 ? "论文保存成功" : "操作失败";
    }
}
