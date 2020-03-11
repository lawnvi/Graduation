package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.ConferencePaper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ConferencePaperMapper {
    @Insert("insert into conferencepaper (uid, name, conference, section, citation, notes, role) values(#{uid}, #{name}, #{conference}, #{section}, #{citation}, #{notes}, #{role})")
    int addPaper(ConferencePaper paper);

    @Select("select * from conferencepaper where uid = #{uid}")
    List<ConferencePaper> findByUid(@Param("uid") int uid);

    @Select("select IFNULL(sum(citation), 0) from conferencepaper where uid = #{uid}")
    int countCitation(@Param("uid") int uid);
}
