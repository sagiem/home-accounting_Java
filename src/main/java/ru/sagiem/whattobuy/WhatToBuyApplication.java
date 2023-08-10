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

	//TODO: Демонстрация, удалить !!!
	@Bean
	public CommandLineRunner commandLineRunner(AuthenticationService service){
		return args -> {
			var admin = RegisterRequest.builder()
					.username("Admin")
					.email("admin@mail.com")
					.password("password")
					.role(ADMIN)
					.build();
			System.out.println("Admin token: " + service.register(admin).getAccessToken());

			var manager = RegisterRequest.builder()
					.username("Manager")
					.email("manager@mail.com")
					.password("password")
					.role(MANAGER)
					.build();
			System.out.println("Manager token: " + service.register(manager).getAccessToken());

		};
	}

}
