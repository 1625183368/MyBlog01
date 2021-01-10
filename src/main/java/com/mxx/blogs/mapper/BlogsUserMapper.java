package com.mxx.blogs.mapper;

import com.mxx.blogs.pojo.BlogsUser;
import org.apache.ibatis.annotations.Param;

public interface BlogsUserMapper {

    BlogsUser getUserByName(@Param("username") String userName);
    void insertIntoUser(@Param("user") BlogsUser user);
}
