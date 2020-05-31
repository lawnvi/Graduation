package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.science.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper {
    @Insert("insert into teacher (name, uid, position, phonetic, notes) values(#{name}, #{uid}, #{position}, #{phonetic}, #{notes})")
    int add(Teacher teacher);

    @Update("update teacher set name = #{name}, position = #{position}, phonetic=#{phonetic}, notes=#{notes} where id = #{id}")
    int update(Teacher teacher);

    @Select("select * from teacher where uid = #{uid}")
    Teacher findById(int uid);

    @Delete("delete from teacher where uid = #{uid}")
    void delete(int uid);

    @Select("select * from teacher")
    List<Teacher> findAll();
}
