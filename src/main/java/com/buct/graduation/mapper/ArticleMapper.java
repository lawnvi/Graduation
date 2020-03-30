package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.Article;
import org.apache.ibatis.annotations.*;

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
    Article findById(int id);

    @Select("select * from article where name  = #{name}")
    Article findByName(String name);

    //todo don't known is right
    @Select("select * from article where id in (select aid from userArticle where uid = #{uid} and role <> '参与')")
    List<Article> findByIds(int uid);
}
