package ru.sagiem.whattobuy.dto;

import java.time.LocalDateTime;

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
