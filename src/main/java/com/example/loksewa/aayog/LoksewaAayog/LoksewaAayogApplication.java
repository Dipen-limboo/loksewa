package com.example.loksewa.aayog.LoksewaAayog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.loksewa.aayog.LoksewaAayog")
public class LoksewaAayogApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoksewaAayogApplication.class, args);
	}

}
