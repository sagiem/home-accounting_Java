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


    List<Product> findAllByFamilyGroup(FamilyGroup familyGroup);
    List<Product> findAllByUserCreator(User user);

    Product findByIdAndFamilyGroup(Integer id, FamilyGroup familyGroup);
    Product findByIdAndUserCreator(Integer id, User user);

    List<Product> findByNameAndFamilyGroup(String name, FamilyGroup familyGroup);
    List<Product> findByNameAndUserCreator(String name, User user);
}
