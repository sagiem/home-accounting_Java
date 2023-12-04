package ru.sagiem.whattobuy.repository.poroduct;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sagiem.whattobuy.model.shopping.Shopping;
import ru.sagiem.whattobuy.model.shopping.ShoppingProject;
import ru.sagiem.whattobuy.model.shopping.ShoppingStatus;

import java.util.List;
import java.util.Optional;

public interface ShoppingRepository extends JpaRepository<Shopping, Integer> {
    Optional<List<Shopping>> findByShoppingProject(ShoppingProject shoppingProject);
    Optional<List<Shopping>> findByShoppingProjectAndShoppingStatus(ShoppingProject shoppingProject, ShoppingStatus shoppingStatus);
}
