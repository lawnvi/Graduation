package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.Project;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProjectMapper {
    @Insert("insert into project (uid, name, funds, notes, role) values(#{uid}, #{name}, #{funds}, #{notes}, #{role})")
    int addProject(Project project);


    @Select("select * from project where uid = #{uid}")
    List<Project> findByUid(@Param("uid") int uid);

    @Select("select IFNULL(sum(funds),0) from project where uid = #{uid}")
    double countFunds(@Param("uid") int uid);
}
