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
import ru.sagiem.whattobuy.service.FamilyGroupService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.sagiem.whattobuy.utils.Constants.FAMILY_GROUP;
import static ru.sagiem.whattobuy.utils.ResponseUtils.*;

@RestController
@RequestMapping("/api/v1/family-group")
@RequiredArgsConstructor
@Tag(name = "Работа с группами")
public class FamilyGroupController {

    private final FamilyGroupService service;


    @Operation(
            summary = "Возвращает список групп где пользователь является владельцем",
            description = "Возвращает все группы где пользователь является владельцем"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = FamilyGroupDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/show-all-my-created-groups")
    public ResponseEntity<List<FamilyGroupDtoResponse>> showAllMyCreatedGroups(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.showAllMyCreatedGroups(userDetails));
    }

    @Operation(
            summary = "Возвращает все группы в которых состоит пользователь",
            description = "Возвращает все группы в которых состоит пользователь"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = FamilyGroupDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})

    @GetMapping("/show-all-my-groups")
    public ResponseEntity<List<FamilyGroupDtoResponse>> showAllMyGroups(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.showAllMyGroups(userDetails));
    }

    @Operation(
            summary = "Возвращает всех пользователей состаящих в группе",
            description = "Возвращает всех пользователей состаящих в группе"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = UserDTOResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/show-all-users-in-group")
    public ResponseEntity<List<UserDTOResponse>> showAllUsersInGroup(@AuthenticationPrincipal UserDetails userDetails, Integer familyGroupId) {
        return ResponseEntity.ok(service.showAllUsersInGroup(userDetails, familyGroupId));
    }

    @Operation(
            summary = "Создает группу",
            description = "Создает группу и добавляет туда пользователя который создал группу"
            //tags = "post"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Integer.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @PostMapping("/add-new-group")
    public ResponseEntity<Integer> addNewGroup(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestBody FamilyGroupDtoRequest request) {
        return ResponseEntity.ok(service.addNewGroup(userDetails, request));
    }

    @Operation(
            summary = "Поиск группы по id",
            description = "Ищет группу по id и возвращает информацию о ней"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = FamilyGroupDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/search/{id}")
    public ResponseEntity<FamilyGroupDtoResponse> search(@PathVariable("id") @Min(1) Integer FamilyGroupid,
                                                         @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.search(FamilyGroupid, userDetails));

    }

    @Operation(
            summary = "Переименовывает группу",
            description = "Переименовывает группу, на это имеет право только владелец группы"
            //tags = "get"
    )
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
//            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
//            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @PatchMapping("/rename-group/{id}")
    public ResponseEntity<SuccessResponse> renameGroup(@PathVariable("id") @Min(1) Integer id,
                                                       @AuthenticationPrincipal UserDetails userDetails,
                                                       @RequestBody String newName) {
        service.renameGroup(userDetails, id, newName);
        return ResponseEntity.ok(getSuccessResponse(UPDATE_MESSAGE, FAMILY_GROUP));
    }

    @Operation(
            summary = "Отправляет приглашение в группу",
            description = "Отправляет приглашение в группу, пользователь находится по email"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})

    @PostMapping("/send-invitation/{GroupId}/{userId}")
    public ResponseEntity<SuccessResponse> sendInvitation(@PathVariable("GroupId") @Min(1) Integer GroupId,
                                                          @PathVariable("userId") @Min(1) Integer userId,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        service.sendInvitation(userDetails, GroupId, userId);
        return ResponseEntity.ok(getSuccessResponse(SEND_INVITATION, FAMILY_GROUP));
    }

    @Operation(
            summary = "Показывает все приглашения которые отправил пользователь",
            description = "Показывает все приглашения которые отправил пользователь"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Integer.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})

    @GetMapping("/show-all-my-created-invitation")
    public ResponseEntity<List<Integer>> showAllMyCreatedInvitation(@AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(service.showAllMyCreatedInvitation(userDetails));
    }

    @Operation(
            summary = "Удаляет приглашение",
            description = "Приглашение может удалить как отправитель так и получатель"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @DeleteMapping("/deleteInvitation/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable("id") @Min(1) Integer familyGroupInvitationId, @AuthenticationPrincipal UserDetails userDetails) {
        service.deleteInvitation(familyGroupInvitationId, userDetails);
        return ResponseEntity.ok(getSuccessResponse(DELETE_INVITATION, FAMILY_GROUP));
    }

    @Operation(
            summary = "Принимает заявку в группу",
            description = "Принимает зафвку в группу, добавляет пользователя в эту группу и удаляет заявку"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @PostMapping("/accept-invitation")
    public ResponseEntity<SuccessResponse> acceptInvitation(@AuthenticationPrincipal UserDetails userDetails, Integer familyGroupInvitationId) {
        service.acceptInvitation(userDetails, familyGroupInvitationId);
        return ResponseEntity.ok(getSuccessResponse(ACCEPT_INVITATION, FAMILY_GROUP));
    }

    @Operation(
            summary = "Удаляет пользователя из группы",
            description = "Удаляет пользователя из группы"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @DeleteMapping("/deleteUserInGroup/{groupId}/{userId}")
    public ResponseEntity<SuccessResponse> deleteUserInGroup(@PathVariable("groupId") @Min(1) Integer groupId,
                                                             @PathVariable("userId") @Min(1) Integer userId,
                                                             @AuthenticationPrincipal UserDetails userDetails) {
        service.deleteUserInGroup(userDetails, groupId, userId);
        return ResponseEntity.ok(getSuccessResponse(DELETE_USER_IN_GROUP, FAMILY_GROUP));
    }


}
