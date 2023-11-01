package ru.sagiem.whattobuy.repository.poroduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sagiem.whattobuy.model.shopping.PointShopping;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PointShoppingRepository extends JpaRepository<PointShopping, Integer> {


    Optional<List<PointShopping>> findAllByFamilyGroup(FamilyGroup familyGroup);
    Optional<List<PointShopping>> findAllByUserCreator(User user);

    PointShopping findByIdAndFamilyGroup(Integer id, FamilyGroup familyGroup);
    PointShopping findByIdAndUserCreator(Integer id, User user);
}
