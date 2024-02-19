package ru.sagiem.whattobuy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
=======
>>>>>>> origin/main
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/family-group")
@RequiredArgsConstructor
@Tag(name = "Работа с группами")
public class FamilyGroupController {
<<<<<<< HEAD

    @GetMapping("/show-all-my-created")
    public ResponseEntity<String> showAllMyCreated() {
        return ResponseEntity.ok("showAllMyCreated");
    }
=======
>>>>>>> origin/main
}
