package com.buct.graduation.model.pojo;

import com.buct.graduation.util.GlobalName;

public class Article {
    private Integer id;
    private Integer jid;
    private String name;//论文名称
    //todo issn 可能没有
    private String journalIssn = "";//期刊名称
    private Integer citation = 0;//被引次数
    private String author = "";//作者
    private String CAuthor = "";//通讯作者
    private String year = "";//发表年份
    private String notes = "";
    private String url = "";
    private Boolean isESI = false;//是否高被引
    private String is_esi = "否";
    private String filePath;//存档位置
    private String volume = "";//卷号
    private String page = "";//页码
    private String issue = "";//期

    private Boolean isSci = false;//sci收录
    private String is_sci;
    private String addWay = GlobalName.addWay_missing_c;
    private String address = "应聘";//是否学院
    private String uploadEmail;//录入者邮箱

    private Journal journal = new Journal();//期刊

    private String AttrName;

    public String getIs_esi() {
        return is_esi;
    }

    public void setIs_esi(String is_esi) {
        this.is_esi = is_esi;
    }

    public String getIs_sci() {
        return is_sci;
    }

    public void setIs_sci(String is_sci) {
        this.is_sci = is_sci;
    }

    public void setAttrName(String attrName) {
        AttrName = attrName;
    }

    public String getAttrName() {
        return AttrName;
    }

    public void setAttrName() {
        if(this.name.length() > 70) {
            int i = 69;
            while (i < this.name.length() && this.name.charAt(i) == ' '){
                i++;
            }
            this.AttrName = name.substring(0, i+1) + " ...";
        }
        else {
            this.AttrName = this.name;
        }
    }

    public String getUploadEmail() {
        return uploadEmail;
    }

    public void setUploadEmail(String uploadEmail) {
        this.uploadEmail = uploadEmail;
    }

    public Boolean getSci() {
        return isSci;
    }

    public void setSci(Boolean sci) {
        isSci = sci;
        this.is_sci = isSci ? "是" : "否";
    }

    public String getAddWay() {
        return addWay;
    }

    public void setAddWay(String addWay) {
        this.addWay = addWay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public Journal getJournal() {
        if(journal == null){
            System.out.println("no journal "+ journalIssn);
            return new Journal();
        }
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
        setAttrName();
    }

    public String getJournalIssn() {
        return journalIssn;
    }

    public void setJournalIssn(String journal) {
        this.journalIssn = journal;
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
        this.is_esi = isESI ? "是" : "否";
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
