package ru.sagiem.whattobuy.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.sagiem.whattobuy.dto.PointShoppingDtoRequest;
import ru.sagiem.whattobuy.dto.PointShoppingDtoResponse;
import ru.sagiem.whattobuy.model.shopping.PointShopping;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-10T11:24:10+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8.1 (BellSoft)"
)
@Component
public class PointShoppingMapperImpl implements PointShoppingMapper {

    @Override
    public PointShoppingDtoResponse convertToDTO(PointShopping pointShopping) {
        if ( pointShopping == null ) {
            return null;
        }

        PointShoppingDtoResponse pointShoppingDtoResponse = new PointShoppingDtoResponse();

        return pointShoppingDtoResponse;
    }

    @Override
    public PointShopping connertToModel(PointShoppingDtoRequest pointShoppingDtoRequest) {
        if ( pointShoppingDtoRequest == null ) {
            return null;
        }

        PointShopping.PointShoppingBuilder pointShopping = PointShopping.builder();

        return pointShopping.build();
    }
}
