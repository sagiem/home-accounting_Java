package ru.sagiem.whattobuy.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the {@link ProductController}
 */
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
    @DisplayName("Test add")
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

    @SuppressWarnings("checkstyle:LocalVariableName")
    @Test
    @DisplayName("Test delete group")
    public void deleteGroupTest() throws Exception {
        String Id = "5";

        mockMvc.perform(delete("/api/v1/product/{Id}", Id)
                        .with(user("max@yandex.ru").password("100")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Test search id")
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
    @DisplayName("Test show all in category")
    public void showAllInCategoryTest() throws Exception {
        String idGroup = "1";
        String idCategory = "1";

        mockMvc.perform(get("/api/v1/product/show_all/{idGroup}/{idCategory}", idGroup, idCategory)
                        .with(user("max@yandex.ru").password("100")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Test show all in group")
    public void showAllInGroupTest() throws Exception {
        String id = "0";

        mockMvc.perform(get("/api/v1/product/show_all/{id}", id)
                        .with(user("sagiem@yandex.ru").password("100")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Test show all in subcategory")
    public void showAllInSubcategoryTest() throws Exception {
        String idGroup = "0";
        String idSubcategory = "0";

        mockMvc.perform(get("/api/v1/product/show_all/{idGroup}/{idSubcategory}", idGroup, idSubcategory)
                        .with(user("sagiem@yandex.ru").password("100")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @SuppressWarnings({"checkstyle:FileTabCharacter", "checkstyle:RegexpSinglelineJava"})
    @Test
    @DisplayName("Test update")
    public void updateTest() throws Exception {
        String id = "4";
        String productDtoRequest = """
                {
                    "name": "колбаса докторская",
                    "categoryId": 0,
                    "subcategoryId": 0,
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
