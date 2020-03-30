package com.buct.graduation.mapper;

import com.buct.graduation.model.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into user (email, psw, level) values(#{email}, #{psw}, #{level})")
    int addUser(User user);

    @Update("update user set level=#{level}, name = #{name}, email = #{email}, tel = #{tel}, picPath = #{picPath}, notes = #{notes}, resumePath=#{resumePath}, education=#{education}, title=#{title}, fund=#{fund}, status=#{status}, major=#{major}, sex=#{sex}, birthday=#{birthday}, contactAddress=#{contactAddress} where id = #{id}")
    int updateUser(User user);

    @Update("update user set psw = #{psw} where email = #{email} and id = #{id}")
    int changePsw(User user);

    @Select("select * from user where id = #{id}")
    User findUserById(int id);

    @Select("select * from user where email = #{email}")
    User findUserByEmail(String email);

    @Select("select * from user where status = #{status}")
    List<User> findUserByStatus(String status);

    @Delete("delete from user where id = #{id}")
    void delete(int id);
}
