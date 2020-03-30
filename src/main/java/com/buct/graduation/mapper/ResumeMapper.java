package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.recruit.Resume;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ResumeMapper {
    @Insert("insert into resume (uid, sid, rid, status, resumePath, notes) values (#{uid}, #{sid}, #{rid}, #{status}, #{resumePath}, #{notes})")
    int addResume(Resume resume);

    //todo 未处理前可以修改
    @Update("update resume set status=#{status}, resumePath = #{resumePath}, sid = #{sid} where id=#{id} and status == '待处理'")
    int updateResume(Resume resume);

    @Select("select * from resume where uid = #{uid}")
    List<Resume> findResumeByUid(int uid);

    @Select("select * from resume where uid = #{uid} and sid = #{sid}")
    Resume findByUid_Sid(@Param("uid") int uid, @Param("sid") int sid);
}
