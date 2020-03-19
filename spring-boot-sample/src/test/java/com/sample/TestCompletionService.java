package com.sample;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestCompletionService {
	// 线程池超时关闭控制
	private static final int TIMEOUT = 300000;

	// 单位统一为毫秒
	private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

	// CompletableFuture基本用法
	private static void asyncTask(int poolSize, List<String> strList) {
		// 1、创建线程池
		ExecutorService executor = Executors.newFixedThreadPool(poolSize);
		// 2、创建多线程任务执行器
		CompletionService<String> completionService = new ExecutorCompletionService<String>(executor);
		// 3、提交任务
		for (String str : strList) {
			completionService.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					return str.trim();
				}

			});
		}
		// 4、获取数据
		try {
			for (int taskCount = 0, size = strList.size(); taskCount < size; taskCount++) {
				System.out.println("future result: " + completionService.take().get());
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 5、关闭线程池
			try {
				executor.shutdownNow();
				executor.awaitTermination(TIMEOUT, UNIT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		String[] arr = new String[] { "aa", "bb " };
		asyncTask(2, Arrays.asList(arr));
	}
}
