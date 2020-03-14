package com.buct.graduation.model.pojo.recruit;

/**
 * 面试官-面试记录-不一定要
 */
public class InterviewHr {
    private Integer id;
    private Integer iid;//面试记录
    private Integer hrId;//面试官
    private String notes;//面试评价
    private Integer score;//分数
//    private String timestamp;//时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer rid) {
        this.iid = rid;
    }

    public Integer getHrId() {
        return hrId;
    }

    public void setHrId(Integer hrId) {
        this.hrId = hrId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
