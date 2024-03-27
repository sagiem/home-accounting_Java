package ru.sagiem.whattobuy.dto;

import lombok.Data;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.shopping.PointShopping;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;
@Data
public class ShoppingDtoRequest {
    private Integer productId;
    private Integer volume;
    private Integer pointShoppingId;
    private Integer familyGroupId;
    private Integer shoppingProjectId;
    private Integer userExecutorId;
}
