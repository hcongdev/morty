package com.morty.controller;

import com.common.entity.Result;
import com.morty.shiro.ShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @ResponseBody
    @PostMapping("/checkLogin")
    public Result checkLogin(String username, String password,String captcha, HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try {
            //登陆成功的话，放到session中
            subject.login(usernamePasswordToken);
        }catch (UnknownAccountException e) {
            return Result.failure(e.getMessage());
        }catch (IncorrectCredentialsException e) {
            return Result.failure("账号或密码不正确");
        }catch (LockedAccountException e) {
            return Result.failure("账号已被锁定,请联系管理员");
        }catch (AuthenticationException e) {
            return Result.failure("账户验证失败");
        }
        return Result.success();
    }

}
