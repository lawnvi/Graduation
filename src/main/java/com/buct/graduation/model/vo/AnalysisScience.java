package com.buct.graduation.model.vo;

import com.buct.graduation.model.pojo.Reporter;
import com.buct.graduation.model.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class AnalysisScience {
    private int article = 0;
    private int project = 0;
    private int paper = 0;
    private int patent = 0;

    private int citation = 0;
    private int jcr_12 = 0;
    private int esi = 0;
    private int top = 0;

    private int patentIn = 0;
    private int patentOut = 0;
    private double funds = 0;

    private int fourYoung = 0;

    private User user;
    private Reporter reporter;

    public Reporter getReporter() {
        return reporter;
    }

    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private List<AnalysisYear> analysisYears = new ArrayList<>();

    public List<AnalysisYear> getAnalysisYears() {
        return analysisYears;
    }

    public void setAnalysisYears(List<AnalysisYear> analysisYears) {
        this.analysisYears = analysisYears;
    }

    public int getArticle() {
        return article;
    }

    public void setArticle(int article) {
        this.article = article;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public int getPaper() {
        return paper;
    }

    public void setPaper(int paper) {
        this.paper = paper;
    }

    public int getPatent() {
        return patent;
    }

    public void setPatent(int patent) {
        this.patent = patent;
    }

    public int getCitation() {
        return citation;
    }

    public void setCitation(int citation) {
        this.citation = citation;
    }

    public int getJcr_12() {
        return jcr_12;
    }

    public void setJcr_12(int jcr_12) {
        this.jcr_12 = jcr_12;
    }

    public int getEsi() {
        return esi;
    }

    public void setEsi(int esi) {
        this.esi = esi;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getPatentIn() {
        return patentIn;
    }

    public void setPatentIn(int patentIn) {
        this.patentIn = patentIn;
    }

    public int getPatentOut() {
        return patentOut;
    }

    public void setPatentOut(int patentOut) {
        this.patentOut = patentOut;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public int getFourYoung() {
        return fourYoung;
    }

    public void setFourYoung(int fourYoung) {
        this.fourYoung = fourYoung;
    }
}
