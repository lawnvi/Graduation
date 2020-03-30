package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.UserArticle;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserArticleMapper {
    @Insert("insert into userArticle (uid, aid, role, notes) values(#{uid}, #{aid}, #{role}, #{notes})")
    int add(UserArticle userArticle);

    @Update("update userArticle set role = #{role}, notes = #{notes} where uid = #{uid} and aid = #{aid}")
    int update(UserArticle userArticle);

    @Select("select * from userArticle where uid = #{uid} and aid=#{aid}")
    UserArticle findById(@Param("aid") int aid, @Param("uid") int uid);

    @Delete("delete from userArticle where id = #{id}")
    void deleteById(int id);

    @Delete("delete from userArticle where uid = #{uid} and aid=#{aid}")
    void delete(@Param("uid") int uid, @Param("aid") int aid);

}
