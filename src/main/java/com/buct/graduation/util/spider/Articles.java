package com.buct.graduation.util.spider;

public class Articles {
//    private Integer id;
    private String name;//论文名称
    private String journal;//期刊名称
    private Boolean isTop = false;//是否TOP
    private Double IF = 0.0;//影响因子
    private Integer citation = 0;//被引次数
    private String author;
    private String CAuthor;
    private String year;//发表年份

    private Integer section = 0;
    private String notes;
    private String url = "";
    private Boolean isESI = false;//是否高被引


    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

//    public Integer getId() {
//        return id;
//    }

//    public void setId(Integer id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
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
