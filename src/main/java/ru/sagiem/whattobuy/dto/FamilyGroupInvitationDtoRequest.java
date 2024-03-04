package ru.sagiem.whattobuy.dto;

import lombok.Data;

@Data
public class FamilyGroupInvitationDtoRequest {
    private Integer id;
    private String familyGroupName;
    private String userSender;

}
