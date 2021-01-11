package com.mxx.blogs.service;


import com.mxx.blogs.pojo.BLogsArticleWithBLOBs;
import com.mxx.blogs.pojo.BlogsSource;
import com.mxx.blogs.result.SystemResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BLogsWriteService {
    /**
     * 初始化确认提交的信息
     */
    List<BlogsSource> initConfirmRelease() throws Exception;

    /**
     * 写博客
     * @param article
     * @param token
     * @param request
     * @return
     * @throws Exception
     */
    SystemResult writeLogs(BLogsArticleWithBLOBs article, String token, HttpServletRequest request)
            throws Exception;
}
