package ru.sagiem.whattobuy.dto;

import lombok.Data;

@Data
public class SubCategoryProductDtoRequest {
    private Integer id;
    private String name;
    private Integer categoryProductId;
}
