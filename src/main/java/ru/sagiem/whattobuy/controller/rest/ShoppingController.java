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
import ru.sagiem.whattobuy.dto.ShoppingDtoRequest;
import ru.sagiem.whattobuy.dto.ShoppingSetDtoRequest;
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

    @PostMapping("/add_set")
    public ResponseEntity<Integer> addSet(@RequestBody ShoppingSetDtoRequest shoppingSetDtoRequest,
                                          @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(shoppingService.addSetShopping(shoppingSetDtoRequest, userDetails));
    }

    @PatchMapping("/executed/{id}")
    public Integer executed(@PathVariable("id") @Min(1) Integer id,
                          @AuthenticationPrincipal UserDetails userDetails) {
        return shoppingService.executedShopping(id, userDetails);
    }

    @PatchMapping("/not_executed/{id}")
    public Integer notExecuted(@PathVariable("id") @Min(1) Integer id,
                          @AuthenticationPrincipal UserDetails userDetails) {
        return shoppingService.notExecutedShopping(id, userDetails);
    }
}
