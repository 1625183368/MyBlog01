package com.mxx.blogs.appoint;

import com.mxx.blogs.pojo.BlogsArticle;
import com.mxx.blogs.contants.ArticleContants;
import com.mxx.blogs.dto.BLogsIndexDto;
import com.mxx.blogs.enums.BlogSysState;
import com.mxx.blogs.mapper.ArticleMapper;
import com.mxx.blogs.result.SystemResult;
import com.mxx.blogs.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component

public class BLogsIndexAppoint {
    @Autowired
    private ArticleMapper articleMapper;
    public static SystemResult checkCookieUser(String cookieValue, HttpServletRequest request) throws Exception {

        //查cookie
        if (StringUtils.isEmpty(cookieValue)) {
            return new SystemResult(BlogSysState.USER_NO_LOGIN.getVALUE(), "用户未登录");
        }
        BLogsIndexDto userInfoDto = null;
        try {
            userInfoDto = JsonUtils.jsonToPojo(cookieValue, BLogsIndexDto.class);
        } catch (Exception e) {
            return new SystemResult(BlogSysState.USER_EDIT_COOKIE.getVALUE(), "cookie被修改");
        }

        //未登录
        if (userInfoDto == null) {
            return new SystemResult(BlogSysState.USER_NO_LOGIN.getVALUE(), "用户未登录");
        }
        return new SystemResult(BlogSysState.SUCCESS.getVALUE(), BlogSysState.SUCCESS.getKEY(),userInfoDto);
    }
    public void getArticle(BLogsIndexDto indexDto)throws Exception{
        // 加锁 保证当前只有一份数据存入缓存中。redis setnx
        synchronized (ArticleContants.ART_CACHE_KEY){
            //查询缓存数据


            //查询数据库数据
            List<BlogsArticle> articles = articleMapper.getArticles();
            indexDto.setArticles(articles);
        }
    }

}
