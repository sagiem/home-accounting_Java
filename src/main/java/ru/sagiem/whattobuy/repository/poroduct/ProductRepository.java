package ru.sagiem.whattobuy.repository.poroduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByName(String name);
    Product findByNameAndFamilyGroupAndUser(String name, FamilyGroup familyGroup, Optional<User> user);
    Product findByIdAndFamilyGroupAndUser(Integer id, FamilyGroup familyGroup, Optional<User> user);
    List<Product> findAllByNameOrFamilyGroup(String name, Optional<FamilyGroup> familyGroup);

    List<Product> findAllByFamilyGroup(FamilyGroup familyGroup);

    List<Product> findAllByUser(User user);
}
