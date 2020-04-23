package com.sample.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.sample.app.properties.FileServerProperties;

/**
 * 文件上传（OSS文件服务器）
 *
 */
@EnableConfigurationProperties(FileServerProperties.class)
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		application.run(args);
	}
}