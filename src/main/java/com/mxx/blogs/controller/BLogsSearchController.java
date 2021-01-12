package com.mxx.blogs.controller;

import com.mxx.blogs.service.BLogsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/logs/search")
public class BLogsSearchController {
    @Autowired
    private BLogsSearchService bLogsSearchService;
    @RequestMapping("/index")
    public String index(String query, HttpServletRequest request) {

        // 存入Request
        request.setAttribute("result", bLogsSearchService.getByQuery(query));
        return "search";
    }
}