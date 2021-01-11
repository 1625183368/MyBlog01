package com.mxx.blogs.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BLogsArticleWithBLOBs extends BlogsArticle{
    @NotBlank(message = "文章内容不能为空")
    private String blogsContent;

    private String imageLists;
}
