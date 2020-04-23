package com.buct.graduation.model.vo;

import com.buct.graduation.model.pojo.recruit.Resume;

import java.util.ArrayList;
import java.util.List;

/**
 * 简历管理vo
 */
public class ResumeData {
    private Integer all;//全部
    private Integer wait;//待处理
    private Integer processing;//处理中
    private Integer passed;//通过
    private Integer failed;//未通过
    private List<Resume> resumes = new ArrayList<>();

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }

    public Integer getWait() {
        return wait;
    }

    public void setWait(Integer wait) {
        this.wait = wait;
    }

    public Integer getProcessing() {
        return processing;
    }

    public void setProcessing(Integer processing) {
        this.processing = processing;
    }

    public Integer getPassed() {
        return passed;
    }

    public void setPassed(Integer passed) {
        this.passed = passed;
    }

    public Integer getFailed() {
        return failed;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    public List<Resume> getResumes() {
        return resumes;
    }

    public void setResumes(List<Resume> resumes) {
        this.resumes = resumes;
    }
}
