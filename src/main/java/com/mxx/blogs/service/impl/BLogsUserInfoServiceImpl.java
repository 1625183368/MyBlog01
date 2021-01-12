package com.mxx.blogs.service.impl;

import com.mxx.blogs.dto.BLogsUserInfoDto;
import com.mxx.blogs.enums.BlogSysState;
import com.mxx.blogs.mapper.UserInfoMapper;
import com.mxx.blogs.pojo.BlogsUser;
import com.mxx.blogs.result.SystemResult;
import com.mxx.blogs.service.BLogsUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class BLogsUserInfoServiceImpl implements BLogsUserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public SystemResult indexInfo(HttpServletRequest request) {
        Object user = request.getAttribute("user");
        if (user == null){
            return new SystemResult(BlogSysState.USER_NO_LOGIN.getVALUE(),BlogSysState.USER_NO_LOGIN.getKEY());
        }
        BLogsUserInfoDto dto = new BLogsUserInfoDto();
        BlogsUser userInfo = (BlogsUser) user;
        dto.setSkillvalues(userInfoMapper.getSkillValueByUserName(userInfo.getUserName()));
        dto.setBLogsUser(userInfo);
        dto.setBLogsUserElseInfo(userInfoMapper.getUserInfoByUserName(userInfo.getUserName()));


        return new SystemResult(BlogSysState.SUCCESS.getVALUE(),BlogSysState.SUCCESS.getKEY(),dto);
    }
}
