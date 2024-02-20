package ru.sagiem.whattobuy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sagiem.whattobuy.dto.FamilyGroupDtoResponse;
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
    public ResponseEntity<List<FamilyGroupDtoResponse>> showAllMyCreated(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.showAllMyCreated(userDetails));
    }

    @GetMapping("/show-all-my-groups")
    public ResponseEntity<List<FamilyGroupDtoResponse>> showAllMyGroups(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.showAllMyGroups(userDetails));
    }

    @GetMapping("/show-all-users-in-group")
    public ResponseEntity<List<UserDTOResponse>> showAllUsersInGroup(@AuthenticationPrincipal UserDetails userDetails, Integer familyGroupId) {
        return ResponseEntity.ok(service.showAllUsersInGroup(userDetails, familyGroupId));}

}
