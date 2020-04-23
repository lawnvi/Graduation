package com.buct.graduation.service.impl;

import com.buct.graduation.mapper.AdminMapper;
import com.buct.graduation.model.pojo.Admin;
import com.buct.graduation.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public int register(Admin admin) {
        return adminMapper.add(admin);
    }

    @Override
    public Admin login(Admin admin) {
        Admin user = adminMapper.findByEmail(admin.getEmail());
        if(user == null || !user.getPsw().equals(admin.getPsw())){
            return null;
        }
        return user;
    }

    @Override
    public Admin updateInform(Admin admin) {
        Admin user = adminMapper.findByEmail(admin.getEmail());
        if(user == null){
            return null;
        }
        adminMapper.update(admin);
        return adminMapper.findByEmail(admin.getEmail());
    }

    @Override
    public Admin findAdminByEmail(String email) {
        return adminMapper.findByEmail(email);
    }

    @Override
    public int changePsw(Admin admin) {
        return adminMapper.changePsw(admin);
    }
}
