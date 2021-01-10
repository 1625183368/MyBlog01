package com.mxx.blogs.pojo;


import lombok.Data;

import java.util.Date;

@Data
public class BlogsArticle {

  private long blogsId;
  private String showImage;
  private String authorNumber;
  private String blogsTitle;
  private String blogsContent;
  private String imageLists;
  private long ifPublic;
  private Date createTime;
  private Date updateTime;
  private long blogsSource;
  private String readSize;
  private String like;
  private String retain1;
  private String retain2;
  private String retain3;


}
