package ru.sagiem.whattobuy.dto;

import lombok.Data;

@Data
public class FamilyGroupDtoResponse {
    private Integer id;
    private String name;
    private Integer creatorUserId;
    private String ownerUsername;

}
