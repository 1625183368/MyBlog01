package com.mxx.blogs.controller;

import com.mxx.blogs.appoint.BLogsWriteAppoint;
import com.mxx.blogs.pojo.BLogsArticleWithBLOBs;
import com.mxx.blogs.result.SystemResult;
import com.mxx.blogs.service.BLogsWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * acl/open/write
 */

@Controller
@RequestMapping("/acl")
public class BLogsWriteArticleController {

    @Autowired
    private BLogsWriteAppoint bLogsWriteAppoint;
    @Autowired
    private BLogsWriteService bLogsWriteService;

    @RequestMapping("/open/write")
    public String openWrite(HttpServletRequest request) throws Exception {

        request.setAttribute("result", bLogsWriteAppoint.init(request));
        return "writeBlogs/writeBlogs";
    }

    @RequestMapping("/save/draft")
    @ResponseBody
    public SystemResult draft(HttpServletRequest request,
                              @NotBlank(message = "加载失败,请重新打开页面") String token,
                              @NotBlank(message = "请填写内容") String content) throws Exception {

        SystemResult systemResult = bLogsWriteAppoint.saveDraft(request, token, content);
        return systemResult;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public SystemResult upload(HttpServletRequest request, MultipartFile file,
                               @NotBlank(message = "加载失败,请重新打开页面") String token) throws Exception {

        // 调用上传
        SystemResult upload = bLogsWriteAppoint.upload(request, file, token);
        return upload;
    }

    @RequestMapping("/write/logs")
    @ResponseBody
    public SystemResult writeLogs(HttpServletRequest request,
                                  @NotBlank(message = "加载失败,请重新打开页面") String token,
                                  @Validated BLogsArticleWithBLOBs bLogsArticle) throws Exception {
        SystemResult systemResult = bLogsWriteService.writeLogs((bLogsArticle), token, request);
        return systemResult;
    }

    @RequestMapping("/confirm/release")
    public String confirmRelease(HttpServletRequest request,
                                 @NotBlank(message = "加载失败,请重新打开页面") String token) throws Exception {
        request.setAttribute("result", bLogsWriteService.initConfirmRelease());
        request.setAttribute("token", token);
        return "writeBlogs/ConfirmRelease";
    }
}
