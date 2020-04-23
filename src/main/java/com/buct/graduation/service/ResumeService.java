package com.buct.graduation.service;

import com.buct.graduation.model.pojo.recruit.Interview;
import com.buct.graduation.model.pojo.recruit.Resume;
import com.buct.graduation.model.vo.ResumeData;

import java.util.List;

public interface ResumeService {
    ResumeData findResumesByStatus(String status);

    ResumeData findAllResumes();

    ResumeData findResumesBySid(int sid);

    int updateResume(Resume resume);

    String responseResume(int rid, String keyword);

    Resume findResumeById(int id);

    List<Resume> findResumeByUid(int uid);

    int addInterview(Interview interview);

    int updateInterview(Interview interview);

    int isAcceptResume(int rid, boolean isAccept);
}
