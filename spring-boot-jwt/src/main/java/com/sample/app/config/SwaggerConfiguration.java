package com.sample.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	/**
	 * swagger 信息
	 *
	 * @return 页面信息
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("接口 API")
				.description("接口描述 API")
				.termsOfServiceUrl("")
				.version("1.0.0")
				.contact(new Contact("", "", ""))
				.build();
	}

	/**
	 * 创建API
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				// 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
				.apiInfo(apiInfo())
				// 设置哪些接口暴露给Swagger展示
				.select()
				// 扫描所有有注解的api，用这种方式更灵活
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				// 扫描指定包中的swagger注解
				// .apis(RequestHandlerSelectors.basePackage("com.sample"))
				// 扫描所有 .apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}
}
