package ru.sagiem.whattobuy.repository.poroduct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.sagiem.whattobuy.model.product.CategoryProduct;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.product.SubcategoryProduct;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;
import ru.sagiem.whattobuy.repository.FamilyGroupRepository;
import ru.sagiem.whattobuy.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
public class ProductRepositoryIT {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testFindAllByUserCreator() {
        // Arrange
        User user = userRepository.findByEmail("max@yandex.ru").orElse(null);

        // Act
        List<Product> products = productRepository.findAllByUserCreator(user).orElse(null);

        // Assert
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(1);
        assertEquals(user, products.get(0).getUser());
    }

    @Test
    public void testFindAllByFamilyGroup() {
        // Arrange


        // Act
        List<Product> products = productRepository.findAllByFamilyGroup(new FamilyGroup()).orElse(null);

        // Assert
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(1);
    }

    @Test
    public void testFindAllByFamilyGroupAndCategory() {
        // Arrange


        // Act
        List<Product> products = productRepository.findAllByFamilyGroupAndCategory(new FamilyGroup(), new CategoryProduct()).orElse(null);

        // Assert
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(1);
    }

    @Test
    public void testFindAllByFamilyGroupAndSubcategory() {
        // Arrange


        // Act
        List<Product> products = productRepository.findAllByFamilyGroupAndSubcategory(new FamilyGroup(), new SubcategoryProduct()).orElse(null);

        // Assert
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(1);
    }
}