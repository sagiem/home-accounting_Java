package ru.sagiem.whattobuy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/family-group-invitations")
@RequiredArgsConstructor
@Tag(name = "Работа с приглашениями в группы")
public class FamilyGroupInvitationsController {
}
