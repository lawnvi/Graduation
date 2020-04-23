package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.ConferencePaper;
import com.buct.graduation.model.pojo.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProjectMapper {
    @Insert("insert into project (belong, uid, name, funds, notes, role, number,fund,charge,ischecked) values(#{belong}, #{uid}, #{name}, #{funds}, #{notes}, #{role}, #{number},#{fund},#{charge},#{ischecked})")
    int addProject(Project project);

    @Update("update project set belong=#{belong}, name=#{name}, funds=#{funds}, notes=#{notes}, role=#{role}, number=#{number}, fund=#{fund},charge=#{charge}, ischecked=#{ischecked} where id = #{id}")
    int update(Project project);

    @Delete("delete from project where id = #{id}")
    void delete(int id);

    @Select("select * from project where uid = #{uid}")
    List<Project> findByUid(@Param("uid") int uid);

    @Select("select * from project where uid = #{uid} and ischecked = #{isChecked}")
    List<Project> findByUidStatus(@Param("uid") int uid, @Param("isChecked") boolean isChecked);

    @Select("select * from project where id = #{id}")
    Project findById(@Param("id") int id);

    @Select("select IFNULL(sum(funds),0) from project where uid = #{uid}")
    double countFunds(@Param("uid") int uid);

    @Select("select * from project where belong = #{belong}")
    List<Project> findByBelong(String belong);
}
