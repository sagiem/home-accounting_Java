package ru.sagiem.whattobuy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sagiem.whattobuy.model.user.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoResponse {

    private Integer id;
    private Integer categoryId;
    private Integer subcategoryId;
    private String name;
    private Integer unitOfMeasurementId;
    private User userCreator;
}
