package com.sample.async;

import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sample.async.task.AsyncTask;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class AsyncTest {

    @Autowired
    private AsyncTask asyncTask;

    @Test
    public void testDealNoReturnTask(){
        asyncTask.dealNoReturnTask();
        try {
            log.info("begin to deal other Task!");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testDealHaveReturnTask() throws Exception {

        Future<String> future = asyncTask.dealHaveReturnTask();
        log.info("begin to deal other Task!");
        while (true) {
            if(future.isCancelled()){
                log.info("deal async task is Cancelled");
                break;
            }
            if (future.isDone() ) {
                log.info("deal async task is Done");
                log.info("return result is " + future.get());
                break;
            }
            log.info("wait async task to end ...");
            Thread.sleep(1000);
        }
    }
}
