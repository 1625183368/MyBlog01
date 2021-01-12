package com.mxx.blogs.mapper;

import com.mxx.blogs.pojo.PojoExBLogsArticle;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BlogsSearchMapper {

    List<PojoExBLogsArticle> get(@Param("query") String query);
}
