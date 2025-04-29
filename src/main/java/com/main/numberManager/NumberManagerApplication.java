package com.main.numberManager;

import com.main.numberManager.utils.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.reflect.Array;

@SpringBootApplication
@EnableAsync
public class NumberManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NumberManagerApplication.class, args);
	}

}
