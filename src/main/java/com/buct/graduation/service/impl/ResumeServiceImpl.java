package com.buct.graduation.service.impl;

import com.buct.graduation.mapper.InterviewMapper;
import com.buct.graduation.mapper.ResumeMapper;
import com.buct.graduation.model.pojo.recruit.Interview;
import com.buct.graduation.model.pojo.recruit.Resume;
import com.buct.graduation.model.vo.ResumeData;
import com.buct.graduation.service.ResumeService;
import com.buct.graduation.util.EmailUtil;
import com.buct.graduation.util.GlobalName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResumeServiceImpl implements ResumeService {
    @Autowired
    private ResumeMapper resumeMapper;
    @Autowired
    private InterviewMapper interviewMapper;

    private void setResumeData(ResumeData data){
        data.setAll(resumeMapper.countAll());
        data.setWait(resumeMapper.countByStatus(GlobalName.resume_wait));
        data.setProcessing(resumeMapper.countByStatus(GlobalName.resume_processing));
        data.setPassed(resumeMapper.countByStatus(GlobalName.resume_pass));
        data.setFailed(resumeMapper.countByStatus(GlobalName.resume_fail));
    }
    @Override
    public ResumeData findResumesByStatus(String status) {
        ResumeData data = new ResumeData();
        data.setResumes(resumeMapper.findByStatus(status));
        setResumeData(data);
        return data;
    }

    @Override
    public ResumeData findAllResumes() {
        ResumeData data = new ResumeData();
        data.setResumes(resumeMapper.findAll());
        setResumeData(data);
        return data;
    }

    @Override
    public ResumeData findResumesBySid(int sid) {
        ResumeData data = new ResumeData();
        data.setResumes(resumeMapper.findBySid(sid));
        setResumeData(data);
        return data;
    }

    @Override
    public int updateResume(Resume resume) {
        return resumeMapper.updateResumeByAdmin(resume);
    }

    @Override
    public String responseResume(int rid, String keyword) {
        Resume resume = resumeMapper.findById(rid);
        if(resume == null)
            return "未找到此简历信息";
        if(keyword.equalsIgnoreCase("yes")){
            resume.setStatus(GlobalName.resume_processing);
        }else if(keyword.equalsIgnoreCase("no")){
            resume.setStatus(GlobalName.resume_fail);
        }else if(keyword.equalsIgnoreCase("pass")){
            resume.setStatus(GlobalName.resume_pass);
        }
        String msg = resumeMapper.updateResumeByAdmin(resume) == 1 ? "成功" : "失败";
        if(msg.equals("成功") && keyword.equals(GlobalName.resume_fail)){
            EmailUtil.sendMail(resume.getUser().getEmail(), EmailUtil.isPassResume(false, resume.getUser(), resume.getStation()));
        }
        return msg;
    }

    @Override
    public Resume findResumeById(int id) {

        return resumeMapper.findById(id);
    }

    @Override
    public List<Resume> findResumeByUid(int uid) {
        return resumeMapper.findResumeByUid(uid);
    }

    @Override
    public int addInterview(Interview interview) {
        return interviewMapper.addInterview(interview);
    }

    @Override
    public int updateInterview(Interview interview) {
        return interviewMapper.update(interview);
    }

    @Override
    @Transactional
    public int isAcceptResume(int rid, boolean isAccept) {
        Resume resume = resumeMapper.findById(rid);
        if(isAccept) {
            resume.setStatus(GlobalName.resume_pass);
        }else {
            resume.setStatus(GlobalName.resume_fail);
        }
        int result = resumeMapper.updateResumeByAdmin(resume);
        if(result > 0){
            EmailUtil.sendMail(resume.getUser().getEmail(), EmailUtil.isPassResume(isAccept, resume.getUser(), resume.getStation()));
        }
        return result;
    }
}
