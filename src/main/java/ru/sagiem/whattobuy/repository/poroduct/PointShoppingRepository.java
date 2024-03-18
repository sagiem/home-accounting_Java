package ru.sagiem.whattobuy.repository.poroduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sagiem.whattobuy.model.shopping.PointShopping;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PointShoppingRepository extends JpaRepository<PointShopping, Integer> {


    Optional<List<PointShopping>> findByUserCreatorOrFamilyGroupIn (User userCreator, Collection<FamilyGroup> familyGroup);
    Optional<List<PointShopping>> findByUserCreator(User user);

    PointShopping findByIdAndFamilyGroupIn(Integer id, Collection<FamilyGroup> familyGroup);
    PointShopping findByIdAndUserCreator(Integer id, User user);
    Optional<List<PointShopping>> findByIdIn(Collection<Integer> id);

    Optional<List<PointShopping>> findAllByFamilyGroup(FamilyGroup familyGroup);


    Optional<List<PointShopping>> findAllByUserCreator(User user);
}
