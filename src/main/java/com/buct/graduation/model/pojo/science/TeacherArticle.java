package com.buct.graduation.model.pojo.science;

import com.buct.graduation.model.pojo.Article;

/**
 * 在职者的期刊论文记录
 */
public class TeacherArticle {
    private Integer id;
    private Integer tid;//教师id
    private Integer aid;//期刊论文id
    private String role;//担任角色
    private String notes;
    private Article article;
    private String flag;//GlobalName.teacher_//待认领等状态标识

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
