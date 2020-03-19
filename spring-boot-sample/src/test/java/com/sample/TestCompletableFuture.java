package com.sample;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TestCompletableFuture {

	// CompletableFuture基本用法
	private static Future<String> asyncTask(String input) {
		// CompletableFuture包装返回结果
		CompletableFuture<String> future = new CompletableFuture<String>();
		new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			System.out.println("async task input value: " + input);
			try {
				String ret = input.substring(1);
				future.complete(ret);
			} catch (Exception ex) {
				// 封装异常
				future.completeExceptionally(ex);
			}
		}).start();
		return future;
	}

	// 使用工厂创建CompletableFuture
	private static Future<String> asyncTask(int input) {
		return CompletableFuture.supplyAsync(() -> {
			return String.valueOf(20 / input);
		});
	}

	// 异步处理流水线
	private static List<String> asyncTaskPipeline(List<String> strList) {
		final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
				new ThreadFactory() {
					public Thread newThread(Runnable r) {
						Thread t = new Thread(r);
						t.setDaemon(true); // 设置为守护线程，这种方式不会阻止程序的关停
						return t;
					}
				});
		List<CompletableFuture<String>> retFuture = strList.stream()
				.map(str -> CompletableFuture.supplyAsync(() -> str.trim(), executor))
				.map(future -> future.thenApply(String::trim))
				.map(future -> future.thenCompose(str -> CompletableFuture.supplyAsync(() -> str.trim(), executor)))
				.collect(Collectors.toList());
		return retFuture.stream().map(CompletableFuture::join) // 等待流中Future执行完毕，提取各自返回值
				.collect(Collectors.toList());
	}

	public static void main(String[] args) {
		String input = " trim";
		System.out.println("async task start ...");
		Future<String> future = asyncTask(input);
		// 出现异常场景 验证
		Future<String> futureInt = asyncTask(0);
		// do something else
		System.out.println("do something else...");
		try {
			// 也可 CompletableFuture.allOf(futuresArr).join() 等待所有与future任务结束
			String str = future.get();
			System.out.println("async task result: " + str);
			System.out.println("async task result: " + futureInt.get());
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		String[] arr = new String[] { "aa", "bb " };
		System.out.println(asyncTaskPipeline(Arrays.asList(arr)));
	}

}
