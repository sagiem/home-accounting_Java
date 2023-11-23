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
import ru.sagiem.whattobuy.service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "Работа с продуктом")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/show_all")
    public ResponseEntity<?> showAll(@AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(productService.showAll(userDetails));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ProductDtoRequest request,
                                 @AuthenticationPrincipal UserDetails userDetails) {

        return productService.addProduct(request, userDetails);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<ProductDtoResponse> searchName(@PathVariable("id") @Min(1) Integer id,
                                                         @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(productService.searchId(id, userDetails));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ProductDtoResponse> update(@PathVariable("id") @Min(1) Integer id,
                                                     ProductDtoRequest productDtoRequest,
                                                     @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(productService.update(id, productDtoRequest, userDetails));
    }
}
