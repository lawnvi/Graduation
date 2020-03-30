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

    @Select("select * from (SELECT * FROM station WHERE end > now() order by start desc) as a limit #{start}, #{number}")
    List<Station> findJobsWithPage(@Param("start") int start, @Param("number") int number);

    @Delete("delete from station where id =#{id}")
    void delete(int id);
}
