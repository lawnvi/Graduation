package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.recruit.Interview;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InterviewMapper {
    @Insert("insert into interview (rid, status, time, place, score, advice) values (#{rid}, #{status}, #{time}, #{place}, #{score}, #{advice})")
    int addInterview(Interview interview);

    @Update("update interview set status = #{status}, time = #{time}, place = #{place}, score = #{score}, notes = #{notes}, advice=#{advice} where id = #{id}")
    int update(Interview interview);

    @Select("select * from interview where id = #{id}")
    Interview findById(int id);

    @Select("select * from interview where rid = #{rid} and status = #{status}")
    List<Interview> findByRidStatus(@Param("rid") int rid, @Param("status") String status);

    @Select("select * from interview where rid = #{rid}")
    List<Interview> findByRid(int rid);

    @Select("select * from interview where time >= DATE_SUB(curdate(),INTERVAL 0 DAY) ")
    List<Interview> findComing();

}
