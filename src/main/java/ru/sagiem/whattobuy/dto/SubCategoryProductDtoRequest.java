package ru.sagiem.whattobuy.dto;

import lombok.Data;

@Data
public class SubCategoryProductDtoRequest {
    private String name;
    private Integer categoryProductId;
}
