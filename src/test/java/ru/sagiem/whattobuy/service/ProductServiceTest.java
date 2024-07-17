package ru.sagiem.whattobuy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
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

import java.util.ArrayList;
import java.util.Collection;
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

    @BeforeEach
    public void setup() {

    }

    @Test
    //@WithUserDetails(value = "max@yandex.ru")
    public void testShowAllInGroup() {


        FamilyGroup familyGroup = FamilyGroup.builder()
                .id(1)
                .name("шопоголики")
                .build();

        User user = User.builder()
                .id(1)
                .email("max@yandex.ru")
                .familyGroups(List.of(familyGroup))
                .build();
        familyGroup.setUsers(List.of(user));


        Product product = Product.builder()
                .id(1)
                .name("Мороженное")
                .userCreator(user)
                .familyGroup(familyGroup)
                .build();

        UserDetails userDetails = new UserDetails(){

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public String getPassword() {
                return "100";
            }

            @Override
            public String getUsername() {
                return "sagiem@yandex.ru";
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };


        // Arrange
        when(familyGroupRepository.findById(any())).thenReturn(Optional.of(familyGroup));
        when(productRepository.findAllByFamilyGroup(any())).thenReturn(Optional.of(List.of(product)));
        when(familyGroupAndUserUtils.getUser(any())).thenReturn(user);

        // Act
        List<ProductDtoResponse> products = productService.showAllInGroup(1);

        // Assert
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(1);
    }

    @Test
    public void testShowAllInCategory() {
        // Arrange
        when(familyGroupRepository.findById(any())).thenReturn(Optional.of(new FamilyGroup()));
        when(categoryProductRepository.findById(any())).thenReturn(Optional.of(new CategoryProduct()));
        when(productRepository.findAllByFamilyGroupAndCategory(any(), any())).thenReturn(Optional.of(List.of(new Product())));

        // Act
        List<ProductDtoResponse> products = productService.showAllInCategory(1, 2);

        // Assert
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(1);
    }

    @Test
    public void testShowAllInSubcategory() {
        // Arrange
        when(familyGroupRepository.findById(any())).thenReturn(Optional.of(new FamilyGroup()));
        when(subcategoryProductRepository.findById(any())).thenReturn(Optional.of(new SubcategoryProduct()));
        when(productRepository.findAllByFamilyGroupAndSubcategory(any(), any())).thenReturn(Optional.of(List.of(new Product())));

        // Act
        List<ProductDtoResponse> products = productService.showAllInSubcategory(1, 2);

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
        ProductDtoResponse product = productService.searchId(1);

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
        String updatedName = productService.update(1, request);

        // Assert
        assertThat(updatedName).isEqualTo("Updated Name");
    }

    @Test
    public void testDelete() {
        // Arrange
        when(productRepository.findById(any())).thenReturn(Optional.of(new Product()));

        // Act
        String deletedName = productService.delete(1);

        // Assert
        assertThat(deletedName).isEqualTo("Deleted Name");
    }


}