package com.mxx.blogs.appoint;

import com.mxx.blogs.contants.ArticleContants;
import com.mxx.blogs.contants.UserContants;
import com.mxx.blogs.dto.OpenBLogsDto;
import com.mxx.blogs.locks.BLogsWriteLock;
import com.mxx.blogs.pojo.BlogsUser;
import com.mxx.blogs.result.SystemResult;
import com.mxx.blogs.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantReadWriteLock;


@Component
public class BLogsWriteAppoint {
    @Autowired
    @Qualifier("IRedis")
    private RedisTemplate redisTemplate;

    @Value("${USER_DEFAULT_IMAGE}")
    private String USER_DEFAULT_IMAGE;

    public SystemResult init(HttpServletRequest request) throws Exception {
        ReentrantReadWriteLock lock = BLogsWriteLock.getInstance().getLock();
        lock.writeLock().lock();
        try{
            /**
             * 过滤器
             * request.setAttribute("user",bLogsUser);
             */
            BlogsUser userInfo = (BlogsUser)request.getAttribute("user");
            OpenBLogsDto dto = new OpenBLogsDto();
            String logsToken = (String) redisTemplate.opsForValue().get(ArticleContants.USER_LOGS_KEY+userInfo.getUserName());
            //空就说明是第一次操作
            if(StringUtils.isEmpty(logsToken)){
                logsToken = userInitLogs(userInfo);
                dto.setToken(logsToken);
            }else{
                //如果有数据
                String contentToken = ArticleContants.GET_USER_LOGS_CONTENT + "#" + userInfo.getUserName() +"#" +logsToken;
                String logsContent = (String) redisTemplate.opsForValue().get(contentToken);
                dto.setToken(logsToken);
                dto.setContent(logsContent);
            }
            return new SystemResult(dto);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 用户第一次写文章 初始化
     * @param userInfo 用户信息
     * @return
     * @throws Exception
     */
    public String userInitLogs(BlogsUser userInfo)throws Exception{
        //加密 修改用户相应信息 下次就验证就不是第一次
        String newLogsToken = MD5Utils.md5(userInfo.getUserName()+ UUID.randomUUID().toString()+System.currentTimeMillis());

        redisTemplate.opsForValue().set(ArticleContants.USER_LOGS_KEY+userInfo.getUserName(),newLogsToken);
        return newLogsToken;
    }
}
