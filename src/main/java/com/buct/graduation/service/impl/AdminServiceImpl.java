package com.buct.graduation.service.impl;

import com.buct.graduation.mapper.*;
import com.buct.graduation.model.pojo.Admin;
import com.buct.graduation.model.pojo.recruit.Interview;
import com.buct.graduation.service.AdminService;
import com.buct.graduation.util.GlobalName;
import com.buct.graduation.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private InterviewMapper interviewMapper;
    @Autowired
    private ResumeMapper resumeMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private UserArticleMapper userArticleMapper;

    @Override
    public int register(Admin admin) {
        return adminMapper.add(admin);
    }

    @Override
    public Admin login(Admin admin) {
        Admin user = adminMapper.findByEmail(admin.getEmail());
        if(user == null || !user.getPsw().equals(admin.getPsw())){
            return null;
        }
        return user;
    }

    @Override
    public Admin updateInform(Admin admin) {
        Admin user = adminMapper.findByEmail(admin.getEmail());
        if(user == null){
            return null;
        }
        adminMapper.update(admin);
        return adminMapper.findByEmail(admin.getEmail());
    }

    @Override
    public Admin findAdminByEmail(String email) {
        return adminMapper.findByEmail(email);
    }

    @Override
    public int changePsw(Admin admin) {
        return adminMapper.changePsw(admin);
    }

    @Override
    public HashMap<String, Integer> findAnalysisData() {
        String tomorrow = Utils.getDate().getYear() + "-" + (Utils.getDate().getDay()+1);
        List<Interview> interviews = interviewMapper.findComing();
        interviews.removeIf(interview -> interview.getTime().compareTo(tomorrow) >= 0);
        int today_interview = interviews.size();
        int today_resume = resumeMapper.findByStatus(GlobalName.resume_wait).size();
        int today_project = projectMapper.findByBelongFlag(GlobalName.belongSchool, GlobalName.teacher_flag_apply).size();
        int today_articles = userArticleMapper.findByFlag(GlobalName.teacher_flag_apply).size();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("interview", today_interview);
        map.put("resume", today_resume);
        map.put("project", today_project);
        map.put("article", today_articles);
        return map;
    }
}
