package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.recruit.Resume;
import com.buct.graduation.util.GlobalName;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface ResumeMapper {
    @Insert("insert into resume (uid, sid, rid, status, resumePath, notes) values (#{uid}, #{sid}, #{rid}, #{status}, #{resumePath}, #{notes})")
    int addResume(Resume resume);

    //todo 未处理前可以修改
    @Update("update resume set status=#{status}, resumePath = #{resumePath}, sid = #{sid} where id=#{id} and status == "+ GlobalName.resume_wait +"")
    int updateResumeByUser(Resume resume);

    @Update("update resume set status=#{status}, resumePath = #{resumePath}, sid = #{sid} where id=#{id}")
    int updateResumeByAdmin(Resume resume);

    @Select("select * from resume where id = #{id}")
    @Results(id = "ridMap",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "uid",property = "uid"),
            @Result(column = "sid",property = "sid"),
            @Result(column = "rid",property = "rid"),
            @Result(column = "status",property = "status"),
            @Result(column = "notes",property = "notes"),
            @Result(column = "resumePath",property = "resumePath"),
            @Result(column = "time",property = "time"),
            @Result(property = "user", column = "uid" ,one = @One(select = "com.buct.graduation.mapper.UserMapper.findUserById",fetchType = FetchType.EAGER)),
            @Result(property = "reporter", column = "rid" ,one = @One(select = "com.buct.graduation.mapper.ReporterMapper.findById",fetchType = FetchType.EAGER)),
            @Result(property = "station", column = "sid" ,one = @One(select = "com.buct.graduation.mapper.StationMapper.findById",fetchType = FetchType.EAGER))
    })
    Resume findById(int id);

    @Select("select * from resume where uid = #{uid}")
    List<Resume> findResumeByUid(int uid);

    @Select("select * from resume where uid = #{uid} and sid = #{sid}")
    Resume findByUid_Sid(@Param("uid") int uid, @Param("sid") int sid);

    @Select("select count(id) from resume where sid = #{sid}")
    int countBySid(int sid);

    @Select("select count(id) from resume where sid = #{sid} and status = #{status}")
    int countBySid_Status(@Param("sid") int sid, @Param("status") String status);

    @Select("select * from resume")
    @Results(id = "allMap",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "uid",property = "uid"),
            @Result(column = "sid",property = "sid"),
            @Result(column = "rid",property = "rid"),
            @Result(column = "status",property = "status"),
            @Result(column = "notes",property = "notes"),
            @Result(column = "resumePath",property = "resumePath"),
            @Result(column = "time",property = "time"),
            @Result(property = "user", column = "uid" ,one = @One(select = "com.buct.graduation.mapper.UserMapper.findUserById",fetchType = FetchType.EAGER)),
            @Result(property = "reporter", column = "rid" ,one = @One(select = "com.buct.graduation.mapper.ReporterMapper.findById",fetchType = FetchType.EAGER)),
            @Result(property = "station", column = "sid" ,one = @One(select = "com.buct.graduation.mapper.StationMapper.findById",fetchType = FetchType.EAGER))
    })
    List<Resume> findAll();

    @Select("select * from resume where status = #{status}")
    @Results(id = "statusMap",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "uid",property = "uid"),
            @Result(column = "sid",property = "sid"),
            @Result(column = "rid",property = "rid"),
            @Result(column = "status",property = "status"),
            @Result(column = "notes",property = "notes"),
            @Result(column = "resumePath",property = "resumePath"),
            @Result(column = "time",property = "time"),
            @Result(property = "user", column = "uid" ,one = @One(select = "com.buct.graduation.mapper.UserMapper.findUserById",fetchType = FetchType.EAGER)),
            @Result(property = "reporter", column = "rid" ,one = @One(select = "com.buct.graduation.mapper.ReporterMapper.findById",fetchType = FetchType.EAGER)),
            @Result(property = "station", column = "sid" ,one = @One(select = "com.buct.graduation.mapper.StationMapper.findById",fetchType = FetchType.EAGER))
    })
    List<Resume> findByStatus(String status);

    @Select("select * from resume where sid = #{sid}")
    @Results(id = "sidMap",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "uid",property = "uid"),
            @Result(column = "sid",property = "sid"),
            @Result(column = "rid",property = "rid"),
            @Result(column = "status",property = "status"),
            @Result(column = "notes",property = "notes"),
            @Result(column = "resumePath",property = "resumePath"),
            @Result(column = "time",property = "time"),
            @Result(property = "user", column = "uid" ,one = @One(select = "com.buct.graduation.mapper.UserMapper.findUserById",fetchType = FetchType.EAGER)),
            @Result(property = "reporter", column = "rid" ,one = @One(select = "com.buct.graduation.mapper.ReporterMapper.findById",fetchType = FetchType.EAGER)),
            @Result(property = "station", column = "sid" ,one = @One(select = "com.buct.graduation.mapper.StationMapper.findById",fetchType = FetchType.EAGER))
    })
    List<Resume> findBySid(int sid);

    @Select("select count(id) from resume")
    int countAll();

    @Select("select count(id) from resume where status = #{status}")
    int countByStatus(String status);


}
