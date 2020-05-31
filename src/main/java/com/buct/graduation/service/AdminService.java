package com.buct.graduation.service;

import com.buct.graduation.model.pojo.Admin;

import java.util.HashMap;

public interface AdminService {
    int register(Admin admin);

    Admin login(Admin admin);

    Admin updateInform(Admin admin);

    Admin findAdminByEmail(String email);

    int changePsw(Admin admin);

    HashMap<String, Integer> findAnalysisData();
}
