package com.mxx.blogs.mapper;

import com.mxx.blogs.pojo.BlogsUserElseInfo;
import com.mxx.blogs.pojo.DtoExBlogsSkillValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper {
    BlogsUserElseInfo getUserInfoByUserName(@Param("username") String username);
    List<DtoExBlogsSkillValue> getSkillValueByUserName(@Param("username")String username);
}
