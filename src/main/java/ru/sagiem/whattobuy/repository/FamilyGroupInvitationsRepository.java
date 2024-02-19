package ru.sagiem.whattobuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sagiem.whattobuy.model.user.FamilyGroupInvitations;

@Repository
public interface FamilyGroupInvitationsRepository extends JpaRepository<FamilyGroupInvitations, Integer> {
}
