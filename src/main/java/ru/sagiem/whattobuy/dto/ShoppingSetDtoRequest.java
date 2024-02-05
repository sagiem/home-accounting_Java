package ru.sagiem.whattobuy.dto;

import lombok.Data;

@Data
public class ShoppingSetDtoRequest {
    private Integer productId;
    private Integer Volume;
    private Integer pointShoppingId;
    private Integer userExecutorId;
    private Integer familyGroupId;
}
