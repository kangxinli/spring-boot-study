package com.sample.async.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sample.async.task.AsyncTask;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskService {
	
	@Autowired
    private AsyncTask asyncTask;
	
	public void testAsync() {
		log.info("begin task....");
		
		// test();
		asyncTask.dealNoReturnTask();
		
		log.info("finish task !");
	}
	
	// 失效原因：因为@Async注解方法与控制类在同一个类中导致没有走代理类，所以@Async 注解失效。
	// 解决方案：在新建一个类，存放需要走异步的方法。如下所示：
	
	@Async
	public void test() {
		try {
            log.info("begin to deal other Task!");
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}

}
