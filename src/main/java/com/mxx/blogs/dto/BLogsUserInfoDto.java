package com.mxx.blogs.dto;

import com.mxx.blogs.pojo.BlogsUser;
import com.mxx.blogs.pojo.BlogsUserElseInfo;
import com.mxx.blogs.pojo.DtoExBlogsSkillValue;
import lombok.Data;

import java.util.List;

@Data
public class BLogsUserInfoDto {
    /**
     * 用户的主信息
     */
    private BlogsUser bLogsUser;

    /**
     * 用户的其他信息
     */
    private BlogsUserElseInfo bLogsUserElseInfo;

    /**
     * 用户掌握的技能
     *
     * @return
     */
    private List<DtoExBlogsSkillValue> skillvalues;
}
