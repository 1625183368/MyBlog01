package com.mxx.blogs.pojo;


import lombok.Data;

import java.util.Date;

@Data
public class BlogsUser {

  private long userId;
  private String userName;
  private String passWord;
  private String uEmail;
  private String uName;
  private String uImage;
  private Date createTime;
  private Date updateTime;
  private long state;
  private String retain;
  private String retain2;
  private String retain3;


}
