package ru.sagiem.whattobuy.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.sagiem.whattobuy.dto.auth.ProductDtoResponse;
import ru.sagiem.whattobuy.dto.auth.ShoppingDtoRequest;
import ru.sagiem.whattobuy.dto.auth.ShoppingDtoResponse;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.shopping.Shopping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = Shopping.class)
public interface ShoppingMapper {



    @Mapping(source = "productId", target = "product.id")
    @Mapping(source = "pointShoppingId", target = "pointShopping.id")
    @Mapping(source = "familyGroupId", target = "familyGroup.id")
    @Mapping(source = "userExecutorId", target = "userExecutor.id")
    Shopping convertToModel(ShoppingDtoRequest shoppingDtoRequest);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "pointShopping.id", target = "pointShoppingId")
    @Mapping(source = "familyGroup.id", target = "familyGroupId")
    @Mapping(source = "userCreator.id", target = "userCreatorId")
    @Mapping(source = "userExecutor.id", target = "userExecutorId")
    @Mapping(source = "shoppingStatus.name", target = "shoppingStatus")
    ShoppingDtoResponse convertToDto(Shopping shopping);
}
