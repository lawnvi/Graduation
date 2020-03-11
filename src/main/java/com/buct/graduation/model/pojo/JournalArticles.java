package com.buct.graduation.model.pojo;

/**
 * 期刊论文信息
 */
public class JournalArticles {
    private Integer id;
    private Integer uid = 1;
    private Integer jid;//期刊id
    private String name;//论文名称
    private String journal;//期刊名称
    private Boolean isTop;//是否TOP
    private Double IF;//影响因子
    private Boolean isESI;//是否高被引
    private Integer citation;//被引次数
    private String notes;//备注
    private String year;//发表年份
    private Integer JCR;//jcr分区1/2/3/4
    private String url = "";
    private String volume = "";//卷号
    private String page = "";//页码
    private String issue = "";//期

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

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

    public Integer getJid() {
        return jid;
    }

    public void setJid(Integer jid) {
        this.jid = jid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getJCR() {
        return JCR;
    }

    public void setJCR(Integer JCR) {
        this.JCR = JCR;
    }

    public Boolean getTop() {
        return isTop;
    }

    public void setTop(Boolean top) {
        isTop = top;
    }

    public Double getIF() {
        return IF;
    }

    public void setIF(Double IF) {
        this.IF = IF;
    }

    public Integer getCitation() {
        return citation;
    }

    public void setCitation(Integer citation) {
        this.citation = citation;
    }

    public Boolean getESI() {
        return isESI;
    }

    public void setESI(Boolean ESI) {
        isESI = ESI;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
