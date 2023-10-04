package ru.sagiem.whattobuy.dto.auth;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sagiem.whattobuy.model.product.CategoryProduct;
import ru.sagiem.whattobuy.model.product.SubcategoryProduct;
import ru.sagiem.whattobuy.model.product.UnitOfMeasurementProduct;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductAddRequest {

    private Integer categoryId;
    private Integer subcategoryId;
    private String name;
    private Integer unitOfMeasurementId;
}
