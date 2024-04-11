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
import ru.sagiem.whattobuy.service.SubcategoryProductService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.sagiem.whattobuy.utils.ResponseUtils.*;

@RestController
@RequestMapping("/api/v1/subcategory-product")
@RequiredArgsConstructor
@Tag(name = "Работа с подкатегориями продуктов")
public class SubcategoryProductController {
    private final SubcategoryProductService service;

    @Operation(
            summary = "Возвращает подкатегорию продуктов",
            description = "Возвращает все подкатегоррии для заданной категории, пользователь состоит в той же группе к которой принадлежит категория, либо это общая категория"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = SubCategoryProductDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/show-all-for-subcategory/{id}")
    public ResponseEntity<List<SubCategoryProductDtoResponse>> showAll(@PathVariable("id") @Min(1) Integer CategoryProductId,
                                                                       @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.showAll(CategoryProductId, userDetails));

    }

    @Operation(
            summary = "Добавление субкатегории",
            description = "Подкатегория добавляется в категорию продукта"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Integer.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @PostMapping("/add")
    public ResponseEntity<Integer> add(@RequestBody SubCategoryProductDtoRequest request,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.create(request, userDetails));
    }

    @Operation(
            summary = "Поиск подкатегории по id",
            description = "Пользователь должен быть в группе к которой принадлежит подкатегория"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = SubCategoryProductDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/search/{id}")
    public ResponseEntity<SubCategoryProductDtoResponse> searchId(@PathVariable("id") @Min(1) Integer id,
                                                                  @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(service.searchId(id, userDetails));
    }

    @Operation(
            summary = "Обновление подкатегории",
            description = "Пользователь должен быть в группе к которой принадлежит подкатегория"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @PatchMapping("/update/{id}/{name}")
    public ResponseEntity<SuccessResponse> update(@PathVariable("id") @Min(1) Integer id,
                                                  @PathVariable("name") @Min(1) String name,
                                                  @AuthenticationPrincipal UserDetails userDetails) {


        String subсategoryProductName = service.update(id, name, userDetails);
        return ResponseEntity.ok(getSuccessResponse(SUBCATEGORY_PRODUCT_UPDATE_MESSAGE, subсategoryProductName));
    }

    @Operation(
            summary = "Удаляет подкатегорию",
            description = "Удаляет подкатегорию по id"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @DeleteMapping("/{Id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable("Id") @Min(1) Integer Id,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        String сategoryProductName = service.delete(Id, userDetails);
        return ResponseEntity.ok(getSuccessResponse(SUBCATEGORY_PRODUCT_DELETE_MESSAGE, сategoryProductName));

    }
}
