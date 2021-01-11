package com.mxx.blogs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
/**
 * acl/open/write
 */

@Controller
@RequestMapping("/acl")
public class BLogsWriteArticleController {


    @RequestMapping("/open/write")
    public String openWrite(HttpServletRequest request){
        request.setAttribute("result",null);
        return "writeBlogs/writeBlogs";
    }
}
