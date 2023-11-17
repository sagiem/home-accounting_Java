package ru.sagiem.whattobuy.dto;

import lombok.Data;

@Data
public class PointShoppingDtoRequest {
    private String name;
    private String address;
    private Integer familyGroup;
    private String comment;

}
