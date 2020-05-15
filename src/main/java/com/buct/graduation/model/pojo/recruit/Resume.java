package com.buct.graduation.model.pojo.recruit;

import com.buct.graduation.model.pojo.Reporter;
import com.buct.graduation.model.pojo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 就某岗位发出的简历表
 */
public class Resume {
    private Integer id;
    private Integer uid;//user
    private Integer rid;//reporter
    private Integer sid;//岗位
    private String status;//状态
    private String notes;//备注
    private String resumePath;//简历路径
    private String time;

    private User user;
    private Reporter reporter = new Reporter();
    private Station station = new Station();
    private List<Interview> interviews = new ArrayList<>();

    public List<Interview> getInterviews() {
        return interviews;
    }

    public void setInterviews(List<Interview> interviews) {
        this.interviews = interviews;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Reporter getReporter() {
        return reporter;
    }

    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getResumePath() {
        return resumePath;
    }

    public void setResumePath(String resumePath) {
        this.resumePath = resumePath;
    }
}
