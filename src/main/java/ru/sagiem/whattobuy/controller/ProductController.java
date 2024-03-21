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
import ru.sagiem.whattobuy.dto.*;
import ru.sagiem.whattobuy.service.ProductService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.sagiem.whattobuy.utils.ResponseUtils.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "Работа с продуктом")
public class ProductController {

    private final ProductService service;

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

        return ResponseEntity.ok(service.showAll(userDetails, familyGroupId));
    }

    @Operation(
            summary = "Добавление прдукта",
            description = "Добавление происходит в группу"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Integer.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @PostMapping("/add/{id}")
    public ResponseEntity<?> add(@PathVariable("id") @Min(1) Integer familyGroupId,
                                 @RequestBody ProductDtoRequest request,
                                 @AuthenticationPrincipal UserDetails userDetails) {

        return service.add(familyGroupId, request, userDetails);
    }

    @Operation(
            summary = "Поиск продукт по id",
            description = "Пользователь должен быть в группе к которой принадлежит продукт"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CategoryProductDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/search/{id}")
    public ResponseEntity<ProductDtoResponse> searchId(@PathVariable("id") @Min(1) Integer id,
                                                       @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(service.searchId(id, userDetails));
    }

    @Operation(
            summary = "Обновление продукта",
            description = "Пользователь должен быть владельцем группы либо создателем продукта и участником группы"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @PatchMapping("/update/{id}")
    public ResponseEntity<SuccessResponse> update(@PathVariable("id") @Min(1) Integer id,
                                                  ProductDtoRequest productDtoRequest,
                                                  @AuthenticationPrincipal UserDetails userDetails) {

        String productName = service.update(id, productDtoRequest, userDetails);
        return ResponseEntity.ok(getSuccessResponse(PRODUCT_UPDATE_MESSAGE, productName));
    }

    @Operation(
            summary = "Удаляет продукт",
            description = "Пользователь должен быть владельцем группы либо создателем продукта и участником группы"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @DeleteMapping("/{Id}")
    public ResponseEntity<SuccessResponse> deleteGroup(@PathVariable("Id") @Min(1) Integer Id,
                                                       @AuthenticationPrincipal UserDetails userDetails) {
        String productName = service.delete(Id, userDetails);
        return ResponseEntity.ok(getSuccessResponse(PRODUCT_DELETE_MESSAGE, productName));
    }
}

