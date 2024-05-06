package ru.sagiem.whattobuy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sagiem.whattobuy.dto.ExceptionResponse;
import ru.sagiem.whattobuy.dto.FamilyGroupDtoResponse;
import ru.sagiem.whattobuy.dto.ShoppingProjectDtoResponse;
import ru.sagiem.whattobuy.dto.UserDTOResponse;
import ru.sagiem.whattobuy.service.UserService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Builder
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "Работа с пользователем")
public class UserController {
    private final UserService service;


    @Operation(
            summary = "Поиск пользователя по email",
            description = "Ищет пользователя по email и возвращает его id и информацию о нем"
            //tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = UserDTOResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})

    @GetMapping("/search/{email}")
    public ResponseEntity<UserDTOResponse> searchUserByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(service.searchUserByEmail(email));
    }
}
