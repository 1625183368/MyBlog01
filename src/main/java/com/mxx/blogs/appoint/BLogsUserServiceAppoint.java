package com.mxx.blogs.appoint;

import com.mxx.blogs.contants.RegisterContants;
import com.mxx.blogs.dto.BLogsIndexDto;
import com.mxx.blogs.mapper.BlogsUserMapper;
import com.mxx.blogs.pojo.BlogsUser;
import com.mxx.blogs.result.SystemResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.util.StringUtils;

import java.util.Date;

@Component
@SuppressWarnings(value = "all")
public class BLogsUserServiceAppoint {
    @Autowired
    private BlogsUserMapper blogsUserMapper;

    public SystemResult checkUserInfo(String userName, String passWord) {

        // 校验用户名
        if (StringUtils.isEmpty(userName) || userName.length() < 7 || userName.length() > 11) {
            return new SystemResult(100, "用户名的长度为7~11位", null);
        }

        // 校验密码
        if (StringUtils.isEmpty(passWord) || passWord.length() < 7) {
            return new SystemResult(100, "密码的长度为7~15位", null);
        }
        return new SystemResult(200);

    }

    public static void commitUserInfo(BLogsIndexDto dto, BlogsUser user) {
        dto.setUName(user.getUserName());
        dto.setUserName(user.getUserName());
        dto.setUImage(user.getUImage());
    }

    /**
     * uName
     * Username
     * Password
     *
     * @return
     */
    public SystemResult checkRegisterInfo(BlogsUser user) {
        if (user == null) {
            return new SystemResult(100, "请填写表单信息");
        }
        SystemResult checkUserResult = checkUserInfo(user.getUserName(), user.getPassWord());
        if (checkUserResult.getStatus() != 200) {
            return checkUserResult;
        }
        if (StringUtils.isEmpty(user.getUName()) || user.getUName().length() > 5 || user.getUName().length() < 1) {
            return new SystemResult(100, "名字在1到5字符");
        }
        BlogsUser checkUserName = blogsUserMapper.getUserByName(user.getUserName());
        if (checkUserName != null) {
            return new SystemResult(100, "账号已存在");
        }
        user.setCreateTime(new Date());
        user.setUImage("./public/images/github.png");
        user.setState(RegisterContants.SUCCESS_REGISTER);

        return new SystemResult(200);
    }


}
