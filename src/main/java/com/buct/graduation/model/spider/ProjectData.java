package com.buct.graduation.model.spider;

import com.buct.graduation.model.pojo.Project;
import com.buct.graduation.util.GlobalName;

/**
 * 爬虫使用obj
 */
public class ProjectData {
    private String charge;//负责人
    private String company;//单位
    private Double funds;//单位:万元
    private String number;//项目编号
    private String fund;//基金
    private String belongSubject;//所属学科
    private int authorizeYear;//批准年份
    private String name;//项目名称
    private String subjectClass;//学科分类
    private String subjectCode;//学科代码
    private String runtime;//执行时间
    private String notes;//备注

    private String url = "";//链接

    public Project toProject(){
        Project project = new Project();
        project.setName(this.name);
        project.setFund(this.fund);
        project.setFunds(this.funds);
        project.setUrl(this.url);
        project.setCharge(this.charge);
        project.setRole(GlobalName.project_leader);
        project.setNotes(this.notes);
        project.setNumber(this.number);
        project.setChecked(true);
        return project;
    }

    public Project toProject(Project project){
        project.setName(this.name);
        project.setFund(this.fund);
        project.setFunds(this.funds);
        project.setUrl(this.url);
        project.setCharge(this.charge);
        project.setNotes(this.notes);
        project.setNumber(this.number);
        project.setChecked(true);
        return project;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getBelongSubject() {
        return belongSubject;
    }

    public void setBelongSubject(String belongSubject) {
        this.belongSubject = belongSubject;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Double getFunds() {
        return funds;
    }

    public void setFunds(Double funds) {
        this.funds = funds;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public int getAuthorizeYear() {
        return authorizeYear;
    }

    public void setAuthorizeYear(int authorizeYear) {
        this.authorizeYear = authorizeYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectClass() {
        return subjectClass;
    }

    public void setSubjectClass(String subjectClass) {
        this.subjectClass = subjectClass;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
