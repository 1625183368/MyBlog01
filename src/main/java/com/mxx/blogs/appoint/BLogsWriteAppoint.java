package com.mxx.blogs.appoint;

import com.mxx.blogs.contants.ArticleContants;

import com.mxx.blogs.dto.OpenBLogsDto;
import com.mxx.blogs.enums.BlogSysState;
import com.mxx.blogs.excep.CheckValueException;
import com.mxx.blogs.locks.BLogsWriteLock;
import com.mxx.blogs.pojo.BlogsUser;
import com.mxx.blogs.result.SystemResult;
import com.mxx.blogs.utils.MD5Utils;
import com.mxx.blogs.utils.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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


    public SystemResult saveDraft(HttpServletRequest request, String token, String content) {

        return null;
    }

    public SystemResult upload(HttpServletRequest request, MultipartFile file, String token) {
        ReentrantReadWriteLock lock = BLogsWriteLock.getInstance().getLock();
        lock.writeLock().lock();
        try{
            BlogsUser userInfo = (BlogsUser) request.getAttribute("user");
            checkHandleIsLoginUser(userInfo,token);

            StringBuffer filename = new StringBuffer();
            checkUploadFile(file,filename);

            // 存入到图片缓存中去
            imageAddressWriteCahce(userInfo,"");
            return new SystemResult(BlogSysState.SUCCESS.getVALUE(),
                    BlogSysState.SUCCESS.getKEY(), "");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
        }
        return null;
    }

    /**
     * 判断是否是本人操作
     * @param userInfo 用户信息
     * @param token 标志
     */
    public void checkHandleIsLoginUser(BlogsUser userInfo, String token) throws CheckValueException {
        //判断是否和缓存中是否有相应用户的文章
        String logsToken = (String) redisTemplate.opsForValue().get(ArticleContants.USER_LOGS_KEY+userInfo.getUserName());
        if (StringUtils.isEmpty(logsToken)){
            throw new CheckValueException("加载失败请重试");
        }
        //判断是否是本人的
        if (!token.equals(logsToken)){
            throw new CheckValueException("加载失败请重试");
        }
    }

    /**
     * 图片写入缓存
     * @param userInfo
     * @param imageAddress
     */
    public void imageAddressWriteCahce(BlogsUser userInfo, String imageAddress){
        //从缓存中取出用户图片
        List<String> images = (List<String>) redisTemplate.opsForValue().get(ArticleContants.IMAGE_CACHE_KEY+userInfo.getUserName());
        if (images==null||images.isEmpty()){
            images = new ArrayList<>();
        }
        //写入缓存
        images.add(imageAddress);
        redisTemplate.opsForValue().set(ArticleContants.IMAGE_CACHE_KEY,images);
    }

    /**
     * 校验图片
     * @param file
     * @param fileName
     * @throws CheckValueException
     * @throws IOException
     */
    public void checkUploadFile(MultipartFile file, StringBuffer fileName) throws CheckValueException, IOException {
        if (file == null){
            throw new CheckValueException("图片不能为空");
        }else if (file.getInputStream()==null){
            throw new CheckValueException("图片不能为空");
        }
        String fileNamefix = MD5Utils.md5("IMAGE_" + UUID.randomUUID().toString() +
                System.currentTimeMillis());

        String originalFilename = file.getOriginalFilename();
        String fileNameEnd = originalFilename.substring(originalFilename.lastIndexOf("."));
        fileName.append(fileNamefix).append(fileNameEnd);
    }
}
