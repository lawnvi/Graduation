package com.buct.graduation.model.pojo;

/**
 * 用户 包括应聘者、在职者、访问者
 * 应聘：招聘
 * 在职：科研评价/分析
 * 访问：临时评价（上传excel/注册db）
 */
public class User {
    private String name;//姓名
    private Integer id;
    private String email;//邮箱
    private String tel;//电话
    private String psw;//密码
    private String picPath;//头像路径
    private String notes;//备注

    private String resumePath;//简历路径
    private String education;//博士在读/博士后/已工作
    private String title;//头衔-四青
    private String fund;//基金-nsfc

    private String status;//简历进度 look/register/pass/fail
    private String registerTime;//投递时间/注册

    private String major;//专业
    private String sex;
    private String birthday;
    private String contactAddress;//联系地址

    private String level = "";//应聘者/老师

    //todo 待定是否加上 还有更多
    private String nation;//民族
    private String nationality;//国籍
    private String nativePlace;//籍贯

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getResumePath() {
        return resumePath;
    }

    public void setResumePath(String resumePath) {
        this.resumePath = resumePath;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
