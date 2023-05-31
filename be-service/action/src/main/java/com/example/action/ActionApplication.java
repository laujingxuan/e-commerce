package com.example.action;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.example")
@ComponentScan({"com.example.shared", "com.example.action"})
public class ActionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActionApplication.class, args);
	}

}
