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



    Optional<List<Product>>findAllByFamilyGroup(FamilyGroup familyGroup);
    Optional<List<Product>> findAllByUserCreator(User user);

    Product findByIdAndFamilyGroup(Integer id, FamilyGroup familyGroup);
    Product findByIdAndUserCreator(Integer id, User user);


}
