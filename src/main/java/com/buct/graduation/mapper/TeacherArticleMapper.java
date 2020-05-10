package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.science.TeacherArticle;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherArticleMapper {
    @Insert("insert into teacherarticle (tid, aid, role, notes, flag) values(#{tid}, #{aid}, #{role}, #{notes}, #{flag})")
    int add(TeacherArticle teacherArticle);

    @Update("update teacherarticle set tid=#{tid}, aid=#{aid}, role=#{role}, notes=#{notes}, flag=#{flag} where id = #{id}")
    int update(TeacherArticle teacherArticle);

    @Select("select * from teacherarticle where id = #{id}")
    TeacherArticle findById(int id);

    @Select("select * from teacherarticle where tid = #{tid}")
    List<TeacherArticle> findByTid(int tid);

    @Select("select * from teacherarticle where aid = #{aid}")
    List<TeacherArticle> findByAid(int aid);

    @Delete("delete from teacherarticle where id = #{id}")
    void delete(int id);
}
