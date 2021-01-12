package com.mxx.blogs.service;

import com.mxx.blogs.result.SystemResult;
import org.apache.ibatis.annotations.Select;

public interface BLogsSearchService {

    SystemResult getByQuery(String query);
}
