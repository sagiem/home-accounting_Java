package ru.sagiem.whattobuy.repository.poroduct;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sagiem.whattobuy.model.shopping.ShoppingProject;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ShoppingProjectRepository extends JpaRepository<ShoppingProject, Integer> {

    Optional<List<ShoppingProject>> findByIdIn(Collection<Integer> id);
    Optional<List<ShoppingProject>> findByFamilyGroupIn(List<FamilyGroup> familyGroups);
    Optional<List<ShoppingProject>> findByUserCreatorOrFamilyGroupIn(User userCreator, List<FamilyGroup> familyGroup);
}
