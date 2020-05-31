package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.Article;
import com.buct.graduation.model.pojo.UserArticle;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface UserArticleMapper {
    @Insert("insert into userarticle (uid, aid, role, notes, flag) values(#{uid}, #{aid}, #{role}, #{notes}, #{flag})")
    int add(UserArticle userarticle);

    @Update("update userarticle set role = #{role}, notes = #{notes}, flag = #{flag} where uid = #{uid} and aid = #{aid}")
    int update(UserArticle userarticle);

    @Select("select * from userarticle where uid = #{uid} and aid=#{aid}")
    UserArticle findById(@Param("aid") int aid, @Param("uid") int uid);

    @Select("select * from userarticle where id = #{id}")
    UserArticle findByOwnId(int id);

    @Delete("delete from userarticle where id = #{id}")
    void deleteById(int id);

    @Delete("delete from userarticle where uid = #{uid} and aid=#{aid}")
    void delete(@Param("uid") int uid, @Param("aid") int aid);

    @Select("select * from userarticle where flag = #{flag}")
    @Results(id = "findByFlagMap",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(property = "article", column = "aid" ,one = @One(select = "com.buct.graduation.mapper.ArticleMapper.findById",fetchType = FetchType.EAGER))
    })
    List<UserArticle> findByFlag(String flag);

    @Select("select * from userarticle where uid = #{uid}")
    @Results(id = "findByUidMap",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(property = "article", column = "aid" ,one = @One(select = "com.buct.graduation.mapper.ArticleMapper.findById",fetchType = FetchType.EAGER))
    })
    List<UserArticle> findByUid(int uid);

}
