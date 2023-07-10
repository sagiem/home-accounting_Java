package ru.sagiem.homeaccounting.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sagiem.homeaccounting.dtos.RegistrationUserDto;
import ru.sagiem.homeaccounting.model.User;
import ru.sagiem.homeaccounting.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @PostMapping("/registration")
    @Transactional
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto){
        return userService.createUser(registrationUserDto);
    }
}
