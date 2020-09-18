package com.microservice.training.photoappapiusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.microservice.training.photoappapiusers.service.UserService;
import com.microservice.training.photoappapiusers.service.impl.UserServiceImpl;

@SpringBootApplication
@EnableDiscoveryClient
public class PhotoappapiusersApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoappapiusersApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder byBCryptPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
}
