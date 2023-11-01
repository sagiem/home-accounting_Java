package ru.sagiem.whattobuy.controller.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.sagiem.whattobuy.dto.auth.PointShoppingDtoRequest;
import ru.sagiem.whattobuy.dto.auth.PointShoppingDtoResponse;
import ru.sagiem.whattobuy.dto.auth.ProductDtoRequest;
import ru.sagiem.whattobuy.dto.auth.ProductDtoResponse;
import ru.sagiem.whattobuy.service.PointShoppingService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/point_shopping")
@RequiredArgsConstructor
@Tag(name = "Работа с точками покупок")
public class PointShoppingController {

    private final PointShoppingService pointShoppingService;


        @GetMapping("/show_all")
    public ResponseEntity<List<PointShoppingDtoResponse>>show(@AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(pointShoppingService.showAll(userDetails));

    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody PointShoppingDtoRequest request,
                                 @AuthenticationPrincipal UserDetails userDetails) {

        pointShoppingService.addPointShopping(request, userDetails);
        return ResponseEntity.ok("Сообщение");
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<PointShoppingDtoResponse> searchName(@PathVariable("id") @Min(1) Integer id,
                                                         @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(pointShoppingService.searchId(id, userDetails));


    }


    @PatchMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") @Min(1) Integer id,
                                    PointShoppingDtoRequest pointShoppingDtoRequest,
                                    @AuthenticationPrincipal UserDetails userDetails) {


        return ResponseEntity.ok(pointShoppingService.update(id, pointShoppingDtoRequest, userDetails));

    }
}
