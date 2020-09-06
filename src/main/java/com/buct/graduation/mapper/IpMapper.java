package com.buct.graduation.mapper;

import com.buct.graduation.model.spider.IpPort;
import org.apache.ibatis.annotations.*;

import java.util.Set;

@Mapper
public interface IpMapper {

    @Insert("insert into ippool (ip, port, type) values(#{ip}, #{port}, #{type})")
    int addIP(IpPort ip);

    @Select("select * from ippool where ip = #{ip}")
    IpPort findIPsByIp(String ip);

    @Select("select * from ippool where status = #{status}")
    Set<IpPort> findIPsByStatus(String status);

    @Update("update ippool set status = #{status}, useTimes=#{useTimes}, badTimes=#{badTimes} where id = #{id}")
    int update(IpPort ip);

    @Update("update ippool set status = #{status} where id = #{id}")
    int updateStatusById(IpPort ip);

    @Update("update ippool set status = #{status} where id = #{newStatus}")
    int updateStatusByStatus(@Param("status") String status, @Param("newStatus") String newStatus);

    @Delete("delete from ippool where status like #{status}")
    void deleteIPByStatus(String status);

    @Delete("delete from ippool where id = #{id}")
    void deleteIP(IpPort ip);

    @Select("SELECT * FROM `ippool` WHERE status = #{status} order by useTimes asc limit 0, 1")
    IpPort findIPByStatus(String status);
}
