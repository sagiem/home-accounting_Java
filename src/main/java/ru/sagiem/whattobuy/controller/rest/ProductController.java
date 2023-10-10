package ru.sagiem.whattobuy.controller.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.sagiem.whattobuy.dto.auth.ProductDto;
import ru.sagiem.whattobuy.service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "Работа с продуктом")
public class ProductController {

    private final ProductService service;

    @GetMapping ("/show_all")
    public ResponseEntity<?> show(@AuthenticationPrincipal UserDetails userDetails){
        return service.showAll(userDetails);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ProductDto request,
                                 @AuthenticationPrincipal UserDetails userDetails){

        return service.addProduct(request, userDetails);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchName(@RequestBody String name,
                                      @AuthenticationPrincipal UserDetails userDetails){

        return service.searchName(name, userDetails);

    }

//    @GetMapping("/search/{id}")
//    public ResponseEntity<?> searchId(@RequestBody Integer id,
//                                      @AuthenticationPrincipal UserDetails userDetails){
//
//        return service.searchId(id, userDetails);
//    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") @Min(1) Integer id,
                                    ProductDto productDto,
                                    @AuthenticationPrincipal UserDetails userDetails){


        return ResponseEntity.ok(service.update(id, productDto, userDetails));

    }
}
