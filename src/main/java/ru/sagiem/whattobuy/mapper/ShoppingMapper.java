package ru.sagiem.whattobuy.mapper;

import org.mapstruct.*;
import ru.sagiem.whattobuy.dto.ShoppingDtoResponse;
import ru.sagiem.whattobuy.model.shopping.Shopping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShoppingMapper {


    @Mappings({
            @Mapping(source = "createDate", target = "createDate", dateFormat = "dd-MM-yyyy HH:mm:ss",
                    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "lastModified", target = "lastModified", dateFormat = "dd-MM-yyyy HH:mm:ss",
                    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "executorDate", target = "executorDate", dateFormat = "dd-MM-yyyy HH:mm:ss",
                    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "product.id", target = "productId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "pointShopping.id", target = "pointShoppingId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
//            @Mapping(source = "familyGroup.id", target = "familyGroupId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "userCreator.id", target = "userCreatorId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "userExecutor.id", target = "userExecutorId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "shoppingStatus", target = "shoppingStatus", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    })
    ShoppingDtoResponse convertToDto(Shopping shopping);
}
