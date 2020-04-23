package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.UserArticle;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserArticleMapper {
    @Insert("insert into userarticle (uid, aid, role, notes) values(#{uid}, #{aid}, #{role}, #{notes})")
    int add(UserArticle userarticle);

    @Update("update userarticle set role = #{role}, notes = #{notes} where uid = #{uid} and aid = #{aid}")
    int update(UserArticle userarticle);

    @Select("select * from userarticle where uid = #{uid} and aid=#{aid}")
    UserArticle findById(@Param("aid") int aid, @Param("uid") int uid);

    @Delete("delete from userarticle where id = #{id}")
    void deleteById(int id);

    @Delete("delete from userarticle where uid = #{uid} and aid=#{aid}")
    void delete(@Param("uid") int uid, @Param("aid") int aid);

}
