package com.buct.graduation.service.impl;

import com.buct.graduation.mapper.*;
import com.buct.graduation.model.pojo.*;
import com.buct.graduation.model.pojo.recruit.Resume;
import com.buct.graduation.service.UserService;
import com.buct.graduation.util.GlobalName;
import com.buct.graduation.util.Utils;
import com.buct.graduation.util.spider.SpiderWOS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.rmi.CORBA.Util;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StationMapper stationMapper;
    @Autowired
    private ResumeMapper resumeMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private PatentMapper patentMapper;
    @Autowired
    private ConferencePaperMapper conferencePaperMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserArticleMapper userArticleMapper;
    @Autowired
    private JournalMapper journalMapper;
    @Autowired
    private ReporterMapper reporterMapper;

    @Override
    public int register(User user) {
        user.setLevel(GlobalName.newGay);
        return userMapper.addUser(user);
    }

    @Override
    public User login(String email, String psw) {
        User user = userMapper.findUserByEmail(email);
        if(user == null)
            return null;
        if(psw.equals(user.getPsw()))
            return user;
        return null;
    }

    @Override
    public User findUserById(int id) {
        return userMapper.findUserById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userMapper.findUserByEmail(email);
    }

    @Override
    public List<User> findUsersByStatus(String status) {
        return userMapper.findUserByStatus(status);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    @Transactional//事务？
    public int postResume(int uid, int sid) {
        User user = userMapper.findUserById(uid);
        //todo 评价获得reporter id
        List<ConferencePaper> papers = conferencePaperMapper.findByUid(uid);
        List<Project> projects = projectMapper.findByUid(uid);
        List<Patent> patents = patentMapper.findByUid(uid);
        List<Article> articles = articleMapper.findByIds(uid);
        for(Article article: articles){
            article.setJournal(journalMapper.findById(article.getJid()));
        }
        Reporter reporter = Utils.getScore(projects, patents, articles, papers);
        int rid = reporterMapper.insert(reporter);
System.out.println("rid"+rid);
        Resume resume = new Resume();
        resume.setUid(uid);
        resume.setSid(sid);
        resume.setRid(rid);
        resume.setResumePath(user.getResumePath());
        resume.setStatus("new");
        return resumeMapper.addResume(resume);
    }

    @Override
    public List<Project> showProjects(int uid) {
        return projectMapper.findByUid(uid);
    }

    @Override
    public List<ConferencePaper> showConferencePapers(int uid) {
        return conferencePaperMapper.findByUid(uid);
    }

    @Override
    public List<Patent> showPatents(int uid) {
        return patentMapper.findByUid(uid);
    }

    @Override
    public List<Article> showArticles(int uid) {
        List<Article> articles = articleMapper.findByIds(uid);
        for(Article article: articles){
            article.setJournal(journalMapper.findById(article.getJid()));
        }
        return articles;
    }

    @Override
    public int updateProject(Project project) {
        return projectMapper.update(project);
    }

    @Override
    public int addProject(Project project) {
        return projectMapper.addProject(project);
    }

    @Override
    public boolean deleteProject(int id, int uid) {
        if(projectMapper.findById(id).getUid() != uid)
            return false;
        projectMapper.delete(id);
        return true;
    }

    @Override
    public int updateConferencePaper(ConferencePaper conferencePaper) {
        return conferencePaperMapper.update(conferencePaper);
    }

    @Override
    public int addConferencePaper(ConferencePaper conferencePaper) {
        SpiderWOS wos = new SpiderWOS();
        Article article = wos.getESIandtimes(conferencePaper.getName());
        conferencePaper.setCitation(article.getCitation());
        conferencePaper.setEsi(article.getESI());
        return conferencePaperMapper.addPaper(conferencePaper);
    }

    @Override
    public boolean deleteConferencePaper(int id, int uid) {
        if(conferencePaperMapper.findById(id).getUid() != uid)
            return false;
        conferencePaperMapper.delete(id);
        return true;
    }

    @Override
    public int updatePatent(Patent patent) {
        return patentMapper.update(patent);
    }

    @Override
    public int addPatent(Patent patent) {
        return patentMapper.add(patent);
    }

    @Override
    public boolean deletePatent(int id, int uid) {
        if(patentMapper.findById(id).getUid() != uid)
            return false;
        patentMapper.delete(id);
        return true;
    }

    @Override
    public int updateUserArticle(Article article, UserArticle userArticle) {
        if(userArticleMapper.update(userArticle) == 0)
            return 0;
        return articleMapper.update(article);
    }

    @Override
    @Transactional
    public int addUserArticle(Article article, UserArticle userArticle) {
        Article a = articleMapper.findByName(article.getName());
        if(a == null) {
            article.setUploadEmail(userMapper.findUserById(userArticle.getUid()).getEmail());
            articleMapper.insertArticle(article);
        }
        article = articleMapper.findByName(article.getName());
        userArticle.setAid(article.getId());
        return userArticleMapper.add(userArticle);
    }

    @Override
    public boolean deleteUserArticle(int aid, int uid) {
        if(userArticleMapper.findById(aid, uid) == null)
            return false;
        userArticleMapper.delete(uid, aid);
        return true;
    }
}
