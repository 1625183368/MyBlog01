package com.mxx.blogs.mapper;


import com.mxx.blogs.pojo.BLogsArticleWithBLOBs;
import com.mxx.blogs.pojo.BlogsSource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogsSourceMapper {
    List<BlogsSource> getAll();

    void insertBlogs(@Param("a")BLogsArticleWithBLOBs article);
}
