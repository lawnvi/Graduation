package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProjectMapper {
    @Insert("insert into project (uid, name, funds, notes, role) values(#{uid}, #{name}, #{funds}, #{notes}, #{role})")
    int addProject(Project project);

    @Update("update project set name=#{name}, funds=#{funds}, notes=#{notes}, role=#{role} where id = #{id}")
    int update(Project project);

    @Delete("delete from project where id = #{id}")
    void delete(int id);

    @Select("select * from project where uid = #{uid}")
    List<Project> findByUid(@Param("uid") int uid);

    @Select("select * from project where id = #{id}")
    Project findById(@Param("uid") int id);

    @Select("select IFNULL(sum(funds),0) from project where uid = #{uid}")
    double countFunds(@Param("uid") int uid);
}
