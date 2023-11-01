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
import ru.sagiem.whattobuy.dto.auth.ProductDtoRequest;
import ru.sagiem.whattobuy.dto.auth.ShoppingDtoRequest;
import ru.sagiem.whattobuy.service.ShoppingService;

@RestController
@RequestMapping("/api/v1/shopping")
@RequiredArgsConstructor
@Tag(name = "Покупки")
public class ShoppingController {

    private final ShoppingService shoppingService;

    @PostMapping("/add")
    public ResponseEntity<Integer> add(@RequestBody ShoppingDtoRequest shoppingDtoRequest,
                                 @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(shoppingService.addShopping(shoppingDtoRequest, userDetails));
    }
}
