package com.buct.graduation.service;

import com.buct.graduation.model.pojo.*;

import java.util.List;

public interface ScienceService {
    List<Article> getArticles();

    List<UserArticle> getUserArticles(String flag);

    List<ConferencePaper> getConferencePapers();

    List<Project> getProjects(String flag);

    List<Patent> getPatents();

    List<Journal> getJournals();

    String checkArticle(int aid, boolean pass);

    String checkProject(int pid, boolean pass);

    String checkPaper(int pid, boolean pass);

    String checkPatent(int pid, boolean pass);

    String updateArticleByFlag(int aid, boolean action);

    String updateProjectByFlag(int pid, boolean action);

    String updatePaperByFlag(int pid, boolean action);

    String updatePatentByFlag(int pid, boolean action);

    String insertArticle(Article article);
}
