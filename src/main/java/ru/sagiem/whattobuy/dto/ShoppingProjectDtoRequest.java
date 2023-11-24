package ru.sagiem.whattobuy.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShoppingProjectDtoRequest {

    private String name;
    private LocalDateTime finishDate;
    private String comment;
    private Integer familyGroupId;
}
