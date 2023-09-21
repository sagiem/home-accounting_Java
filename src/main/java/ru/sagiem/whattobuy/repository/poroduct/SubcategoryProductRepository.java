package ru.sagiem.whattobuy.repository.poroduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sagiem.whattobuy.model.product.SubcategoryProduct;

import java.util.Optional;

@Repository
public interface SubcategoryProductRepository extends JpaRepository<SubcategoryProduct, Integer> {
    Optional<SubcategoryProduct> findByName(String name);
}
