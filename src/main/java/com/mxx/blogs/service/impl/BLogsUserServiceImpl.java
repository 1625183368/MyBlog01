package com.mxx.blogs.service.impl;

import com.mxx.blogs.appoint.BLogsUserServiceAppoint;
import com.mxx.blogs.service.BLogsUserService;
import com.mxx.blogs.utils.CookieUtils;
import com.mxx.blogs.contants.UserContants;
import com.mxx.blogs.dto.BLogsIndexDto;
import com.mxx.blogs.mapper.BlogsUserMapper;
import com.mxx.blogs.pojo.BlogsUser;
import com.mxx.blogs.result.SystemResult;
import com.mxx.blogs.utils.JsonUtils;
import com.mxx.blogs.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@SuppressWarnings(value = "all")
public class BLogsUserServiceImpl implements BLogsUserService {
    private static final Lock USER_REGISTER_LOCK = new ReentrantLock(true);

    @Autowired
    private BlogsUserMapper blogsUserMapper;
    @Autowired
    private BLogsUserServiceAppoint bLogsUserServiceAppoint;
    @Autowired
    @Qualifier("IRedis")
    private RedisTemplate redisTemplate;
    @Override
    public SystemResult login(String userName, String passWord, HttpServletRequest request, HttpServletResponse response) {
        SystemResult systemResult = bLogsUserServiceAppoint.checkUserInfo(userName, passWord);
        if (systemResult.getStatus() != 200){
            return systemResult;
        }
        BlogsUser user = blogsUserMapper.getUserByName(userName);
        if (user == null){
            return new SystemResult(100,"用户名或密码错误");
        }
        if (!user.getPassWord().equals(MD5Utils.md5(passWord))){
            return new SystemResult(100,"用户名或密码错误");
        }
        BLogsIndexDto dto = new BLogsIndexDto();
        BLogsUserServiceAppoint.commitUserInfo(dto,user);
        redisTemplate.opsForValue().set(UserContants.USER_KEY+userName,user);
        CookieUtils.setCookie(request,response, UserContants.USER_KEY, JsonUtils.objectToJson(dto),60*60*24*7);
        dto.setPassWord(null);
        return new SystemResult(200,"账号密码正确",dto);
    }

    @Override
    public SystemResult register(BlogsUser user) {
        USER_REGISTER_LOCK.lock();
        try{
            SystemResult systemResult = bLogsUserServiceAppoint.checkRegisterInfo(user);
            if(systemResult.getStatus()!= 200){
                return systemResult;
            }
            blogsUserMapper.insertIntoUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return new SystemResult(100, "网络不稳定。");
        }finally {
            USER_REGISTER_LOCK.unlock();
        }
        return new SystemResult(100);
    }

//    @Override
//    public SystemResult login(String userName, String passWord, HttpServletRequest request, HttpServletResponse response, Boolean isMd5) throws Exception {
//        return login(userName,passWord,request,response,true);
//    }
}
