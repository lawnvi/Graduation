package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.recruit.Station;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StationMapper {
    @Insert("insert into station (title, number, status, start, end, treatment, conditions, notes, process, contacts, tel, email, contactAddress, maxAge, major, education) values (#{title}, #{number}, #{status}, #{start}, #{end}, #{treatment}, #{conditions}, #{notes}, #{process}, #{contacts}, #{tel}, #{email}, #{contactAddress}, #{maxAge}, #{major}, #{education})")
    int add(Station station);

    @Update("update station set title=#{title} number = #{number}, status=#{status}, start=#{start}, end=#{end}, treatment=#{treatment}, conditions=#{conditions}, notes=#{notes}, process=#{process},contacts=#{contacts}, tel=#{tel}, email=#{email}, contactAddress=#{contactAddress}, maxAge=#{maxAge}, major=#{major}, education=#{education} where id = #{id}")
    int update(Station station);

    @Select("select * from station where id = #{id}")
    Station findById(int id);

    @Select("select * from station")
    List<Station> findAll();

    @Delete("delete from station where id =#{id}")
    void delete(int id);
}
