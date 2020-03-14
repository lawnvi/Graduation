package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.recruit.Resume;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ResumeMapper {
    @Insert("insert into resume (uid, sid, rid, status, resumePath, notes) values (#{uid}, #{sid}, #{rid}, #{status}, #{resumePath}, #{notes})")
    int addResume(Resume resume);

    //todo 未处理前可以修改
    @Update("update resume set status=#{status}, resumePath = #{resumePath}, sid = #{sid} where id=#{id} and status == '待处理'")
    int updateResume(Resume resume);
}
