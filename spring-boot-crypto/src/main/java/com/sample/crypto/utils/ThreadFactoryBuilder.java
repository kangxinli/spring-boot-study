package com.sample.crypto.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 抄袭google-guava的factorybuilder
 * 
 * @author unascribed
 */
public final class ThreadFactoryBuilder {
	private String nameFormat = null;
	private boolean daemon = true;
	private UncaughtExceptionHandler uncaughtExceptionHandler = null;
	private ThreadFactory backingThreadFactory = null;

	private ThreadFactoryBuilder() {
	}

	public static ThreadFactoryBuilder create() {
		return new ThreadFactoryBuilder();
	}

	public ThreadFactoryBuilder setNameFormat(String nameFormat) {
		// String.format(nameFormat, Integer.valueOf(0));
		this.nameFormat = nameFormat;
		return this;
	}

	public ThreadFactoryBuilder setDaemon(boolean daemon) {
		this.daemon = daemon;
		return this;
	}

	public ThreadFactoryBuilder setUncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
		if (null == uncaughtExceptionHandler) {
			throw new NullPointerException();
		}
		this.uncaughtExceptionHandler = uncaughtExceptionHandler;
		return this;
	}

	public ThreadFactoryBuilder setThreadFactory(ThreadFactory backingThreadFactory) {
		if (null == backingThreadFactory) {
			throw new NullPointerException();
		}
		this.backingThreadFactory = backingThreadFactory;
		return this;
	}

	public ThreadFactory build() {
		return build(this);
	}

	private static ThreadFactory build(ThreadFactoryBuilder builder) {
		final String nameFormat = builder.nameFormat;
		final Boolean daemon = builder.daemon;
		final UncaughtExceptionHandler uncaughtExceptionHandler = builder.uncaughtExceptionHandler;
		final ThreadFactory backingThreadFactory = builder.backingThreadFactory != null ? builder.backingThreadFactory
				: Executors.defaultThreadFactory();
		final AtomicLong count = nameFormat != null ? new AtomicLong(0L) : null;
		return (runnable) -> {
			Thread thread = backingThreadFactory.newThread(runnable);
			if (nameFormat != null) {
				thread.setName(String.format(nameFormat, count.getAndIncrement()));
			}
			thread.setDaemon(daemon);
			if (uncaughtExceptionHandler != null) {
				thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
			}

			return thread;
		};
	}
}