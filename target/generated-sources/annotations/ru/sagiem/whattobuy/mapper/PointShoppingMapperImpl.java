package ru.sagiem.whattobuy.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.sagiem.whattobuy.dto.PointShoppingDtoResponse;
import ru.sagiem.whattobuy.model.shopping.PointShopping;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-21T17:21:03+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (BellSoft)"
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
}
