package ru.sagiem.whattobuy.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sagiem.whattobuy.dto.auth.AuthenticationRequest;
import ru.sagiem.whattobuy.dto.auth.AuthenticationResponse;
import ru.sagiem.whattobuy.dto.auth.RegisterRequest;
import ru.sagiem.whattobuy.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Регистрация и аутентификация пользователей")
public class AuthenticationController {
    private final AuthenticationService service;

    @Operation(
            description = "Регистрация",
            summary = "Регистрация пользователей",
            responses = {
                    @ApiResponse(
                            description = "Успешно",
                            responseCode = "200"
                    )
            }

    )
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));

    }

        @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));

    }
}
