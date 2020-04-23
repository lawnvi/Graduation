package com.buct.graduation.controller;

import com.buct.graduation.model.pojo.Admin;
import com.buct.graduation.model.pojo.User;
import com.buct.graduation.model.pojo.recruit.Station;
import com.buct.graduation.service.AdminService;
import com.buct.graduation.service.StationService;
import com.buct.graduation.util.EmailUtil;
import com.buct.graduation.util.GlobalName;
import com.buct.graduation.util.Utils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/adminUser")
public class AdminController {
    @Autowired
    private AdminService adminService;

    //todo information update

    @RequestMapping("/index")
    public String index(HttpServletRequest request, Model model){
        Admin admin = Utils.getAdmin(request);
        model.addAttribute("user", admin);
        return "/admin/index";
    }

    /**
     * 注册
     */
    @RequestMapping("/register")
    public String register(){
        return "/admin/register";
    }
    //验证码

    @RequestMapping("/register.code")
    @ResponseBody
    public String getCode(HttpServletRequest request){
        String email = request.getParameter("email");
        if(adminService.findAdminByEmail(email) != null){
            return "existed";
        }
        String code = Utils.getCode();
        //加上admin前缀，放置user混淆
        if(EmailUtil.sendMail(email, EmailUtil.register(code))){
            Utils.saveSession(GlobalName.type_admin+email, code, request);
            return GlobalName.success;
        }
        return GlobalName.fail;
    }

    @RequestMapping("/register.do")
    @ResponseBody
    public String registerMethod(HttpServletRequest request){
        String code = request.getParameter("checkCode");
        String email = request.getParameter("email");
        String psw = request.getParameter("psw");
        //验证码检验
        if(!code.equals(Utils.getSession(request, GlobalName.type_admin+email))){
            System.out.println(Utils.getSession(request, GlobalName.type_admin+email));
            System.out.println(code);
            return "codeError";
        }
        Admin user = new Admin();
        user.setEmail(email);
        user.setPsw(psw);
        if(adminService.register(user) <= 0)
            return GlobalName.fail;
        Utils.removeSession(request, GlobalName.type_admin+email);
        return GlobalName.success;
    }

    /**
     * 找回密码
     */
    @RequestMapping("/resetPsw")
    public String resetPsw(){
        return "/admin/recovery_password";
    }
    //验证码
    @RequestMapping("/resetPsw.code")
    @ResponseBody
    public String getCode2ResetPsw(HttpServletRequest request){
        String email = request.getParameter("email");
        if(adminService.findAdminByEmail(email) == null){
            return "!existed";
        }
        String code = Utils.getCode();
        if(EmailUtil.sendMail(email, EmailUtil.resetPsw(code))){
            Utils.saveSession(GlobalName.type_admin+email, code, request);
            return GlobalName.success;
        }
        return GlobalName.fail;
    }
    @RequestMapping("/resetPsw.do")
    @ResponseBody
    public String resetPswMethod(HttpServletRequest request){
        String code = request.getParameter("checkCode");
        String email = request.getParameter("email");
        String psw = request.getParameter("psw");
        //验证码检验
        if(!code.equals(Utils.getSession(request, GlobalName.type_admin+email))){
            return GlobalName.fail;
        }
        if(psw.length() < 6){
            return GlobalName.fail;
        }
        Admin user = adminService.findAdminByEmail(email);
        user.setPsw(psw);
        adminService.changePsw(user);
        Utils.removeSession(request, GlobalName.type_admin+email);
        return GlobalName.success;
    }

    /**
     * 登录
     */
    @RequestMapping("/login")
    public String login(){
        return "/admin/login";
    }

    @RequestMapping("/login.do")
    @ResponseBody
    public String loginMethod(HttpServletRequest request){
        String email = request.getParameter("email");
        String psw = request.getParameter("psw");
        if(email == null || email.equals("") || psw == null || psw.equals("")){
            return GlobalName.fail;
        }
        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setPsw(psw);
        Admin user = adminService.login(admin);
        if(user == null){
            return GlobalName.fail;
        }
        //Utils.saveSession(GlobalName.session_userId, user.getId()+"", request);
        HttpSession session = request.getSession();
        user.setPsw("");
        session.setAttribute(GlobalName.session_admin, user);
        //时限600s
        session.setMaxInactiveInterval(600);
        return GlobalName.success;
    }

    @RequestMapping("/logout")
    public String logoutMethod(HttpServletRequest request){
        Utils.removeSession(request, GlobalName.session_admin);
        //todo 去首页
        return "redirect: ../index";
    }

    //todo 招聘/用户等管理
}
