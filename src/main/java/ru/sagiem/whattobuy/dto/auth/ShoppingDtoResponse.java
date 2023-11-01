package ru.sagiem.whattobuy.dto.auth;

import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.shopping.PointShopping;
import ru.sagiem.whattobuy.model.shopping.ShoppingStatus;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;

import java.time.LocalDateTime;

public class ShoppingDtoResponse {
    private Integer id;
    private LocalDateTime dataCreatorShoping;
    private LocalDateTime dataExecutedShoping;
    private Integer productId;
    private Double price;
    private Integer Volume;
    private Integer pointShoppingId;
    private Integer familyGroupId;
    private Integer userCreatorId;
    private Integer userExecutorId;
    private String shoppingStatus;

}
