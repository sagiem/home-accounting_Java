package ru.sagiem.whattobuy.controller.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.sagiem.whattobuy.dto.ShoppingDtoRequest;
import ru.sagiem.whattobuy.dto.ShoppingDtoResponse;
import ru.sagiem.whattobuy.dto.ShoppingSetDtoRequest;
import ru.sagiem.whattobuy.service.ShoppingService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shopping")
@RequiredArgsConstructor
@Tag(name = "Покупки")
public class ShoppingController {

    private final ShoppingService service;


    @GetMapping("/show-all-my-creator")
    public ResponseEntity<List<ShoppingDtoResponse>> showAllMyCreatores(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.showAllMyCreatores(userDetails));
    }

    @PostMapping("/add-my")
    public ResponseEntity<Integer> add(@RequestBody ShoppingDtoRequest shoppingDtoRequest,
                                       @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(service.addMyShopping(shoppingDtoRequest, userDetails));
    }

    @PostMapping("/add-set-user")
    public ResponseEntity<Integer> addSet(@RequestBody ShoppingSetDtoRequest shoppingSetDtoRequest,
                                          @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(service.addSetUserShopping(shoppingSetDtoRequest, userDetails));
    }

    @PostMapping("/add-set-family-group")
    public ResponseEntity<Integer> addSetFamilyGroup(@RequestBody ShoppingSetDtoRequest shoppingSetDtoRequest,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.addSetFamilyGroupShopping(shoppingSetDtoRequest, userDetails));
    }

    @PatchMapping("/executed/{id}")
    public Integer executed(@PathVariable("id") @Min(1) Integer id,
                            @AuthenticationPrincipal UserDetails userDetails) {
        return service.executedShopping(id, userDetails);
    }

    @PatchMapping("/not_executed/{id}")
    public Integer notExecuted(@PathVariable("id") @Min(1) Integer id,
                               @AuthenticationPrincipal UserDetails userDetails) {
        return service.notExecutedShopping(id, userDetails);
    }
}
