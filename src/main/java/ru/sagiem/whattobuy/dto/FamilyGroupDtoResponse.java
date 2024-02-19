package ru.sagiem.whattobuy.dto;

import lombok.Data;

@Data
public class FamilyGroupDtoResponse {
    private String name;
    private Integer ownerUserId;
    private String ownerUsername;

}
