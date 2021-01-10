package com.mxx.blogs.controller;

import com.mxx.blogs.service.BLogsUserService;
import com.mxx.blogs.pojo.BlogsUser;
import com.mxx.blogs.result.SystemResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/logs/user")
public class BLogsUserController {
    @Autowired
    private BLogsUserService bLogsUserService;
    @RequestMapping("/login")
    public SystemResult login( String userName,String passWord, HttpServletRequest request, HttpServletResponse response){
        return bLogsUserService.login(userName, passWord,request,response);
    }

    @RequestMapping("/register")
    public SystemResult register(BlogsUser user){
        return bLogsUserService.register(user);
    }
}
