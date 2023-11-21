package ru.sagiem.whattobuy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@SpringBootApplication
@EnableJpaAuditing
public class WhatToBuyApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhatToBuyApplication.class, args);
	}

}
