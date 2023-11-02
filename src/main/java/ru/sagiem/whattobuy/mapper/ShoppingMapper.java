package ru.sagiem.whattobuy.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ru.sagiem.whattobuy.dto.auth.ProductDtoResponse;
import ru.sagiem.whattobuy.dto.auth.ShoppingDtoRequest;
import ru.sagiem.whattobuy.dto.auth.ShoppingDtoResponse;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.shopping.Shopping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShoppingMapper {



    @Mappings({
    @Mapping(source = "productId", target = "product.id"),
    @Mapping(source = "pointShoppingId", target = "pointShopping.id"),
    @Mapping(source = "familyGroupId", target = "familyGroup.id"),
    @Mapping(source = "userExecutorId", target = "userExecutor.id")
            })
    @InheritInverseConfiguration
    Shopping convertToModel(ShoppingDtoRequest shoppingDtoRequest);

    @Mappings({
            @Mapping(source = "product.id", target = "productId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "pointShopping.id", target = "pointShoppingId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "familyGroup.id", target = "familyGroupId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "userCreator.id", target = "userCreatorId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "userExecutor.id", target = "userExecutorId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "shoppingStatus", target = "shoppingStatus", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    })
    ShoppingDtoResponse convertToDto(Shopping shopping);
}
