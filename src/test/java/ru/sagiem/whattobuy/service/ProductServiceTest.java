package ru.sagiem.whattobuy.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import ru.sagiem.whattobuy.dto.ProductDtoRequest;
import ru.sagiem.whattobuy.dto.ProductDtoResponse;
import ru.sagiem.whattobuy.mapper.ProductMapper;
import ru.sagiem.whattobuy.model.product.CategoryProduct;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.product.SubcategoryProduct;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.repository.FamilyGroupRepository;
import ru.sagiem.whattobuy.repository.UserRepository;
import ru.sagiem.whattobuy.repository.poroduct.CategoryProductRepository;
import ru.sagiem.whattobuy.repository.poroduct.ProductRepository;
import ru.sagiem.whattobuy.repository.poroduct.SubcategoryProductRepository;
import ru.sagiem.whattobuy.utils.FamilyGroupAndUserUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private FamilyGroupRepository familyGroupRepository;

    @MockBean
    private CategoryProductRepository categoryProductRepository;

    @MockBean
    private SubcategoryProductRepository subcategoryProductRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductMapper productMapper;

    @MockBean
    private FamilyGroupAndUserUtils familyGroupAndUserUtils;

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    public void testShowAllInGroup() {
//        // Arrange
//        when(familyGroupRepository.findById(any())).thenReturn(Optional.of(new FamilyGroup()));
//        when(productRepository.findAllByFamilyGroup(any())).thenReturn(List.of(new Product()));
//
//        // Act
//        List<ProductDtoResponse> products = productService.showAllInGroup(null, 1);
//
//        // Assert
//        assertThat(products).isNotNull();
//        assertThat(products.size()).isEqualTo(1);
//    }

//    @Test
//    public void testShowAllInCategory() {
//        // Arrange
//        when(familyGroupRepository.findById(any())).thenReturn(Optional.of(new FamilyGroup()));
//        when(categoryProductRepository.findById(any())).thenReturn(Optional.of(new CategoryProduct()));
//        when(productRepository.findAllByFamilyGroupAndCategory(any(), any())).thenReturn(List.of(new Product()));
//
//        // Act
//        List<ProductDtoResponse> products = productService.showAllInCategory(null, 1, 2);
//
//        // Assert
//        assertThat(products).isNotNull();
//        assertThat(products.size()).isEqualTo(1);
//    }

    @Test
    public void testShowAllInSubcategory() {
        // Arrange
        when(familyGroupRepository.findById(any())).thenReturn(Optional.of(new FamilyGroup()));
        when(subcategoryProductRepository.findById(any())).thenReturn(Optional.of(new SubcategoryProduct()));
        when(productRepository.findAllByFamilyGroupAndSubcategory(any(), any())).thenReturn(Optional.of(List.of(new Product())));

        // Act
        List<ProductDtoResponse> products = productService.showAllInSubcategory(null, 1, 2);

        // Assert
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(1);
    }

    @Test
    public void testAdd() {
        // Arrange
        ProductDtoRequest request = new ProductDtoRequest();
        request.setName("Product Name");
        request.setCategoryId(1);
        request.setSubcategoryId(2);
        request.setUnitOfMeasurement("unit");
        when(familyGroupRepository.findById(any())).thenReturn(Optional.of(new FamilyGroup()));

        // Act
        ResponseEntity<?> response = productService.add(request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(1);
    }

    @Test
    public void testSearchId() {
        // Arrange
        when(productRepository.findById(any())).thenReturn(Optional.of(new Product()));

        // Act
        ProductDtoResponse product = productService.searchId(1, null);

        // Assert
        assertThat(product).isNotNull();
    }

    @Test
    public void testUpdate() {
        // Arrange
        ProductDtoRequest request = new ProductDtoRequest();
        request.setName("Updated Name");
        when(productRepository.findById(any())).thenReturn(Optional.of(new Product()));

        // Act
        String updatedName = productService.update(1, request, null);

        // Assert
        assertThat(updatedName).isEqualTo("Updated Name");
    }

    @Test
    public void testDelete() {
        // Arrange
        when(productRepository.findById(any())).thenReturn(Optional.of(new Product()));

        // Act
        String deletedName = productService.delete(1, null);

        // Assert
        assertThat(deletedName).isEqualTo("Deleted Name");
    }

    @Test
    @DisplayName("Test add")
    @WithMockUser(username = "max@yandex.ru", password = "100")
    public void addTest() throws Exception {
        String request = """
                {
                   "name": "мороженное",
                   "categoryId": 0,
                   "subcategoryId": 0,
                   "unitOfMeasurement": "кг",
                   "familyGroupId": 2
                }""";

        mockMvc.perform(post("/api/v1/product/add")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        //.with(SecurityMockMvcRequestPostProcessors.user("max@yandex.ru").password("100"))
                )

                .andExpect(status().isOk())
                .andDo(print());
    }
}