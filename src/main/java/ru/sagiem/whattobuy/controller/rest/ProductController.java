package ru.sagiem.whattobuy.controller.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.sagiem.whattobuy.dto.auth.ProductDto;
import ru.sagiem.whattobuy.repository.UserRepository;
import ru.sagiem.whattobuy.service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "Работа с продуктом")
public class ProductController {

    private final ProductService productService;
    UserRepository userRepository;



    @GetMapping("/show_all")
    public ResponseEntity<?> show(@AuthenticationPrincipal UserDetails userDetails) {

//        var user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
//
//        if (user.getUsersFamilyGroup() != null)
//            return productService.showAllFamaly(userDetails);

        return ResponseEntity.ok(productService.showAllId(userDetails));

    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ProductDto request,
                                 @AuthenticationPrincipal UserDetails userDetails) {

        return productService.addProduct(request, userDetails);
    }

    @GetMapping("/search/{nameOrId}")
    public ResponseEntity<?> searchName(@RequestBody Object nameOrId,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        if(String.valueOf(nameOrId).isEmpty())
            return ResponseEntity.ok(productService.searchName(String.valueOf(nameOrId), userDetails));

        return ResponseEntity.ok(productService.searchId(Integer.valueOf(String.valueOf(nameOrId)), userDetails));

    }


    @PatchMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") @Min(1) Integer id,
                                    ProductDto productDto,
                                    @AuthenticationPrincipal UserDetails userDetails) {


        return productService.update(id, productDto, userDetails);

    }
}
