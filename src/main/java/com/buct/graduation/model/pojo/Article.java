package com.buct.graduation.model.pojo;

public class Article {
    private Integer id;
    private Integer jid;
    private String name;//论文名称
    private String journalName;//期刊名称
    private Integer citation = 0;//被引次数
    private String author;//作者
    private String CAuthor;//通讯作者
    private String year;//发表年份
    private String notes;
    private String url = "";
    private Boolean isESI = false;//是否高被引
    private String filePath;//存档位置
    private String volume = "";//卷号
    private String page = "";//页码
    private String issue = "";//期

    private Journal journal;//期刊

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public Integer getJid() {
        return jid;
    }

    public void setJid(Integer jid) {
        this.jid = jid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journal) {
        this.journalName = journal;
    }

    public Integer getCitation() {
        return citation;
    }

    public void setCitation(Integer citation) {
        this.citation = citation;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCAuthor() {
        return CAuthor;
    }

    public void setCAuthor(String CAuthor) {
        this.CAuthor = CAuthor;
    }

    public Boolean getESI() {
        return isESI;
    }

    public void setESI(Boolean ESI) {
        isESI = ESI;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
