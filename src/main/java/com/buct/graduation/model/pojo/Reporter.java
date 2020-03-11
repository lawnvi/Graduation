package com.buct.graduation.model.pojo;

/**
 * 评价表
   */
public class Reporter {
    private Integer id;
    private Integer uid;
    private String name;
    private String notes;
    private String education;//博士在读/博士后/已工作
    //    private String status;
    private String title;//头衔
    private String fund;//基金

    private Integer jcr = 0;//jcr I/II 之和
    private Integer sciCitation = 0;//sci他引之和?
    private Integer citation = 0;//总被引
    private Integer esi = 0;//esi论文数
    private Integer jcrScore = 0;
    private double funds = 0;//基金
    private double IF = 0;//影响因子

    private Double score;//综合指标
    private String post;//定岗

    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getCitation() {
        return citation;
    }

    public void setCitation(Integer citation) {
        this.citation = citation;
    }

    public double getIF() {
        return IF;
    }

    public void setIF(double IF) {
        this.IF = IF;
    }

    public Integer getJcrScore() {
        return jcrScore;
    }

    public void setJcrScore(Integer jcrScore) {
        this.jcrScore = jcrScore;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public Integer getJcr() {
        return jcr;
    }

    public void setJcr(Integer jcr) {
        this.jcr = jcr;
    }

    public Integer getSciCitation() {
        return sciCitation;
    }

    public void setSciCitation(Integer sciCitation) {
        this.sciCitation = sciCitation;
    }

    public Integer getEsi() {
        return esi;
    }

    public void setEsi(Integer esi) {
        this.esi = esi;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }


    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
