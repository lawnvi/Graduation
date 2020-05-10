package com.buct.graduation.service;

import com.buct.graduation.model.pojo.Article;
import com.buct.graduation.model.pojo.ConferencePaper;
import com.buct.graduation.model.pojo.Patent;
import com.buct.graduation.model.pojo.Project;
import com.buct.graduation.model.pojo.science.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher findByEmail(String email);

    int register(Teacher teacher);

    Teacher login(Teacher teacher);

    int resetPsw(Teacher teacher);

    Teacher update(Teacher teacher);

    List<Article> findArticlesByFlag(int tid, String flag);

    List<ConferencePaper> findPapersByFlag(int tid, String flag);

    List<Patent> findPatentsByFlag(int tid, String flag);

    List<Project> findProjectsByFlag(int tid, String flag);

    //新增，认领

    String claimArticle(int uAid, boolean claim);

    String claimProject(int pid, boolean claim);
}
