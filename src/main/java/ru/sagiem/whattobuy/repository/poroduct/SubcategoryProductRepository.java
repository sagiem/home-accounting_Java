package ru.sagiem.whattobuy.repository.poroduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sagiem.whattobuy.model.product.CategoryProduct;
import ru.sagiem.whattobuy.model.product.SubcategoryProduct;

import java.util.List;
import java.util.Optional;


public interface SubcategoryProductRepository extends JpaRepository<SubcategoryProduct, Integer> {
    Optional<SubcategoryProduct> findByName(String name);
    Optional<List<SubcategoryProduct>> findAllByCategoryProduct(CategoryProduct categoryProduct);

    List<SubcategoryProduct> findAllByCategoryProductId(Integer categoryProductId); // TODO а так работает ?
}
