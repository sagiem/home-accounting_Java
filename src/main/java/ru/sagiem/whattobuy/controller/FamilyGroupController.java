package ru.sagiem.whattobuy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.sagiem.whattobuy.dto.FamilyGroupDtoRequest;
import ru.sagiem.whattobuy.dto.FamilyGroupDtoResponse;
import ru.sagiem.whattobuy.dto.ProductDtoRequest;
import ru.sagiem.whattobuy.dto.UserDTOResponse;
import ru.sagiem.whattobuy.service.FamilyGroupService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/family-group")
@RequiredArgsConstructor
@Tag(name = "Работа с группами")
public class FamilyGroupController {

    private final FamilyGroupService service;


    @GetMapping("/show-all-my-created-groups")
    public ResponseEntity<List<FamilyGroupDtoResponse>> showAllMyCreatedGroups(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.showAllMyCreatedGroups(userDetails));
    }

    @GetMapping("/show-all-my-groups")
    public ResponseEntity<List<FamilyGroupDtoResponse>> showAllMyGroups(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.showAllMyGroups(userDetails));
    }

    @GetMapping("/show-all-users-in-group")
    public ResponseEntity<List<UserDTOResponse>> showAllUsersInGroup(@AuthenticationPrincipal UserDetails userDetails, Integer familyGroupId) {
        return ResponseEntity.ok(service.showAllUsersInGroup(userDetails, familyGroupId));}

    @PostMapping("/add-new-group")
    public ResponseEntity<Integer> addNewGroup(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestBody FamilyGroupDtoRequest request) {
        return ResponseEntity.ok(service.addNewGroup(userDetails, request));
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<FamilyGroupDtoResponse> search(@PathVariable("id") @Min(1) Integer id,
                                                         @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.search(id, userDetails));

    }

    @PatchMapping("/rename-group/{id}")
    public ResponseEntity<Integer> renameGroup(@PathVariable("id") @Min(1) Integer id,
                                               @AuthenticationPrincipal UserDetails userDetails,
                                               @RequestBody String newName) {
        return ResponseEntity.ok(service.renameGroup(id, userDetails, newName));
    }

    @PostMapping("send-invitation-to-user")
    public ResponseEntity<?> sendInvitation(@AuthenticationPrincipal UserDetails userDetails,
                                                  @RequestBody Integer productId,
                                                  @RequestBody String email) {
        return ResponseEntity.ok(service.sendInvitation(userDetails, productId, email));
    }

    @PostMapping("delete-user-in-group")
    public ResponseEntity<?> deleteUserInGroup(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestBody Integer groupId,
                                               @RequestBody Integer userId) {
        return ResponseEntity.ok(service.deleteUserInGroup(userDetails, groupId, userId));
    }




}
