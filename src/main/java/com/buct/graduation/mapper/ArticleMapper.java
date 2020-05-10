package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.Article;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("insert into article (jid, name, journalIssn, citation, author, CAuthor, year, isESI, url, filePath, notes, volume, issue, page, isSci, addWay, address, uploadEmail) values(#{jid}, #{name}, #{journalIssn}, #{citation}, #{author}, #{CAuthor}, #{year}, #{isESI}, #{url}, #{filePath}, #{notes}, #{volume}, #{issue}, #{page}, #{isSci}, #{addWay}, #{address}, #{uploadEmail})")
    int insertArticle(Article article);

    @Update("update article set jid=#{jid}, name=#{name}, journalIssn=#{journalIssn}, citation=#{citation}, author=#{author}, CAuthor=#{CAuthor}, year=#{year}, isESI=#{isESI}, url=#{url}, filePath=#{filePath}, notes=#{notes}, volume=#{volume}, issue=#{issue}, page=#{page}, isSci=#{isSci}, addWay=#{addWay}, address=#{address} where id = #{id}")
    int update(Article article);

    @Delete("delete from article where id = #{id}")
    void delete(int id);

    @Select("select * from article where id  = #{id}")
    @Results(id = "findByAid",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(property = "journal", column = "jid" ,one = @One(select = "com.buct.graduation.mapper.JournalMapper.findById",fetchType = FetchType.EAGER))
    })
    Article findById(int id);

    @Select("select * from article where name  = #{name}")
    Article findByName(String name);

    //todo don't known is right
    @Select("select * from article where id in (select aid from userarticle where uid = #{uid} and role <> '参与')")
    @Results(id = "findByUidMap",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(property = "journal", column = "jid" ,one = @One(select = "com.buct.graduation.mapper.JournalMapper.findById",fetchType = FetchType.EAGER))
    })
    List<Article> findByIds(int uid);

    @Select("select * from article where addway = #{addway} and id in (select aid from userarticle where uid = #{uid} and role <> '参与')")
    @Results(id = "findByUidStatusMap",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(property = "journal", column = "jid" ,one = @One(select = "com.buct.graduation.mapper.JournalMapper.findById",fetchType = FetchType.EAGER))
    })
    List<Article> findByStatusUid(@Param("uid") int uid, @Param("addway") String addway);

    @Select("select * from article where address = #{address}")
    @Results(id = "findByBelongMap",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(property = "journal", column = "jid" ,one = @One(select = "com.buct.graduation.mapper.JournalMapper.findById",fetchType = FetchType.EAGER))
    })
    List<Article> findByAddress(String address);
}
