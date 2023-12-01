package ru.sagiem.whattobuy.controller.rest;

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
import ru.sagiem.whattobuy.model.shopping.ShoppingProject;
import ru.sagiem.whattobuy.service.ShoppingProjectService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/shopping_project")
@RequiredArgsConstructor
@Tag(name = "Проект покупок")
public class ShoppingProjectController {

    private final ShoppingProjectService service;


     @Operation(
            summary = "Извлекает все проекты покупок для пользователя",
            description = "Извлечет результаты для всех Family group в которых находится пользователь, так и лично созданных проектов",
            tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ShoppingProjectDtoResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})

    @GetMapping("/show_all")
    public ResponseEntity<?> showAllByFamilyGroupAll(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.showAllUserCreatorOrFamilyGroup(userDetails));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ShoppingProjectDtoRequest request,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.add(request, userDetails));
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<ShoppingProjectDtoResponse> search(@PathVariable("id") @Min(1) Integer id,
                                                             @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(service.searchId(id, userDetails));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") @Min(1) Integer id,
                                    ShoppingProjectDtoRequest request,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.update(id, request, userDetails));


    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") @Min(1) Integer id, @AuthenticationPrincipal UserDetails userDetails){

        return ResponseEntity.ok(service.delete(id, userDetails));
    }

}
