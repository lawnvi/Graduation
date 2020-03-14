package com.buct.graduation.service;

import com.buct.graduation.model.pojo.*;

import java.util.List;

public interface UserService {
    int register(User user);

    User login(String email, String psw);

    User findUserById(int id);

    User findUserByEmail(String email);

    List<User> findUsersByStatus(String status);

    int updateUser(User user);

    int postResume(int uid, int sid);

    //项目论文等
    List<Project> showProjects(int uid);

    List<ConferencePaper> showConferencePapers(int uid);

    List<Patent> showPatents(int uid);

    List<Article> showArticles(int uid);

    int updateProject(Project project);
    
    int addProject(Project project);
    
    boolean deleteProject(int id, int uid);

    int updateConferencePaper(ConferencePaper conferencePaper);

    int addConferencePaper(ConferencePaper conferencePaper);

    boolean deleteConferencePaper(int id, int uid);

    int updatePatent(Patent patent);

    int addPatent(Patent patent);

    boolean deletePatent(int id, int uid);

    //仅应聘者可改更新 教师的需要申请
    int updateUserArticle(Article article, UserArticle userArticle);

    int addUserArticle(Article article, UserArticle userArticle);

    boolean deleteUserArticle(int aid, int uid);
}