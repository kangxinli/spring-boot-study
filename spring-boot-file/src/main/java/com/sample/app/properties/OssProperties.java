package com.sample.app.properties;

import lombok.Getter;
import lombok.Setter;

/**
 */
@Setter
@Getter
public class OssProperties {
    /**
     * 密钥key
     */
    private String accessKey;
    /**
     * 密钥密码
     */
    private String accessKeySecret;
    /**
     * 端点
     */
    private String endpoint;
    /**
     * bucket名称
     */
    private String bucketName;
    /**
     * 说明
     */
    private String domain;
    /**
     * 路径
     */
    private String path;
    
    /**
     * 过期年限
     */
    private Integer expireYear;
}
