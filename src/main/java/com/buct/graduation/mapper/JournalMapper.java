package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.Journal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface JournalMapper {
    @Insert("insert into journal (name, AbbrTitle, issn, isTop, impact_factor, year, section, url, notes) values (#{name}, #{AbbrTitle}, #{ISSN}, #{isTop}, #{IF}, #{year}, #{section}, #{url}, #{notes})")
    int addJournal(Journal journal);

    @Select("select * from journal where id = #{id}")
    Journal findById(int id);

    @Select("select * from journal where issn = #{kw} or AbbrTitle=#{kw} or name=#{kw}")
    Journal findByKeyword(String kw);

    @Update("update journal set name=#{name}, AbbrTitle=#{AbbrTitle}, issn=#{ISSN}, impact_factor=#{IF}, year=#{year}, section=#{section}, url=#{url}, notes=#{notes} where id=#{id}")
    int update(Journal journal);
}
