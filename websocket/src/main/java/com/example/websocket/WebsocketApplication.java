package com.example.websocket;

import com.example.common.PropertyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@Import(PropertyConfig.class)
@EnableRetry
public class WebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketApplication.class, args);
	}

}
