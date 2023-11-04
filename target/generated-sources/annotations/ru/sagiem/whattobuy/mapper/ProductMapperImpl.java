package ru.sagiem.whattobuy.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.sagiem.whattobuy.dto.auth.ProductDtoResponse;
import ru.sagiem.whattobuy.model.product.CategoryProduct;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.product.SubcategoryProduct;
import ru.sagiem.whattobuy.model.product.UnitOfMeasurementProduct;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-04T15:32:14+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8.1 (BellSoft)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDtoResponse convertToDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDtoResponse.ProductDtoResponseBuilder productDtoResponse = ProductDtoResponse.builder();

        productDtoResponse.categoryId( productCategoryId( product ) );
        productDtoResponse.subcategoryId( productSubcategoryId( product ) );
        productDtoResponse.unitOfMeasurementId( productUnitOfMeasurementId( product ) );
        productDtoResponse.id( product.getId() );
        productDtoResponse.name( product.getName() );
        productDtoResponse.userCreator( product.getUserCreator() );

        return productDtoResponse.build();
    }

    private Integer productCategoryId(Product product) {
        if ( product == null ) {
            return null;
        }
        CategoryProduct category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        Integer id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Integer productSubcategoryId(Product product) {
        if ( product == null ) {
            return null;
        }
        SubcategoryProduct subcategory = product.getSubcategory();
        if ( subcategory == null ) {
            return null;
        }
        Integer id = subcategory.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Integer productUnitOfMeasurementId(Product product) {
        if ( product == null ) {
            return null;
        }
        UnitOfMeasurementProduct unitOfMeasurement = product.getUnitOfMeasurement();
        if ( unitOfMeasurement == null ) {
            return null;
        }
        Integer id = unitOfMeasurement.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
