package ru.sagiem.whattobuy.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.sagiem.whattobuy.dto.CategoryProductDtoResponse;
import ru.sagiem.whattobuy.dto.SubCategoryProductDtoResponse;
import ru.sagiem.whattobuy.model.product.CategoryProduct;
import ru.sagiem.whattobuy.model.product.SubcategoryProduct;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = SubcategoryProduct.class)
public interface SubCategoryProductMapper {

    @Mapping(source = "categoryProduct.name", target = "categoryProductName")
    SubCategoryProductDtoResponse convertToDto(SubcategoryProduct subcategoryProduct);
}
