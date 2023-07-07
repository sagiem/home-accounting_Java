package ru.sagiem.homeaccounting.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sagiem.homeaccounting.model.Family;
import ru.sagiem.homeaccounting.repository.FamilyRepository;

@RestController
@RequiredArgsConstructor
public class FamilyRestController {

    final FamilyRepository familyRepository;

    @Transactional
    @GetMapping("/family")
    public ResponseEntity<Iterable<Family>> FamilyAllList(){
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(familyRepository.findAll());
    }
}
