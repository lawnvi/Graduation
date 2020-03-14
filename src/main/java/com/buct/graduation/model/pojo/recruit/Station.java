package com.buct.graduation.model.pojo.recruit;

import com.buct.graduation.util.Utils;

/**
 * 岗位表
 * 管理员管理
 * 应聘者查看
 */
public class Station {
    private Integer id;
    private String title;//题目
    private Integer number;//需求数量
    private String status;//状态
    private String start;//发布时间
    private String end;//截至时间
    private String treatment;//待遇
    private String conditions;//任职条件
    private String notes;//任职描述 做什么
    private String process;//招聘流程
    private String contacts;//联系人
    private String tel;//联系电话
    private String email;//联系邮箱
    private String contactAddress;//通讯地址

    private String major;//专业要求
    private String education;//学位要求
    private int maxAge;//年龄要求

    public Station() {
        this.title = "";
        this.number = 0;
        this.status = "正常";
        this.start = Utils.getDate().toDate();
        this.end = Utils.getDate().toDate();
        this.treatment = "";
        this.conditions = "";
        this.notes = "";
        this.process = "";
        this.contacts = "";
        this.tel = "";
        this.email = "";
        this.contactAddress = "";
        this.major = "";
        this.education = "无";
        this.maxAge = 0;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
