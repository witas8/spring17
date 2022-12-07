package com.example.spring17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Spring17Application {

	//http://localhost:8080/spring17
	//http://localhost:8080/swagger-ui/index.html
	//http://localhost:8080/spring17/login (post, body x-www-form-un, username, password)
	public static void main(String[] args) {
		SpringApplication.run(Spring17Application.class, args);
	}

}
