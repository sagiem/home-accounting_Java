package ru.sagiem.whattobuy.repository.poroduct;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sagiem.whattobuy.model.shopping.Shopping;

public interface ShoppingRepository extends JpaRepository<Shopping, Integer> {
}
