package com.buct.graduation.model.vo;

import com.buct.graduation.model.pojo.*;
import com.buct.graduation.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class UserVData {
    private User user = new User();
    private List<Article> articles = new ArrayList<>();
    private List<ConferencePaper> papers = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();
    private List<Patent> patents = new ArrayList<>();
    private Reporter reporter = new Reporter();

    private int a_num = 0;
    private int c_num = 0;
    private int pr_num = 0;
    private int pa_num = 0;

    public Reporter calculateScore(){
        return Utils.getScore(reporter, projects, patents, articles, papers);
    }

    public int getA_num() {
        return a_num;
    }

    public void setA_num(int a_num) {
        this.a_num = a_num;
    }

    public int getC_num() {
        return c_num;
    }

    public void setC_num(int c_num) {
        this.c_num = c_num;
    }

    public int getPr_num() {
        return pr_num;
    }

    public void setPr_num(int pr_num) {
        this.pr_num = pr_num;
    }

    public int getPa_num() {
        return pa_num;
    }

    public void setPa_num(int pa_num) {
        this.pa_num = pa_num;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        this.a_num = articles.size();
    }

    public List<ConferencePaper> getPapers() {
        return papers;
    }

    public void setPapers(List<ConferencePaper> papers) {
        this.papers = papers;
        this.c_num = papers.size();
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
        this.pr_num = projects.size();
    }

    public List<Patent> getPatents() {
        return patents;
    }

    public void setPatents(List<Patent> patents) {
        this.patents = patents;
        this.pa_num = patents.size();
    }

    public Reporter getReporter() {
        return reporter;
    }

    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }
}
