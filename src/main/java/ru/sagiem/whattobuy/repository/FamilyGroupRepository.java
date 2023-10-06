package ru.sagiem.whattobuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;

import java.util.Optional;

@Repository
public interface FamilyGroupRepository extends JpaRepository<FamilyGroup, Integer> {
    Optional<FamilyGroup> findByOwnerUserId_Email(String name);

}
