package com.mxx.blogs.mapper;

import com.mxx.blogs.BlogsSource;
import com.mxx.blogs.pojo.BLogsArticleWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogsSourceMapper {
    List<BlogsSource> getAll();

    void insert(@Param("a")BLogsArticleWithBLOBs article);
}
