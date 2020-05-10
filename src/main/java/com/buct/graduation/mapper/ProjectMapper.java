package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.ConferencePaper;
import com.buct.graduation.model.pojo.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProjectMapper {
    @Insert("insert into project (belong, uid, name, funds, notes, role, number,fund,charge,checked,flag,url) values(#{belong}, #{uid}, #{name}, #{funds}, #{notes}, #{role}, #{number},#{fund},#{charge},#{checked}, #{flag},#{url})")
    int addProject(Project project);

    @Update("update project set belong=#{belong}, name=#{name}, funds=#{funds}, notes=#{notes}, role=#{role}, number=#{number}, fund=#{fund},charge=#{charge}, checked=#{checked}, flag=#{flag}, url=#{url} where id = #{id}")
    int update(Project project);

    @Delete("delete from project where id = #{id}")
    void delete(int id);

    @Select("select * from project where uid = #{uid}")
    List<Project> findByUid(@Param("uid") int uid);

    @Select("select * from project where uid = #{uid} and checked = #{isChecked}")
    List<Project> findByUidStatus(@Param("uid") int uid, @Param("checked") boolean checked);

    @Select("select * from project where id = #{id}")
    Project findById(@Param("id") int id);

    @Select("select IFNULL(sum(funds),0) from project where uid = #{uid}")
    double countFunds(@Param("uid") int uid);

    @Select("select * from project where belong = #{belong}")
    List<Project> findByBelong(String belong);

    @Select("select * from project where belong = #{belong} and flag = #{flag}")
    List<Project> findByBelongFlag(@Param("belong") String belong, @Param("flag") String flag);
}
