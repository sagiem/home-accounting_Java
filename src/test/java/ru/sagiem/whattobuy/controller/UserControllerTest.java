package ru.sagiem.whattobuy.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("Интеграционные тесты для UserController")
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    //@WithMockUser(username = "test", password = "<PASSWORD>")
    @DisplayName("Отправляем email существующего пользователя и получаем в ответ его id")
    void searchUserByEmail_RequestIsValid_shouldReturnUser() throws Exception {

        //given
        String email = "max@yandex.ru";
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/user/search/{email}", email);
        //.with(jwt().jwt(builder -> builder.))
        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    @DisplayName("Отправляем email не существующего пользователя и получаем в ответ Exception")
    void searchUserByEmail_RequestIsNotValid_ThrowException() throws Exception {

        //given
        String email = "test@yandex.ru";
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/user/search/{email}", email)
                .with(user("max").password("12345678"));
        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value("UsernameNotFoundException"));
    }
}



