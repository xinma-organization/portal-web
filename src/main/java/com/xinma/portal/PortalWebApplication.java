package com.xinma.portal;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

@SpringBootApplication
public class PortalWebApplication implements ApplicationListener<ApplicationEvent> {

	Logger logger = LoggerFactory.getLogger(PortalWebApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PortalWebApplication.class, args);
	}

	@PreDestroy
	public void preDestroy() {
		// TODO
		logger.info("我被销毁了、、、、、我是用的@PreDestory的方式、、、、、、");
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		// 在这里可以监听到Spring Boot的生命周期

		if (event instanceof ApplicationEnvironmentPreparedEvent) {
			// 初始化环境变量
			logger.info("-----------初始化环境变量-----------");

		} else if (event instanceof ApplicationPreparedEvent) {
			// 初始化完成
			logger.info("-----------初始化完成-----------");
		} else if (event instanceof ApplicationReadyEvent) {
			// 应用已启动完成
			logger.info("-----------应用已启动完成-----------");
		} else if (event instanceof ApplicationFailedEvent) {
			// 应用启动失败
			logger.info("-----------应用启动失败-----------");
		} else if (event instanceof ContextRefreshedEvent) {
			// 应用上下文刷新，注意：多个上下文，事件会进入多次
			logger.info("-----------应用上下文刷新-----------");
		} else if (event instanceof ContextStartedEvent) {
			// 应用启动，需要在代码动态添加监听器才可捕获
			logger.info("-----------应用启动，需要在代码动态添加监听器才可捕获-----------");
		} else if (event instanceof ContextStoppedEvent) {
			// 应用停止
			logger.info("-----------应用上下文停止-----------");
		} else if (event instanceof ContextClosedEvent) {
			// 应用上下文关闭
			logger.info("-----------应用上下文关闭-----------");
		} else {
			logger.info("-----------其他应用类型事件，事件类名为{}-----------", event.getClass().getName());
		}
	}
}
