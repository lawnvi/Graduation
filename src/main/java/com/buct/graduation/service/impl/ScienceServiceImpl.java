package com.buct.graduation.service.impl;

import com.buct.graduation.mapper.*;
import com.buct.graduation.model.pojo.*;
import com.buct.graduation.service.ScienceService;
import com.buct.graduation.util.GlobalName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScienceServiceImpl implements ScienceService {

    @Autowired
    private ArticleMapper articleMapper;
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
    public List<ConferencePaper> getConferencePapers() {
        return conferencePaperMapper.findByBelong(GlobalName.belongSchool);
    }

    @Override
    public List<Project> getProjects() {
        return projectMapper.findByBelong(GlobalName.belongSchool);
    }

    @Override
    public List<Patent> getPatents() {
        return patentMapper.findByBelong(GlobalName.belongSchool);
    }

    @Override
    public List<Journal> getJournals() {
        return journalMapper.findAll();
    }
}
