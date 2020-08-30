package com.me.work.greeting.api;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.me.work.greeting.api.mapper.GreetingMapper;
import com.me.work.greeting.api.mapper.GreetingMapperImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ComponentScan(basePackages = { "com.me.work.greeting.api" })
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		
		new SpringApplicationBuilder(Application.class)
			.listeners(event -> log.debug(" > " + event.getClass().getCanonicalName()))
			.bannerMode(Mode.CONSOLE)
			.run(args);
	}
	
	@Bean
	public GreetingMapper greetingMapper() {
		return new GreetingMapperImpl();
	}
}
