package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("insert into article (jid, name, journalName, citation, author, CAuthor, year, isESI, url, filePath, notes, volume, issue, page) values(#{jid}, #{name}, #{journalName}, #{citation}, #{author}, #{CAuthor}, #{year}, #{isESI}, #{url}, #{filePath}, #{notes}, #{volume}, #{issue}, #{page})")
    int insertArticle(Article article);

    @Select("select * from article where id  = #{id}")
    Article findById(int id);

    //todo don't known is right
    @Select("select * from article where id in #{id}")
    List<Article> findByIds(List<Integer> id);
}
