package ru.sagiem.whattobuy.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.sagiem.whattobuy.dto.PointShoppingDtoRequest;
import ru.sagiem.whattobuy.dto.PointShoppingDtoResponse;
import ru.sagiem.whattobuy.model.shopping.PointShopping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = PointShopping.class)
public interface PointShoppingMapper {



    @Mapping(source = "userCreator.username", target = "userCreator")
    @Mapping(source = "familyGroup.name", target = "familyGroup")
    @Mapping(source = "lastModifiedUser.username", target = "lastModifiedUser", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    PointShoppingDtoResponse convertToDTO(PointShopping pointShopping);

}
