package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.ConferencePaper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ConferencePaperMapper {
    @Insert("insert into conferencepaper (belong, uid, name, conference, section, citation, notes, role, isEsi) values(#{belong}, #{uid}, #{name}, #{conference}, #{section}, #{citation}, #{notes}, #{role}, #{isEsi})")
    int addPaper(ConferencePaper paper);

    @Update("update conferencepaper set belong=#{belong}, name=#{name}, conference=#{conference}, section=#{section}, citation=#{citation}, notes=#{notes}, role=#{role}, isEsi=#{isEsi} where id = #{id}")
    int update(ConferencePaper paper);

    @Delete("delete from conferencepaper where id = #{id}")
    void delete(int id);

    @Select("select * from conferencepaper where uid = #{uid}")
    List<ConferencePaper> findByUid(@Param("uid") int uid);

    @Select("select * from conferencepaper where id = #{id}")
    ConferencePaper findById(@Param("id") int id);

    @Select("select IFNULL(sum(citation), 0) from conferencepaper where uid = #{uid}")
    int countCitation(@Param("uid") int uid);

    @Select("select * from conferencepaper where belong = #{belong}")
    List<ConferencePaper> findByBelong(String belong);
}
