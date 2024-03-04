package ru.sagiem.whattobuy.repository;

import com.fasterxml.classmate.AnnotationOverrides;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.FamilyGroupInvitations;
import ru.sagiem.whattobuy.model.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface FamilyGroupInvitationsRepository extends JpaRepository<FamilyGroupInvitations, Integer> {

    Optional<List<FamilyGroupInvitations>> findByFamilyGroupIn(List<FamilyGroup> familyGroups);
    Optional<List<FamilyGroupInvitations>> findByUser(User user);
}
