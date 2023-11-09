package ru.sagiem.whattobuy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WhatToBuyApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhatToBuyApplication.class, args);
	}

}
