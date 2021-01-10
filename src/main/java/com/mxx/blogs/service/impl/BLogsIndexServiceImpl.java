package com.mxx.blogs.service.impl;

import com.mxx.blogs.appoint.BLogsIndexAppoint;
import com.mxx.blogs.appoint.BLogsUserServiceAppoint;
import com.mxx.blogs.dto.BLogsIndexDto;
import com.mxx.blogs.pojo.BlogsUser;
import com.mxx.blogs.result.SystemResult;
import com.mxx.blogs.service.BLogsIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class BLogsIndexServiceImpl implements BLogsIndexService {
    @Autowired
    private BLogsIndexAppoint bLogsIndexAppoint;

    @Override
    public SystemResult index(HttpServletRequest request) throws Exception {
        Object user = request.getAttribute("user");
        System.out.println(user);
        BLogsIndexDto dto = new BLogsIndexDto();
        if (user == null){
            dto.setLogin(false);
        }else{
            BlogsUser blogsUser = (BlogsUser)user;
            BLogsUserServiceAppoint.commitUserInfo(dto,blogsUser);
            dto.setLogin(true);
        }
        bLogsIndexAppoint.getArticle(dto);
        return new SystemResult(dto);
    }
}
