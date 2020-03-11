package com.buct.graduation.service;

import com.buct.graduation.model.pojo.User;

import java.util.List;

public interface UserService {
    int register(User user);

    User login(String email, String psw);

    User findUserById(int id);

    User findUserByEmail(String email);

    List<User> findUsersByStatus(String status);

    int updateUser(User user);
}
