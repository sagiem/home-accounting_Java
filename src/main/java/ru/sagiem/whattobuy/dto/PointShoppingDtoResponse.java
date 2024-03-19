package ru.sagiem.whattobuy.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PointShoppingDtoResponse {
    private Integer id;
    private String name;
    private String address;
    private String comment;
    private String familyGroup;
    private LocalDateTime createDate;
    private LocalDateTime lastModified;
    private String userCreator;
    private String lastModifiedUser;
}
