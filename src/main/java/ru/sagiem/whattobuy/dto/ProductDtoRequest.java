package ru.sagiem.whattobuy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sagiem.whattobuy.model.user.FamilyGroup;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoRequest {

    private String name;
    private Integer categoryId;
    private Integer subcategoryId;
    private String unitOfMeasurement;
    private Integer familyGroupId;
}
