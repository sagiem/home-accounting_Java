package ru.sagiem.whattobuy.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.sagiem.whattobuy.dto.auth.ProductDto;
import ru.sagiem.whattobuy.model.product.Product;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = Product.class)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper( ProductMapper.class );

    @Mapping(source = "name", target = "CategoryProduct.id")
    ProductDto convertToDTO(Product product);
}
