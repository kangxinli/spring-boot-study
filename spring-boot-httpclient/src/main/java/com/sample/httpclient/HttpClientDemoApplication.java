package com.sample.httpclient;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HttpClientDemoApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(HttpClientDemoApplication.class, args);
	}

	/**
	 * 对上传文件的配置
	 *
	 * @return MultipartConfigElement配置实例
	 * @date 2018年6月29日 上午10:55:02
	 */
	@Bean
	public MultipartConfigElement multipartConfig() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// 设置单个附件大小上限值(默认为1M)
		//选择字符串作为参数的话，单位可以用MB、KB;
		// factory.setMaxFileSize("200MB");
		// 设置所有附件的总大小上限值
		// factory.setMaxRequestSize("1024MB");
		return factory.createMultipartConfig();
	}

}
