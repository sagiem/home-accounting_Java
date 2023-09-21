package ru.sagiem.whattobuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sagiem.whattobuy.model.product.SubcategoryProduct;
import ru.sagiem.whattobuy.model.product.UnitOfMeasurementProduct;

import java.util.Optional;

public interface UnitOfMeasurementProductRepository extends JpaRepository<ru.sagiem.whattobuy.model.product.UnitOfMeasurementProduct, Integer> {
    Optional<UnitOfMeasurementProduct> findByName(String name);
}
