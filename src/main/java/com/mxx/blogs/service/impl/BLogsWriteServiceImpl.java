package com.mxx.blogs.service.impl;


import com.mxx.blogs.appoint.BLogsWriteAppoint;
import com.mxx.blogs.contants.ArticleContants;
import com.mxx.blogs.enums.BlogSysState;
import com.mxx.blogs.locks.BLogsWriteLock;
import com.mxx.blogs.mapper.BlogsSourceMapper;
import com.mxx.blogs.pojo.BLogsArticleWithBLOBs;
import com.mxx.blogs.pojo.BlogsSource;
import com.mxx.blogs.pojo.BlogsUser;
import com.mxx.blogs.result.SystemResult;
import com.mxx.blogs.service.BLogsWriteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class BLogsWriteServiceImpl implements BLogsWriteService {

    @Autowired
    @Qualifier("IRedis")
    private RedisTemplate redisTemplate;
    @Autowired
    private BLogsWriteAppoint bLogsWriteAppoint;
    @Autowired
    private BlogsSourceMapper blogsSourceMapper;
    @Override
    public List<BlogsSource> initConfirmRelease() throws Exception {
        synchronized (this){
            List<BlogsSource> sources = (List<BlogsSource>) redisTemplate.opsForValue().get(ArticleContants.SOURCE_KEY);
            if (sources==null||sources.isEmpty()){
                //查询数据库 写入缓存
                sources = blogsSourceMapper.getAll();
                redisTemplate.opsForValue().set(ArticleContants.SOURCE_KEY,sources);
            }
            return sources;
        }

    }

    @Override
    public SystemResult writeLogs(BLogsArticleWithBLOBs article, String token, HttpServletRequest request) throws Exception {
        ReentrantReadWriteLock lock = BLogsWriteLock.getInstance().getLock();
        lock.writeLock().lock();
        try {
            // 获取用户的信息
            BlogsUser userInfo = (BlogsUser) request.getAttribute("user");
            bLogsWriteAppoint.checkHandleIsLoginUser(userInfo, token);
            bLogsWriteAppoint.beforeCreateMySqlProceeeor(article, userInfo);
            blogsSourceMapper.insertBlogs(article);
            bLogsWriteAppoint.deleteSuccess(userInfo, token);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
        }
        return new SystemResult(BlogSysState.SUCCESS);
    }
}
