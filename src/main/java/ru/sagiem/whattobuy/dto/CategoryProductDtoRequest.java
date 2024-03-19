package ru.sagiem.whattobuy.dto;

import lombok.Data;

@Data
public class CategoryProductDtoRequest {
    private String name;
    private Integer familyGroupId;
}
