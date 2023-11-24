package ru.sagiem.whattobuy.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShoppingProjectDtoResponse {
    private Integer id;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime lastModified;
    private LocalDateTime finishDate;
    private boolean active;
    private String comment;
    private Integer userCreatorId;
    private Integer familyGroupId;
}
