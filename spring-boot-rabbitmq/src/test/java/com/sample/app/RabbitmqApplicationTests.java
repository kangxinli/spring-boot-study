package com.sample.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.app.demo1.HelloSender;
import com.sample.app.demo2.NeoSender;
import com.sample.app.demo3.FanoutSender;
import com.sample.app.demo5.TopicSend;
import com.sample.app.msgconfim.sender.ConfirmSender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqApplicationTests {
	@Autowired
	private HelloSender helloSender;

	@Autowired
	private NeoSender neoSender;
	
	@Autowired
    private FanoutSender fanoutSender;
	
	@Autowired
    private TopicSend sender;
	
	@Autowired
    private ConfirmSender confirmSender;
	
	/**
	 * 简单模式
	 */
	@Test
	public void hello() throws Exception {
		helloSender.send();
	}

	/**
	 * 工作模式
	 */
	@Test
	public void oneToMany() throws Exception {
		for (int i = 0; i < 100; i++) {
			neoSender.send(i);
		}
	}
	
	/**
	 * 发布订阅
	 */
	@Test
    public void setFanoutSender(){
        fanoutSender.send();
    }
	
	/**
	 * topic 模式
	 */
	@Test
    public void topic() throws Exception {
        sender.send();
    }

    @Test
    public void topic1() throws Exception {
        sender.send1();
    }

    @Test
    public void topic2() throws Exception {
        sender.send2();
    }
    // topic 模式   end =======
    
    
    // 消息确认
    @Test
    public void confirm() {
        confirmSender.send();
    }
}
