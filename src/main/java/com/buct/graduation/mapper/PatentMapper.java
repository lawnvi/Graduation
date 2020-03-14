package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.Patent;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PatentMapper {
    @Insert("insert into patent (uid, name, category, notes, role) values (#{uid}, #{name}, #{category}, #{notes}, #{role})")
    int add(Patent patent);

    @Update("update patent set name=#{name}, category=#{category}, notes=#{notes}, role=#{role} where id = #{id}")
    int update(Patent patent);

    @Delete("delete from patent where id = #{id}")
    void delete(int id);

    @Select("select * from patent where uid = #{uid}")
    List<Patent> findByUid(@Param("uid") int uid);

    @Select("select * from patent where id = #{id}")
    Patent findById(@Param("id") int id);

    @Select("select count(*) from patent where uid = #{uid} and category = #{category}")
    int countByCategory(@Param("uid") int uid, @Param("category") String category);
}
