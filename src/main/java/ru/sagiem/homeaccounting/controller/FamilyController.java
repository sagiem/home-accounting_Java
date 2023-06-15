package ru.sagiem.homeaccounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sagiem.homeaccounting.model.Family;
import ru.sagiem.homeaccounting.repository.FamilyRepository;
import ru.sagiem.homeaccounting.repository.UserRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FamilyController {

    final FamilyRepository familyRepository;

    @GetMapping("/family")
    public ResponseEntity<Iterable<Family>> FamilyAllList(){
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(familyRepository.findAll());
    }
}
