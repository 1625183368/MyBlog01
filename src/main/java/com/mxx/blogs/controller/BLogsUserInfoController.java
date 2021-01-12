package com.mxx.blogs.controller;

import com.mxx.blogs.result.SystemResult;
import com.mxx.blogs.service.BLogsUserInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/acl/my/info")
public class BLogsUserInfoController {
    @Autowired
    private BLogsUserInfoService bLogsUserInfoService;
    @RequestMapping("index")
    public String MyInfo(HttpServletRequest request){
        request.setAttribute("result",bLogsUserInfoService.indexInfo(request));
        return "MyInfo/index";
    }
}
