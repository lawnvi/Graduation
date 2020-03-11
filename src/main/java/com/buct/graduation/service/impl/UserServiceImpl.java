package com.buct.graduation.service.impl;

import com.buct.graduation.mapper.UserMapper;
import com.buct.graduation.model.pojo.User;
import com.buct.graduation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int register(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public User login(String email, String psw) {
        User user = userMapper.findUserByEmail(email);
        if(user == null)
            return null;
        if(psw.equals(user.getPsw()))
            return user;
        return null;
    }

    @Override
    public User findUserById(int id) {
        return userMapper.findUserById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userMapper.findUserByEmail(email);
    }

    @Override
    public List<User> findUsersByStatus(String status) {
        return userMapper.findUserByStatus(status);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }
}
