package ru.sagiem.whattobuy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sagiem.whattobuy.model.user.User;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoResponse {

    private Integer id;
    private String name;
    private Integer categoryId;
    private Integer subcategoryId;
    private String unitOfMeasurement;
    private String userCreator;
    private LocalDateTime createDate;
}
