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

//    @GetMapping()
//    public List<ShoppingDtoResponse> getByPeriod(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime dateStart,
//                                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime dateEnd,
//                                                 @RequestParam(required = false) List<Integer> productId,
//                                                 @RequestParam(required = false) List<Integer> pointShoppingId,
//                                                 @RequestParam(required = false) List<Integer> familyGroupId,
//                                                 @RequestParam(required = false) List<Integer> userCreatorId,
//                                                 @RequestParam(required = false) List<Integer> userExecutorId,
//                                                 @RequestParam(required = false) List<Integer> shoppingProjectId,
//                                                 @RequestParam(required = false) List<String> shoppingStatus,
//                                                 @AuthenticationPrincipal UserDetails userDetails) {
//
//        return service.getByMyPeriod(dateStart, dateEnd, productId, pointShoppingId, familyGroupId, userCreatorId, userExecutorId, shoppingProjectId, shoppingStatus, userDetails);
//    }

    @PostMapping("/add")
    public ResponseEntity<Integer> add(@RequestBody ShoppingDtoRequest shoppingDtoRequest,
                                       @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(service.addShopping(shoppingDtoRequest, userDetails));
    }

    @PostMapping("/add_set")
    public ResponseEntity<Integer> addSet(@RequestBody ShoppingSetDtoRequest shoppingSetDtoRequest,
                                          @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(service.addSetShopping(shoppingSetDtoRequest, userDetails));
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
