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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.sagiem.whattobuy.dto.*;
import ru.sagiem.whattobuy.service.ShoppingService;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.sagiem.whattobuy.utils.ResponseUtils.PRODUCT_UPDATE_MESSAGE;
import static ru.sagiem.whattobuy.utils.ResponseUtils.getSuccessResponse;

@RestController
@RequestMapping("/api/v1/shopping")
@RequiredArgsConstructor
@Tag(name = "Работа с покупками")
public class ShoppingController {

    private final ShoppingService service;


    @Operation(
            summary = "Возвращает все покупки назначенные пользователю,во всех группах и проектах покупок",
            description = "Возвращает все покупки назначенные пользователю,во всех группах и проектах покупок"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ShoppingDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/show-all-myExecutor")
    public ResponseEntity<List<ShoppingDtoResponse>> showAllMyExecutor(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.showAllMyExecutor(userDetails));
    }



    @Operation(
            summary = "Возвращает все покупки назначенные пользователю в проекте покупок",
            description = "Возвращает все покупки назначенные пользователю в проекте покупок"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ShoppingDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/show-myExecutorInShoppingProgect/{id}")
    public ResponseEntity<List<ShoppingDtoResponse>> showShoppingProgectMyExecutor(@PathVariable("id") @Min(1) Integer shoppingProgectId,
                                                                               @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.showMyExecutorinShoppingProgect(shoppingProgectId, userDetails));
    }

    @Operation(
            summary = "Возвращает все покупки в проекте покупок",
            description = "Пользователь должен быть участником группы к которой принадлежит группа покупок"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ShoppingDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/show-showAllInShoppingProgect/{id}")
    public ResponseEntity<List<ShoppingDtoResponse>> showAllInShoppingProgect(@PathVariable("id") @Min(1) Integer shoppingProgectId,
                                                                                   @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.showAllInShoppingProgect(shoppingProgectId, userDetails));
    }


    @Operation(
            summary = "Назначает покупку пользователю",
            description = "Если не указан проект покупок, то покупка назначается без проекта. Если не указан пользователь, то покупка назначается без пользователя"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/show-showAllInShoppingProgect/{id}")
    @PostMapping("/add-set-user")
    public ResponseEntity<SuccessResponse> addSet(@RequestBody ShoppingDtoRequest shoppingSetDtoRequest,
                                          @AuthenticationPrincipal UserDetails userDetails) {

        service.addSet(shoppingSetDtoRequest, userDetails);
        return ResponseEntity.ok(getSuccessResponse(PRODUCT_UPDATE_MESSAGE, productName));
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
