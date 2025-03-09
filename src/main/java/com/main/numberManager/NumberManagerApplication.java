package com.main.numberManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NumberManagerApplication {

	public static void main(String[] args) {

		SpringApplication.run(NumberManagerApplication.class, args);
	}

}
