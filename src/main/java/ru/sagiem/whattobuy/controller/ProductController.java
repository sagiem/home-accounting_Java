package ru.sagiem.whattobuy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.sagiem.whattobuy.dto.CategoryProductDtoResponse;
import ru.sagiem.whattobuy.dto.ExceptionResponse;
import ru.sagiem.whattobuy.dto.ProductDtoRequest;
import ru.sagiem.whattobuy.dto.ProductDtoResponse;
import ru.sagiem.whattobuy.service.ProductService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "Работа с продуктом")
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "Возвращает все продукты для группы",
            description = "Возвращает все продукты для группы для группы"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ProductDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/show_all/{id}")
    public ResponseEntity<?> showAll(@PathVariable("id") @Min(1) Integer familyGroupId,
                                     @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(productService.showAll(userDetails, familyGroupId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ProductDtoRequest request,
                                 @AuthenticationPrincipal UserDetails userDetails) {

        return productService.addProduct(request, userDetails);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<ProductDtoResponse> searchId(@PathVariable("id") @Min(1) Integer id,
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
