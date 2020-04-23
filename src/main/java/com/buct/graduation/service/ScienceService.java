package com.buct.graduation.service;

import com.buct.graduation.model.pojo.*;

import java.util.List;

public interface ScienceService {
    List<Article> getArticles();

    List<ConferencePaper> getConferencePapers();

    List<Project> getProjects();

    List<Patent> getPatents();

    List<Journal> getJournals();
}
