package com.wp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.wp.**"})
public class BjeamApplication {

	public static void main(String[] args) {
		SpringApplication.run(BjeamApplication.class, args);
	}
}
