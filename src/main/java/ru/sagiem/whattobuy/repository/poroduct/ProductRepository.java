package ru.sagiem.whattobuy.repository.poroduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
    Optional<Product> findByNameAndFamilyGroupAndUser(String name, FamilyGroup familyGroup, Optional<User> user);
    Optional<Product> findByIdAndFamilyGroupAndUser(Integer id, FamilyGroup familyGroup, Optional<User> user);
    Optional<Product> findAllByNameOrFamilyGroup(String name, Optional<FamilyGroup> familyGroup);

    Optional<Product> findAllByFamilyGroup(FamilyGroup familyGroup);

    Optional<Product> findAllByUser(User user);
}
