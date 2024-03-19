package ru.sagiem.whattobuy.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.sagiem.whattobuy.dto.CategoryProductDtoResponse;
import ru.sagiem.whattobuy.model.product.CategoryProduct;
import ru.sagiem.whattobuy.model.shopping.PointShopping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = CategoryProduct.class)
public interface CategoryProductMapper {

    @Mapping(source = "familyGroup.name", target = "familyGroup")
    CategoryProductDtoResponse convertToDto(CategoryProduct categoryProduct);
}
