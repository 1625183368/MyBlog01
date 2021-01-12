package com.mxx.blogs.service;


import com.mxx.blogs.result.SystemResult;

import javax.servlet.http.HttpServletRequest;

public interface BLogsUserInfoService {
    SystemResult indexInfo(HttpServletRequest request);
}
