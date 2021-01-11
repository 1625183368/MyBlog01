package com.mxx.blogs.service.impl;

import com.mxx.blogs.BlogsSource;
import com.mxx.blogs.appoint.BLogsWriteAppoint;
import com.mxx.blogs.contants.ArticleContants;
import com.mxx.blogs.pojo.BLogsArticleWithBLOBs;
import com.mxx.blogs.result.SystemResult;
import com.mxx.blogs.service.BLogsWriteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class BLogsWriteServiceImpl implements BLogsWriteService {

    @Autowired
    @Qualifier("IRedis")
    private RedisTemplate redisTemplate;
    @Autowired
    private BLogsWriteAppoint bLogsWriteAppoint;

    @Override
    public List<BlogsSource> initConfirmRelease() throws Exception {
        synchronized (this){
            List<BlogsSource> sources = (List<BlogsSource>) redisTemplate.opsForValue().get(ArticleContants.SOURCE_KEY);
            if (sources==null||sources.isEmpty()){

            }
        }

        return null;
    }

    @Override
    public SystemResult writeLogs(BLogsArticleWithBLOBs article, String token, HttpServletRequest request) throws Exception {
        return null;
    }
}
