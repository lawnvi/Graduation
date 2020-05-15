package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.recruit.Station;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface StationMapper {
    @Insert("insert into station (title, number, status, start, end, treatment, conditions, notes, process, contacts, tel, email, contactAddress, maxAge, major, education) values (#{title}, #{number}, #{status}, #{start}, #{end}, #{treatment}, #{conditions}, #{notes}, #{process}, #{contacts}, #{tel}, #{email}, #{contactAddress}, #{maxAge}, #{major}, #{education})")
    int add(Station station);

    @Update("update station set title=#{title}, number = #{number}, status=#{status}, end=#{end}, treatment=#{treatment}, conditions=#{conditions}, notes=#{notes}, process=#{process},contacts=#{contacts}, tel=#{tel}, email=#{email}, contactAddress=#{contactAddress}, maxAge=#{maxAge}, major=#{major}, education=#{education} where id = #{id}")
    int update(Station station);

/*    @Update("update station set passed=#{passed}, interviewing=#{interviewing} where id = #{id}")
    int updatePassed(Station station);

    @Update("update station set interviewing = #{interviewing} where id = #{id}")
    int updateInterview(Station station);

    @Update("update station set resumeNumber=#{resumeNumber} where id = #{id}")
    int updateNewResume(Station station);*/

    @Select("select * from station where id = #{id}")
    Station findById(int id);

    @Select("select * from station")
    List<Station> findAll();

    @Select("select * from station where status=#{status}")
    List<Station> findByStatus(String status);

    @Select("select * from (SELECT * FROM station WHERE end > now() order by start desc) as a limit #{start}, #{number}")
    List<Station> findJobsWithPage(@Param("start") int start, @Param("number") int number);

    @Select("select * from (SELECT * FROM station WHERE end > now() and (title like #{kw} or major like #{kw}) order by start desc) as a limit #{start}, #{number}")
    List<Station> findJobsByKeywordWithPage(@Param("start") int start, @Param("number") int number, @Param("kw") String kw);


    @Delete("delete from station where id =#{id}")
    void delete(int id);

    @Select("select count(id) from station")
    int countAll();

    @Select("select count(id) from station where status = #{status}")
    int countByStatus(String status);

    @Update("update station set status = #{status}, notes = #{msg} where end < now()")
    void updateOverTime(@Param("status") String status, @Param("msg") String msg);
}
