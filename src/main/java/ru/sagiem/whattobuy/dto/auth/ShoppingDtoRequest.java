package ru.sagiem.whattobuy.dto.auth;

import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.shopping.PointShopping;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;

public class ShoppingDtoRequest {
    private Integer productId;
    private Double price;
    private Integer Volume;
    private Integer pointShoppingId;
    private Integer familyGroupId;
    private Integer userExecutorId;
}
