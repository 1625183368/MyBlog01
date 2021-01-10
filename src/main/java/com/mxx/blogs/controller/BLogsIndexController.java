package com.mxx.blogs.controller;

import com.mxx.blogs.service.BLogsIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/logs")
@Slf4j
public class BLogsIndexController {
    @Autowired
    private BLogsIndexService bLogsIndexService;
    @RequestMapping("/index")
    public String IndexBlog(HttpServletRequest request) throws Exception {
        request.setAttribute("result",bLogsIndexService.index(request));
        return "index";
    }
}
