package ru.sagiem.whattobuy.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import ru.sagiem.whattobuy.dto.ProductDtoRequest;
import ru.sagiem.whattobuy.dto.ProductDtoResponse;
import ru.sagiem.whattobuy.mapper.ProductMapper;
import ru.sagiem.whattobuy.model.product.CategoryProduct;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.product.SubcategoryProduct;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
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

//    @Test
//    public void testShowAllInGroup() {
//        // Arrange
//        when(familyGroupRepository.findById(1)).thenReturn(Optional.of(new FamilyGroup()));
//        when(productRepository.findAllByFamilyGroup(new FamilyGroup())).thenReturn(Optional.of(List.of(new Product())));
//
//        // Act
//        List<ProductDtoResponse> products = productService.showAllInGroup(1);
//
//        // Assert
//        assertThat(products).isNotNull();
//        assertThat(products.size()).isEqualTo(1);
//    }

    @Test
    @WithMockUser(username = "test@yandex.ru")
    public void testShowAllInGroup() {


        FamilyGroup familyGroup = FamilyGroup.builder()
                .id(1)
                .name("тестовая группа")
                .build();

        User user = User.builder()
                .id(1)
                .email("test@yandex.ru")
                .familyGroups(List.of(familyGroup))
                .build();
        familyGroup.setUsers(List.of(user));


        Product product = Product.builder()
                .id(1)
                .name("Мороженное")
                .userCreator(user)
                .familyGroup(familyGroup)
                .build();


        // Arrange
        when(familyGroupRepository.findById(any())).thenReturn(Optional.of(familyGroup));
        when(productRepository.findAllByFamilyGroup(any())).thenReturn(Optional.of(List.of(product)));
        when(familyGroupAndUserUtils.getUser(any())).thenReturn(user);
        when(familyGroupAndUserUtils.isUserInFamilyGroup(any(), eq(1))).thenReturn(true);


        // Act
        List<ProductDtoResponse> products = productService.showAllInGroup(1);

        // Assert
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(1);
    }

    @Test
    public void testShowAllInCategory() {
        // Arrange
        when(familyGroupRepository.findById(1)).thenReturn(Optional.of(new FamilyGroup()));
        when(categoryProductRepository.findById(1)).thenReturn(Optional.of(new CategoryProduct()));
        when(productRepository.findAllByFamilyGroupAndCategory(new FamilyGroup(), new CategoryProduct())).thenReturn(Optional.of(List.of(new Product())));

        // Act
        List<ProductDtoResponse> products = productService.showAllInCategory(1, 1);

        // Assert
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(1);
    }

    @Test
    public void testShowAllInSubcategory() {
        // Arrange
        when(familyGroupRepository.findById(1)).thenReturn(Optional.of(new FamilyGroup()));
        when(subcategoryProductRepository.findById(1)).thenReturn(Optional.of(new SubcategoryProduct()));
        when(productRepository.findAllByFamilyGroupAndSubcategory(new FamilyGroup(), new SubcategoryProduct())).thenReturn(Optional.of(List.of(new Product())));

        // Act
        List<ProductDtoResponse> products = productService.showAllInSubcategory(1, 1);

        // Assert
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(1);
    }

    @Test
    public void testAdd() {
        // Arrange
        when(familyGroupRepository.findById(1)).thenReturn(Optional.of(new FamilyGroup()));
        when(categoryProductRepository.findById(1)).thenReturn(Optional.of(new CategoryProduct()));
        when(subcategoryProductRepository.findById(1)).thenReturn(Optional.of(new SubcategoryProduct()));
        when(productRepository.save(new Product())).thenReturn(new Product());

        // Act
        ResponseEntity<?> response = productService.add(new ProductDtoRequest());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(1);
    }

    @Test
    public void testSearchId() {
        // Arrange
        when(productRepository.findById(1)).thenReturn(Optional.of(new Product()));

        // Act
        ProductDtoResponse product = productService.searchId(1);

        // Assert
        assertThat(product).isNotNull();
    }

    @Test
    public void testUpdate() {
        // Arrange
        when(productRepository.findById(1)).thenReturn(Optional.of(new Product()));
        when(categoryProductRepository.findById(1)).thenReturn(Optional.of(new CategoryProduct()));
        when(subcategoryProductRepository.findById(1)).thenReturn(Optional.of(new SubcategoryProduct()));

        // Act
        String updatedName = productService.update(1, new ProductDtoRequest());

        // Assert
        assertThat(updatedName).isEqualTo("Updated Name");
    }

    @Test
    public void testDelete() {
        // Arrange
        when(productRepository.findById(1)).thenReturn(Optional.of(new Product()));

        // Act
        String deletedName = productService.delete(1);

        // Assert
        assertThat(deletedName).isEqualTo("Deleted Name");
    }
}