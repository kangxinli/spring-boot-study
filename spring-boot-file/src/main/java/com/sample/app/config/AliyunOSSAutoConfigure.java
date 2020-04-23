package com.sample.app.config;

import java.net.URL;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.sample.app.entity.FileInfo;
import com.sample.app.properties.FileServerProperties;
import com.sample.app.service.impl.AbstractIFileService;

/**
 * 阿里云配置
 *
 * @author 作者 owen E-mail: 624191343@qq.com
 */
@Configuration
@ConditionalOnProperty(name = "app.file-server.type", havingValue = "aliyun")
public class AliyunOSSAutoConfigure {
    @Autowired
    private FileServerProperties fileProperties;

    /**
     * 阿里云文件存储client
     * 只有配置了aliyun.oss.access-key才可以使用
     */
    @Bean
    public OSSClient ossClient() {
        OSSClient ossClient = new OSSClient(fileProperties.getOss().getEndpoint()
                , new DefaultCredentialProvider(fileProperties.getOss().getAccessKey(), fileProperties.getOss().getAccessKeySecret())
                , null);
        return ossClient;
    }

    @Service
    public class AliyunOssServiceImpl extends AbstractIFileService {
        @Autowired
        private OSSClient ossClient;

        @Override
        protected String fileType() {
            return fileProperties.getType();
        }

        @Override
        protected void uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception {
            ossClient.putObject(fileProperties.getOss().getBucketName(), 
            		fileProperties.getOss().getPath() + fileInfo.getName(), file.getInputStream());
            fileInfo.setUrl(getUrl(fileInfo.getName()));
        }

        @Override
        protected boolean deleteFile(FileInfo fileInfo) {
            ossClient.deleteObject(fileProperties.getOss().getBucketName(), fileInfo.getName());
            return true;
        }
        
        protected String getUrl(String filename) {
            // 设置URL过期时间为10年  3600l* 1000*24*365*10
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * fileProperties.getOss().getExpireYear());
            // 生成URL
            URL url = ossClient.generatePresignedUrl(fileProperties.getOss().getBucketName(), fileProperties.getOss().getPath() + filename, expiration);
            if (url != null) {
                return url.toString();
            }
            return null;
        }
    }
}
