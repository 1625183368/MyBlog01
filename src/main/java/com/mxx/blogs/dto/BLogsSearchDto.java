package com.mxx.blogs.dto;

import com.mxx.blogs.pojo.PojoExBLogsArticle;
import lombok.Data;

import java.util.List;
@Data
public class BLogsSearchDto {// 查询的条件
    private String query;

    // 查询的类型
    private List<SearchTypeDto> typeDto;

    // 文章的信息
    private List<PojoExBLogsArticle> articles;
}
