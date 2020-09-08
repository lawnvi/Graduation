package com.buct.graduation.model.pojo;

/**
 * 期刊信息
 * 查找期刊时
 */
public class Journal {
    private Integer id;
    private String name;//期刊名称
    private String AbbrTitle;//简称
    private String ISSN;//唯一码？
    private Boolean isTop = false;//是否TOP
    private String is_top = "否";
    private Float IF;//影响因子
    private Integer year;//数据年份
    private String section = "JCR-";//jcr分区1/2/3/4
    private String url = "";//letPub网址
    private String notes="";//备注
    private String updateDate;//更新时间

    public Journal() {
        this.name = "404";
        AbbrTitle = "404";
        this.ISSN = "";
        this.isTop = false;
        this.IF = Float.parseFloat("0");
        this.year = 0;
        this.section = "未知";
        this.url = "not find";
        this.notes = "404";
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getIs_top() {
        return isTop ? "是": "否";
    }

    public void setIs_top(String is_top) {
        this.is_top = is_top;
    }

    public String getAbbrTitle() {
        return AbbrTitle;
    }

    public void setAbbrTitle(String abbrTitle) {
        AbbrTitle = abbrTitle;
    }

    public String getISSN() {
        return ISSN;
    }

    public void setISSN(String ISSN) {
        this.ISSN = ISSN;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getTop() {
        return isTop;
    }

    public void setTop(Boolean top) {
        isTop = top;
        this.is_top = isTop ? "是" : "否";
    }

    public Float getIF() {
        return IF;
    }

    public void setIF(Float IF) {
        this.IF = IF;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
