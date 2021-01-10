package com.mxx.blogs.service;

import com.mxx.blogs.result.SystemResult;

import javax.servlet.http.HttpServletRequest;

public interface BLogsIndexService {
    SystemResult index(HttpServletRequest request) throws Exception;
}
