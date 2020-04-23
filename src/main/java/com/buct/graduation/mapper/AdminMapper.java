package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * account 暂时不用，忘了有什么用了
 */
@Mapper
public interface AdminMapper {
    @Insert("insert into admin (name, tel, email, psw, unit, picPath) values (#{name}, #{tel}, #{email}, #{psw}, #{unit}, #{picPath})")
    int add(Admin admin);

    @Select("select * from admin where email = #{email}")
    Admin findByEmail(String email);

    @Update("update admin set tel = #{tel}, unit = #{unit}, picPath = #{picPath} where email = #{email}")
    int update(Admin admin);

    @Update("update admin set psw = #{psw} where email = #{email}")
    int changePsw(Admin admin);

}
