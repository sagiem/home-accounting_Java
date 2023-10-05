package ru.sagiem.whattobuy.controller.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sagiem.whattobuy.dto.auth.ProductAddRequest;
import ru.sagiem.whattobuy.service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "Работа с продуктом")
public class ProductController {

    private final ProductService service;

    @PostMapping("/show_all")
    public ResponseEntity<?> show(@AuthenticationPrincipal UserDetails userDetails){
        return service.showAll(userDetails);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ProductAddRequest request,
                                 @AuthenticationPrincipal UserDetails userDetails){

        return service.addProduct(request, userDetails);
    }
}
