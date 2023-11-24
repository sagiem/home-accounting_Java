package ru.sagiem.whattobuy.controller.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.sagiem.whattobuy.dto.ProductDtoRequest;
import ru.sagiem.whattobuy.dto.ProductDtoResponse;
import ru.sagiem.whattobuy.dto.ShoppingProjectDtoRequest;
import ru.sagiem.whattobuy.dto.ShoppingProjectDtoResponse;
import ru.sagiem.whattobuy.model.shopping.ShoppingProject;
import ru.sagiem.whattobuy.service.ShoppingProjectService;

@RestController
@RequestMapping("/api/v1/shopping_project")
@RequiredArgsConstructor
@Tag(name = "Проект покупок")
public class ShoppingProjectController {

    private final ShoppingProjectService service;


    @GetMapping("/show_all")
    public ResponseEntity<?> showAllByFamilyGroupAll(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.showAllByFamilyGroupAll(userDetails));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ShoppingProjectDtoRequest request,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.add(request, userDetails));
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<ShoppingProjectDtoResponse> search(@PathVariable("id") @Min(1) Integer id,
                                                             @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(service.searchId(id, userDetails));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") @Min(1) Integer id,
                                    ShoppingProjectDtoRequest request,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.update(id, request, userDetails));


    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") @Min(1) Integer id, @AuthenticationPrincipal UserDetails userDetails){

        return ResponseEntity.ok(service.delete(id, userDetails));
    }
}
