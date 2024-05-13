package ru.sagiem.whattobuy.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.sagiem.whattobuy.dto.UserDTOResponse;
import ru.sagiem.whattobuy.mapper.UserMapper;
import ru.sagiem.whattobuy.model.user.User;
import ru.sagiem.whattobuy.repository.UserRepository;
import ru.sagiem.whattobuy.service.UserService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@DisplayName("Модульные тесты для UserController")
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
       // MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("Отправляем email существующего пользователя и получаем в ответ его id")
    void searchUserByEmail_RequestIsValid_shouldReturnUser() throws Exception {

        String userEmail = "max@yandex.ru";

        UserDTOResponse userDTOResponse = new UserDTOResponse();
        userDTOResponse.setId(1);
        userDTOResponse.setEmail(userEmail);

        User user = User.builder()
                .id(1)
                .email(userEmail).build();


        when(userService.searchUserByEmail(userEmail)).thenReturn(userDTOResponse);

        mockMvc.perform(get("/api/v1/user/search/{email}", userEmail))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(userService, times(1)).searchUserByEmail(userEmail);

    }

    @Test
    @DisplayName("Отправляем email не существующего пользователя и получаем в ответ ошибку")
    void searchUserByEmail_RequestIsNotValid_shouldReturnUser() throws Exception {
//        var exp = assertThrows(UsernameNotFoundException.class, () -> userController.searchUserByEmail("max@yandex.ru"));
//        assertEquals(USER_NOT_FOUND_EXCEPTION_MESSAGE, exp.getMessage());

         String userEmail = "max@yandex.ru";
        String testEmail = "test@yandex.ru";

        UserDTOResponse userDTOResponse = new UserDTOResponse();
        userDTOResponse.setId(1);
        userDTOResponse.setEmail(userEmail);

        User user = User.builder()
                .id(1)
                .email(userEmail).build();


        when(userService.searchUserByEmail(userEmail)).thenReturn(userDTOResponse);

        mockMvc.perform(get("/api/v1/user/search/{email}", testEmail))
                .andExpect(status().isNotFound());
    }

}



