package ru.sagiem.whattobuy.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.sagiem.whattobuy.dto.UserDTOResponse;
import ru.sagiem.whattobuy.dto.auth.UserRegisterDto;
import ru.sagiem.whattobuy.model.user.User;
import ru.sagiem.whattobuy.repository.UserRepository;
import ru.sagiem.whattobuy.service.UserService;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Модульные тесты для UserController")
class UserControllerTest {

    @Mock
    UserRepository userRepository;

    @Mock
    AuthenticationController authenticationController;

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @Test
    @DisplayName("Отправляем email существующего пользователя и получаем в ответ его id")
    void searchUserByEmail_RequestIsValid_shouldReturnUser() {

        // given
        String email = "test@email.ru";
        String Password = "12345678";
        User user = new User();
        user.setId(1);

        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setEmail(email);
        userRegisterDto.setPassword(Password);
        userRegisterDto.setConfirmPassword(Password);

        authenticationController.register(userRegisterDto);


        // when
        ResponseEntity<UserDTOResponse> response = userController.searchUserByEmail(email);

        // then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(user.getEmail(), (response.getBody()).getEmail());
    }


}



