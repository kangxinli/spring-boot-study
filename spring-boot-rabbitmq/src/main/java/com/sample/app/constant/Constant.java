package com.sample.app.constant;

public class Constant {
	
	/**
	 * rabbitmq 六种模式
	 * 1： 简单模式 simple
	 * 2： 工作模式 work
	 * 3： 发布订阅模式 publish/subscribe
	 * 4： 路由模式 routing
	 * 5： 主题模式(路由模式的一种) topic
	 * 6： RPC
	 */
	
	public final static String QUEUE_NAME = "spring-boot-queue";
	
	public static final String QUEUE_NAME_1 = "hello";
	
	public static final String QUEUE_NAME_2 = "neo";
	
	public static final String QUEUE_NAME_3_A = "fanout.A";
	
	public static final String QUEUE_NAME_3_B = "fanout.B";
	
	public static final String QUEUE_NAME_3_C = "fanout.C";
	
	public static final String QUEUE_NAME_5_A = "topic.A";
	
	public static final String QUEUE_NAME_5_B = "topic.B";
	
	
	public static final String EXCHANGE_NAME_FANOUT = "fanoutExchange";
	public final static String EXCHANGE_NAME_TOPIC = "topicExchange";
	
	
    
    public final static String ROUTING_KEY = "spring-boot-key";

}
