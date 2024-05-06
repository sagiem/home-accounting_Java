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
import ru.sagiem.whattobuy.model.shopping.Shopping;
import ru.sagiem.whattobuy.service.ShoppingService;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.sagiem.whattobuy.utils.ResponseUtils.*;

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
    @GetMapping("/show-showAllInProject/{id}")
    public ResponseEntity<List<ShoppingDtoResponse>> showAllInShoppingProgect(@PathVariable("id") @Min(1) Integer shoppingProgectId,
                                                                                   @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.showAllInShoppingProgect(shoppingProgectId, userDetails));
    }


    @Operation(
            summary = "Назначает покупку пользователю",
            description = "Если не указан проект покупок, то покупка назначается без проекта. Если не указан пользователь, то покупка назначается без пользователя в группу"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Integer.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/add-shopping")
    @PostMapping("/add-set-user")
    public ResponseEntity<Integer> addSet(@RequestBody ShoppingDtoRequest shoppingSetDtoRequest,
                                          @AuthenticationPrincipal UserDetails userDetails) {

        return  ResponseEntity.ok(service.addSet(shoppingSetDtoRequest, userDetails));

    }

    @Operation(
            summary = "Обновление покупки",
            description = "Пользователь должен быть владельцем группы либо создателем покупки и участником группы"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @PatchMapping("/update/{id}")
    public ResponseEntity<SuccessResponse> update(@PathVariable("id") @Min(1) Integer id,
                                                  ShoppingDtoRequest request,
                                                  @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(getSuccessResponse(SHOPPING_UPDATE_MESSAGE, null));
    }


    @Operation(
            summary = "Удаление покупки",
            description = "Пользователь должен быть владельцем группы либо создателем покупки и участником группы"
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
        service.delete(Id, userDetails);
        return ResponseEntity.ok(getSuccessResponse(SHOPPING_DELETE_MESSAGE, null));

    }


    @Operation(
            summary = "Переводит покупку в статус Выполнено",
            description = "Пользователь должен быть участником группы к которой принадлежит покупка"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @PatchMapping("/executed/{id}")
    public ResponseEntity<SuccessResponse> executed(@PathVariable("id") @Min(1) Integer id,
                            @AuthenticationPrincipal UserDetails userDetails) {
        service.executedShopping(id, userDetails);
        return ResponseEntity.ok(getSuccessResponse(SHOPPING_STATUS_EXECUTED_MESSAGE, null));
    }

    @Operation(
            summary = "Переводит покупку в статус Не выполнено",
            description = "Пользователь должен быть участником группы к которой принадлежит покупка"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @PatchMapping("/not_executed/{id}")
    public ResponseEntity<SuccessResponse> notExecuted(@PathVariable("id") @Min(1) Integer id,
                               @AuthenticationPrincipal UserDetails userDetails) {
        service.notExecutedShopping(id, userDetails);
        return ResponseEntity.ok(getSuccessResponse(SHOPPING_DELETE_NOT_EXECUTED_MESSAGE, null));
    }

     @Operation(
            summary = "Список покупок в работе, внутри проекта",
            description = "выводит список покупок, привязанных к проекту, которые находятся в работе"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ShoppingDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/work/{id}")
    public ResponseEntity<List<ShoppingDtoResponse>> workShoppingInProgect(@PathVariable("id") @Min(1) Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.workShoppingInProgect(id, userDetails));
    }

    @Operation(
            summary = "Список выполненных покупок, внутри проекта",
            description = "выводит список покупок, привязанных к проекту, которые выполнены"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ShoppingDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/finish/{id}")
    public ResponseEntity<List<ShoppingDtoResponse>> FinishShoppingInProgect(@PathVariable("id") @Min(1) Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.FinishShoppingInProgect(id, userDetails));
    }

    @Operation(
            summary = "Список не выполненных покупок, внутри проекта",
            description = "выводит список покупок, привязанных к проекту, которые не выполнены"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ShoppingDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/not_work/{id}")
    public ResponseEntity<List<ShoppingDtoResponse>> notWorkShoppingInProgect(@PathVariable("id") @Min(1) Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.notWorkShoppingInProgect(id, userDetails));
    }
}
