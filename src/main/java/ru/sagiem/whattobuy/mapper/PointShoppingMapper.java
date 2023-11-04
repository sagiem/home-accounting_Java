package ru.sagiem.whattobuy.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.sagiem.whattobuy.dto.PointShoppingDtoRequest;
import ru.sagiem.whattobuy.dto.PointShoppingDtoResponse;
import ru.sagiem.whattobuy.model.shopping.PointShopping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = PointShopping.class)
public interface PointShoppingMapper {




    PointShoppingDtoResponse convertToDTO(PointShopping pointShopping);
    PointShopping connertToModel(PointShoppingDtoRequest pointShoppingDtoRequest);

}
