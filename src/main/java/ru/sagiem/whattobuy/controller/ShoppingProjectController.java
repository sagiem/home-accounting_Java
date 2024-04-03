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
import ru.sagiem.whattobuy.model.shopping.Shopping;
import ru.sagiem.whattobuy.service.ShoppingProjectService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.sagiem.whattobuy.utils.ResponseUtils.*;

@RestController
@RequestMapping("/api/v1/shopping_project")
@RequiredArgsConstructor
@Tag(name = "Проект покупок")
public class ShoppingProjectController {

    private final ShoppingProjectService service;

    @Operation(
            summary = "Извлекает все проекты покупок для пользователя которые он создал, является владельцем",
            description = "Извлечет результаты для всех Family group в которых находится пользователь"
            // tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ShoppingProjectDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})

    @GetMapping("/show-all-my-creator")
    public ResponseEntity<List<ShoppingProjectDtoResponse>> showAllUserCreatorProjects(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.showAllUserCreatorProjects(userDetails));
    }

    @Operation(
            summary = "Извлекает все проекты покупок в группе",
            description = "Извлечет результаты для Family group, пользователь должен быть участником этой группы"
            // tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ShoppingProjectDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})

    @GetMapping("/show-all-group/{id}")
    public ResponseEntity<List<ShoppingProjectDtoResponse>> showAllGroupProjects(@PathVariable("id") @Min(1) Integer familyGroupId,
                                                                                 @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.showAllGroupProjects(familyGroupId, userDetails));
    }

    @Operation(
            summary = "Добавляет проект покупок",
            description = "Добавление проекта покупок в группу."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Integer.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @PostMapping("/add-in-group/{id}")
    public ResponseEntity<Integer> addInFamilyGroup(@PathVariable("id") @Min(1) Integer familyGroupId,
                                 @RequestBody ShoppingProjectDtoRequest request,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.addInFamilyGroup(familyGroupId, request, userDetails));
    }


    @Operation(
            summary = "Поиск проекта покупок по id",
            description = "Поиск проекта покупок по id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ShoppingProjectDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/search/{id}")
    public ResponseEntity<ShoppingProjectDtoResponse> searchId(@PathVariable("id") @Min(1) Integer id,
                                                             @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.searchId(id, userDetails));
    }

    @Operation(
            summary = "Обновление проекта покупок",
            description = "Обновление проекта покупок, обновить может только создатель проекта"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})

    @PatchMapping("/update/{id}")
    public ResponseEntity<SuccessResponse> update(@PathVariable("id") @Min(1) Integer id,
                                                  ShoppingProjectDtoRequest request,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        service.update(id, request, userDetails);
        return ResponseEntity.ok(getSuccessResponse(SHOPPING_PROJECT_UPDATE_MESSAGE, null));
    }

    @Operation(
            summary = "Удаление проекта покупок",
            description = "Удаление проекта покупо доступно только создателю проекта и если к проекту не привязаны покупки"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable("id") @Min(1) Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        service.delete(id, userDetails);
        return ResponseEntity.ok(getSuccessResponse(SHOPPING_PROJECT_DELETE_MESSAGE, null));
    }



    @Operation(
            summary = "Статистка проекта покупок",
            description = "Показывает сколько покупок, привязанных к проекту, в работе, выполенно и не выполнено"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ShoppingProjectDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/work_finish/{id}")
    public ResponseEntity<ShoppingProjectDtoWorkFinish> workFinishShoppingInProgect(@PathVariable("id") @Min(1) Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.workFinishShoppingInProgect(id, userDetails));
    }

    @Operation(
            summary = "Список покупок в работе, внутри проекта",
            description = "выводит список покупок, привязанных к проекту, которые находятся в работе"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ShoppingProjectDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/work/{id}")
    public ResponseEntity<List<Shopping>> workShoppingInProgect(@PathVariable("id") @Min(1) Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.workShoppingInProgect(id, userDetails));
    }

    @Operation(
            summary = "Список выполненных покупок, внутри проекта",
            description = "выводит список покупок, привязанных к проекту, которые выполнены"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ShoppingProjectDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/finish/{id}")
    public ResponseEntity<List<Shopping>> FinishShoppingInProgect(@PathVariable("id") @Min(1) Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.FinishShoppingInProgect(id, userDetails));
    }

    @Operation(
            summary = "Список не выполненных покупок, внутри проекта",
            description = "выводит список покупок, привязанных к проекту, которые не выполнены"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ShoppingProjectDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/not_work/{id}")
    public ResponseEntity<List<Shopping>> notWorkShoppingInProgect(@PathVariable("id") @Min(1) Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.notWorkShoppingInProgect(id, userDetails));
    }

}
