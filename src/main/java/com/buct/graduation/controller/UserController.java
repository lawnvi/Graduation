package com.buct.graduation.controller;

import com.buct.graduation.model.pojo.User;
import com.buct.graduation.service.UserService;
import com.buct.graduation.util.EmailUtil;
import com.buct.graduation.util.GlobalName;
import com.buct.graduation.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册流程，注册页面，邮箱验证码，注册
     * @return
     */
    @RequestMapping("/register")
    public String register(){
        return "/user/register";
    }
    //验证码
    @RequestMapping("/register.code")
    @ResponseBody
    public String getCode(HttpServletRequest request){
        String email = request.getParameter("email");
        if(userService.findUserByEmail(email) != null){
            return "existed";
        }
        String code = Utils.getCode();
        if(EmailUtil.sendMail(email, EmailUtil.register(code))){
            Utils.saveSession(email, code, request);
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
        if(!code.equals(Utils.getSession(request, email))){
            return GlobalName.fail;
        }
        User user = new User();
        user.setEmail(email);
        user.setPsw(psw);
        userService.register(user);
        Utils.removeSession(request, email);
        return GlobalName.success;
    }


    /**
     * 登录相关
     */
    @RequestMapping("/login")
    public String login(){
        return "/user/login";
    }

    @RequestMapping("/login.do")
    @ResponseBody
    public String loginMethod(HttpServletRequest request){
        String email = request.getParameter("email");
        String psw = request.getParameter("psw");
        User user = userService.login(email, psw);
        if(user == null){
            return GlobalName.fail;
        }
        Utils.saveSession(GlobalName.session_userId, user.getId()+"", request);
        return GlobalName.success;
    }
}
