package com.buct.graduation.util.excel;

/**
 * 临时obj for year&month
 */
public class Student {
    private String year;
    private int number;
    private String name;
    private String authorType;
    private String subject;
    private String paper;
    private String journal;
    private String sci;
    private String time;
    private String xueyuan;
    private String isEsi;
    private String volume = "";
    private String page = "";
    private String issue = "";

    public Student(){}

    public Student(String year, int number, String name, String authorType, String subject, String paper, String journal, String sci, String time, String xueyuan, String isEsi) {
        this.year = year;
        this.number = number;
        this.name = name;
        this.authorType = authorType;
        this.subject = subject;
        this.paper = paper;
        this.journal = journal;
        this.sci = sci;
        this.time = time;
        this.xueyuan = xueyuan;
        this.isEsi = isEsi;
    }

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorType() {
        return authorType;
    }

    public void setAuthorType(String authorType) {
        this.authorType = authorType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getSci() {
        return sci;
    }

    public void setSci(String sci) {
        this.sci = sci;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getXueyuan() {
        return xueyuan;
    }

    public void setXueyuan(String xueyuan) {
        this.xueyuan = xueyuan;
    }

    public String getIsEsi() {
        return isEsi;
    }

    public void setIsEsi(String isEsi) {
        this.isEsi = isEsi;
    }
}
