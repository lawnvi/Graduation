package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.Patent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PatentMapper {
    @Select("select * from patent where uid = #{uid}")
    List<Patent> findByUid(@Param("uid") int uid);

    @Select("select count(*) from patent where uid = #{uid} and category = #{category}")
    int countByCategory(@Param("uid") int uid, @Param("category") String category);
}
