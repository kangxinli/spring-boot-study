package com.sample.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElkTestApplicationTests {

    private final static Logger log = LoggerFactory.getLogger(ElkTestApplicationTests.class);
    @Test
    public void test() {
        log.info("测试 info 成功了！！！");
        log.warn("测试 warn 成功了！！！");
        log.error("测试 error 成功了！！");
    }
    
}
