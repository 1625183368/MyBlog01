package com.mxx.blogs.appoint;

import com.mxx.blogs.contants.ArticleContants;
import com.mxx.blogs.dto.BLogsSearchDto;
import com.mxx.blogs.dto.SearchTypeDto;
import com.mxx.blogs.enums.BLogsSearchTypeEnum;
import com.mxx.blogs.pojo.PojoExBLogsArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BLogsSearchAppoint {
    @Autowired
    @Qualifier("IRedis")
    private RedisTemplate redisTemplate;
    public void setCommonFiled(String query, BLogsSearchDto dto, List<PojoExBLogsArticle> bLogsArticles){
        dto.setQuery(query);
        dto.setArticles(bLogsArticles);

        // 创建一个List用来存放我们的Type
        List<SearchTypeDto> searchTypeDtos = null;

        // 去缓存中获取数据
        try {
            Object o = redisTemplate.opsForValue().get(ArticleContants.SEARCH_TYPE_KEY);
            if (o == null) {
                // 说明缓存中没有数据  没有数据的话 我们需要从类中获取。
                // 查询类型
                searchTypeDtos = new ArrayList<>();
                for (BLogsSearchTypeEnum value : BLogsSearchTypeEnum.values()) {
                    SearchTypeDto typeDto = new SearchTypeDto();
                    typeDto.setKey(value.getContent());
                    typeDto.setValue(value + "");
                    searchTypeDtos.add(typeDto);
                }

                // 查询完成后 存入缓存中
                redisTemplate.opsForValue().set(ArticleContants.SEARCH_TYPE_KEY, searchTypeDtos);
            } else {
                searchTypeDtos = (List<SearchTypeDto>) o;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        dto.setTypeDto(searchTypeDtos);
    }

}
