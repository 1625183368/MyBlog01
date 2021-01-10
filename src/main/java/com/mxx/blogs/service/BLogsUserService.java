package com.mxx.blogs.service;

import com.mxx.blogs.pojo.BlogsUser;
import com.mxx.blogs.result.SystemResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BLogsUserService {
    SystemResult login(String userName, String passWord, HttpServletRequest request, HttpServletResponse response);
    SystemResult register(BlogsUser user);
//    SystemResult login(String userName, String passWord, HttpServletRequest request, HttpServletResponse response
//            , Boolean isMd5) throws Exception;
}
