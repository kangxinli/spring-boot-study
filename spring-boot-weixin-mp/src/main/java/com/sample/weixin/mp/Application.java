package com.sample.weixin.mp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 微信公众号后端开发demo
 * github: 参考https://github.com/binarywang/weixin-java-mp-demo-springboot
 * 
 * 步骤：
 * 运行Java程序：`Application`
 * 配置微信公众号中的接口地址：http://公网可访问域名/wx/portal/xxxxx （注意，xxxxx为对应公众号的appid值）；
 * 根据自己需要修改各个handler的实现，加入自己的业务逻辑。
 *
 */
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		application.run(args);
	}
}