package ru.sagiem.whattobuy.controller.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sagiem.whattobuy.service.ShoppingProjectService;

@RestController
@RequestMapping("/api/v1/shopping_project")
@RequiredArgsConstructor
@Tag(name = "Проект покупок")
public class ShoppingProjectController {

    private final ShoppingProjectService service;


    @GetMapping("/show_all")
    public ResponseEntity<?> showAll(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(service.showAll(userDetails));
    }
}
