package ru.sagiem.whattobuy.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Integer categoryId;
    private Integer subcategoryId;
    private String name;
    private Integer unitOfMeasurementId;
}
