package ru.sagiem.whattobuy.repository.poroduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sagiem.whattobuy.model.product.CategoryProduct;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.user.FamilyGroup;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Integer> {
    Optional<CategoryProduct> findByName(String name);
    Optional<List<CategoryProduct>> findAllByFamilyGroup(FamilyGroup familyGroup);

    Optional<List<CategoryProduct>> findByFamilyGroupId(Integer id); // TODO: а так работает ?
}
