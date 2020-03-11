package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.Reporter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/*
private Integer uid;
    private String name;
    private String notes;
    private String education;//博士在读/博士后/已工作
    //    private String status;
    private String title;//头衔
    private String fund;//基金
    private String timestamp;
    private Double score;
    private String status;//简历进度
    private String post;//定岗
 */
@Mapper
public interface ReporterMapper {
    @Insert("insert into resume (uid, name, notes, education, title, fund, jcr, funds, sciCitation, impactFactor, score, post, jcrScore, esi, timestamp, citations) values(#{uid}, #{name}, #{notes}, #{education}, #{title}, #{fund}, #{jcr}, #{funds}, #{sciCitation}, #{IF}, #{score}, #{post}, #{jcrScore}, #{esi}, #{timestamp}, #{citation})")
    int insert(Reporter reporter);

    @Select("select * from resume where id = #{id}")
    Reporter findById(@Param("id") int id);

    @Select("select * from resume where status = #{status}")
    List<Reporter> findByStatus(@Param("status") String status);

}
