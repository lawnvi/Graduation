package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.Journal;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface JournalMapper {
    @Insert("insert into journal (name, AbbrTitle, issn, isTop, impact_factor, year, section, url, notes) values (#{name}, #{AbbrTitle}, #{ISSN}, #{isTop}, #{IF}, #{year}, #{section}, #{url}, #{notes})")
    int addJournal(Journal journal);

    @Select("select * from journal where id = #{id}")
    @Results({@Result(column="impact_factor", property="IF")})
    Journal findById(int id);

    @Select("select * from journal")
    @Results({@Result(column="impact_factor", property="IF")})
    List<Journal> findAll();

    @Select("select * from journal where updateDate < #{date}")
    @Results({@Result(column="impact_factor", property="IF")})
    List<Journal> findAllOld(String date);

    @Select("select * from journal where issn = #{kw} or AbbrTitle=#{kw} or name=#{kw}")
    @Results({@Result(column="impact_factor", property="IF")})
    Journal findByKeyword(String kw);

    @Update("update journal set name=#{name}, AbbrTitle=#{AbbrTitle}, issn=#{ISSN}, impact_factor=#{IF}, year=#{year}, section=#{section}, url=#{url}, notes=#{notes}, updateDate=#{updateDate} where id=#{id}")
    int update(Journal journal);
}
