package com.buct.graduation.service.impl;

import com.buct.graduation.mapper.*;
import com.buct.graduation.model.pojo.*;
import com.buct.graduation.model.pojo.science.Teacher;
import com.buct.graduation.service.TeacherService;
import com.buct.graduation.util.GlobalName;
import com.buct.graduation.util.Utils;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private PatentMapper patentMapper;
    @Autowired
    private ConferencePaperMapper conferencePaperMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserArticleMapper userArticleMapper;
    @Autowired
    private JournalMapper journalMapper;
    @Autowired
    private ReporterMapper reporterMapper;

    @Override
    public Teacher findByEmail(String email) {
        User user = userMapper.findUserByEmail(email);
        if(user == null || !user.getLevel().equals(GlobalName.user_type_teacher)){
            return null;
        }
        Teacher teacher = teacherMapper.findById(user.getId());
        if(teacher == null){
            return null;
        }
        teacher.setUser(user);
        return teacher;
    }

    @Override
    @Transactional
    public int register(Teacher teacher) {
        if(findByEmail(teacher.getUser().getEmail()) != null){
            return -1;//已存在
        }
        teacher.getUser().setLevel(GlobalName.user_type_teacher);
        userMapper.addUser(teacher.getUser());
        //User user = userMapper.findUserByEmail(teacher.getUser().getEmail());
        System.out.println("uid:"+teacher.getUser().getId());
        teacher.setUid(teacher.getUser().getId());
        return teacherMapper.add(teacher);
    }

    @Override
    public Teacher login(Teacher teacher) {
        Teacher t = findByEmail(teacher.getUser().getEmail());
        if(t == null || !teacher.getUser().getPsw().equals(t.getUser().getPsw())){
            return null;
        }
        return t;
    }

    @Override
    public int resetPsw(Teacher teacher) {
        return userMapper.changePsw(teacher.getUser());
    }

    @Override
    @Transactional
    public Teacher update(Teacher teacher) {
        try {
            teacher.setPhonetic(Utils.getPinyin(teacher.getUser().getName()));
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        teacherMapper.update(teacher);
        userMapper.updateUser(teacher.getUser());
        return findByEmail(teacher.getUser().getEmail());
    }

    @Override
    public List<Article> findArticlesByFlag(int tid, String flag) {
        return null;
    }

    @Override
    public List<ConferencePaper> findPapersByFlag(int tid, String flag) {
        return null;
    }

    @Override
    public List<Patent> findPatentsByFlag(int tid, String flag) {
        return null;
    }

    @Override
    public List<Project> findProjectsByFlag(int tid, String flag) {
        return null;
    }

    @Override
    public String claimArticle(int uAid, boolean claim) {
        UserArticle article = userArticleMapper.findByOwnId(uAid);
        if(article == null){
            return "参数错误";
        }

        if (claim) {
            article.setFlag(GlobalName.teacher_flag_apply);
            userArticleMapper.update(article);
            return "已认领，待审核";
        } else {
            article.setFlag(GlobalName.teacher_flag_other);
            userArticleMapper.update(article);
            return "已移除";
        }
    }

    @Override
    public String claimProject(int pid, boolean claim) {
        Project project = projectMapper.findById(pid);
        if(project == null){
            return "参数错误";
        }

        if (claim) {
            project.setFlag(GlobalName.teacher_flag_apply);
            projectMapper.update(project);
            return "已认领，待审核";
        } else {
            project.setFlag(GlobalName.teacher_flag_other);
            projectMapper.update(project);
            return "已移除";
        }
    }
}
