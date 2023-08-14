package ru.sagiem.whattobuy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.sagiem.whattobuy.dto.auth.RegisterRequest;
import ru.sagiem.whattobuy.service.AuthenticationService;
import static ru.sagiem.whattobuy.model.Role.*;

@SpringBootApplication
public class WhatToBuyApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhatToBuyApplication.class, args);
	}

}
