package com.mxx.blogs.service.impl;

import com.mxx.blogs.appoint.BLogsSearchAppoint;
import com.mxx.blogs.dto.BLogsSearchDto;
import com.mxx.blogs.mapper.BlogsSearchMapper;
import com.mxx.blogs.pojo.PojoExBLogsArticle;
import com.mxx.blogs.result.SystemResult;
import com.mxx.blogs.service.BLogsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BLogsSearchServiceImpl implements BLogsSearchService {

    @Autowired
    private BlogsSearchMapper blogsSearchMapper;
    @Autowired
    private BLogsSearchAppoint bLogsSearchAppoint;
    @Override
    public SystemResult getByQuery(String query) {
        if (StringUtils.isEmpty(query)){
            query = "";
        }
        BLogsSearchDto bLogsSearchDto = new BLogsSearchDto();
        List<PojoExBLogsArticle> pojoExBLogsArticles = blogsSearchMapper.get(query);
        bLogsSearchAppoint.setCommonFiled(query, bLogsSearchDto, pojoExBLogsArticles);
        return new SystemResult(bLogsSearchDto);
    }
}
