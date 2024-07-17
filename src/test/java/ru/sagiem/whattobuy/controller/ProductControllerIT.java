package ru.sagiem.whattobuy.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:JavadocStyle"})
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {

    }

    @SuppressWarnings({"checkstyle:RegexpSinglelineJava", "checkstyle:FileTabCharacter"})
    @Test
    @DisplayName("Тест добавление товара")
    public void addTest() throws Exception {
        String request = """
                {
                    "name": "колбаса докторская",
                    "categoryId": 0,
                    "subcategoryId": 0,
                    "unitOfMeasurement": "кг",
                    "familyGroupId": 2
                  }""";

        mockMvc.perform(post("/api/v1/product/add")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("max@yandex.ru")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Тест добавления товара когда неверно указана famaly group")
    public void addTestFamalyGroupNotFound() throws Exception {
        String request = """
                {
                    "name": "колбаса докторская",
                    "categoryId": 0,
                    "subcategoryId": 0,
                    "unitOfMeasurement": "кг",
                    "familyGroupId": 10
                  }""";

        mockMvc.perform(post("/api/v1/product/add")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("max@yandex.ru")))
                .andExpectAll(status().isNotFound(),
                        jsonPath("$.type").value("FamilyGroupNotFoundException")
                )
                .andDo(print());
    }

    @Test
    @DisplayName("Тест добавления товара когда пользователь не состоит в famaly group")
    public void addTestFamalyGroupNotUser() throws Exception {
        String request = """
                {
                    "name": "колбаса докторская",
                    "categoryId": 0,
                    "subcategoryId": 0,
                    "unitOfMeasurement": "кг",
                    "familyGroupId": 1
                  }""";

        mockMvc.perform(post("/api/v1/product/add")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("max@yandex.ru")))
                .andExpectAll(status().isNotFound(),
                        jsonPath("$.type").value("FamilyGroupNotUserException")
                )
                .andDo(print());
    }

    @SuppressWarnings("checkstyle:LocalVariableName")
    @Test
    @DisplayName("Тест удаления товара")
    public void deleteTest() throws Exception {
        String Id = "4";

        mockMvc.perform(delete("/api/v1/product/{Id}", Id)
                        .with(user("max@yandex.ru").password("100")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Поиск продукта по id")
    public void searchIdTest() throws Exception {
        String id = "7";

        mockMvc.perform(get("/api/v1/product/search/{id}", id)
                        .with(user("max@yandex.ru")))
                .andDo(print())
                .andExpectAll(status().isOk(),
                        jsonPath("$.id").value(id)
                );

    }

    @Test
    @DisplayName("Поиск продукта по id когда пользователь не состоит в famaly group товара")
    public void searchIdTestFamalyGroupNotUse() throws Exception {
        String id = "7";

        mockMvc.perform(get("/api/v1/product/search/{id}", id)
                        .with(user("max1@yandex.ru")))
                .andDo(print())
                .andExpectAll(status().isNotFound(),
                        jsonPath("$.type").value("FamilyGroupNotUserException")
                );

    }

    @Test
    @DisplayName("Тест поиска товаров в категории")
    public void showAllInCategoryTest() throws Exception {
        String idGroup = "2";
        String idCategory = "1";

        mockMvc.perform(get("/api/v1/product/show-all-in-category/{idGroup}/{idCategory}", idGroup, idCategory)
                        .with(user("max@yandex.ru").password("100")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Тест поиска товаров в famaly group")
    public void showAllInGroupTest() throws Exception {
        String id = "2";

        mockMvc.perform(get("/api/v1/product/show-all-in-group/{id}", id)
                        .with(user("max@yandex.ru").password("100")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Тест поиска товаров в подкатегории")
    public void showAllInSubcategoryTest() throws Exception {
        String idGroup = "2";
        String idSubcategory = "1";

        mockMvc.perform(get("/api/v1/product/show-all-in-subcategory/{idGroup}/{idSubcategory}", idGroup, idSubcategory)
                        .with(user("max@yandex.ru")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @SuppressWarnings({"checkstyle:FileTabCharacter", "checkstyle:RegexpSinglelineJava"})
    @Test
    @DisplayName("Тест обновления товара")
    public void updateTest() throws Exception {
        String id = "4";
        String productDtoRequest = """
                {
                  "name": "новое мороженное",
                  "categoryId": 1,
                  "subcategoryId": 1,
                  "unitOfMeasurement": "кг",
                  "familyGroupId": 2
                }""";

        mockMvc.perform(patch("/api/v1/product/update/{id}", id)
                        .content(productDtoRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("max@yandex.ru")))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
