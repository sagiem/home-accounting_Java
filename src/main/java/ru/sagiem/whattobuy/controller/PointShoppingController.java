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
import ru.sagiem.whattobuy.service.PointShoppingService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.sagiem.whattobuy.utils.ResponseUtils.*;

@RestController
@RequestMapping("/api/v1/point_shopping")
@RequiredArgsConstructor
@Tag(name = "Работа с точками покупок")
public class PointShoppingController {

    private final PointShoppingService service;


    @Operation(
            summary = "Возвращает все точки покупок для группы",
            description = "Пользователь должен быть участником группы"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = PointShoppingDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/show-all-for-group/{id}")
    public ResponseEntity<List<PointShoppingDtoResponse>> showAllForGroup(@PathVariable("id") @Min(1) Integer id,
                                                                          @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.showAllForGroup(id, userDetails));

    }

    @Operation(
            summary = "Возвращает все точки покупок для пользователя",
            description = "Все точки которые пользователь создал"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = PointShoppingDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/show-all-my-created")
    public ResponseEntity<List<PointShoppingDtoResponse>> showAllMyCreated(@AuthenticationPrincipal UserDetails userDetails) {

            return ResponseEntity.ok(service.showAllMyCreated(userDetails));

        }


    @Operation(
            summary = "Добавление точки покупки",
            description = "Добавление происходит в группу"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Integer.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @PostMapping("/add/{id}")
    public ResponseEntity<Integer> add(@PathVariable("id") @Min(1) Integer id,
                                @RequestBody PointShoppingDtoRequest request,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.addPointShopping(id, request, userDetails));
    }


    @Operation(
            summary = "Поиск точки покупки по id",
            description = "Пользователь должен быть в группе к которой принадлежит точка покупки"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = PointShoppingDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/search/{id}")
    public ResponseEntity<PointShoppingDtoResponse> searchName(@PathVariable("id") @Min(1) Integer id,
                                                               @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(service.searchId(id, userDetails));


    }


    @Operation(
            summary = "Обновление точки покупки",
            description = "Пользователь должен быть в группе к которой принадлежит точка покупки"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @PatchMapping("/update/{id}")
    public ResponseEntity<SuccessResponse> update(@PathVariable("id") @Min(1) Integer id,
                                                  PointShoppingDtoRequest pointShoppingDtoRequest,
                                                  @AuthenticationPrincipal UserDetails userDetails) {


        String pointShopingName = service.update(id, pointShoppingDtoRequest, userDetails);
        return ResponseEntity.ok(getSuccessResponse(POINT_SHOPING_UPDATE_MESSAGE, pointShopingName));

    }

    @Operation(
            summary = "Удаляет точку покупки",
            description = "Удаляет точку покупки"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @DeleteMapping("/{Id}")
    public ResponseEntity<SuccessResponse> deleteGroup(@PathVariable("Id") @Min(1) Integer Id,
                                                       @AuthenticationPrincipal UserDetails userDetails){
        String familyGroupName = service.delete(Id, userDetails);
        return ResponseEntity.ok(getSuccessResponse(FAMILY_GROUP_DELETE_MESSAGE, familyGroupName));

    }

}
