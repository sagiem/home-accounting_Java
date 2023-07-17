package ru.sagiem.whattobuy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sagiem.whattobuy.model.Family;

import java.util.Optional;

@Repository
public interface FamilyRepository extends CrudRepository<Family, Long> {

    Optional<Family> findByName(String name);
}
