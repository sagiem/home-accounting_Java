package ru.sagiem.whattobuy.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.sagiem.whattobuy.dto.auth.ShoppingDtoResponse;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.shopping.PointShopping;
import ru.sagiem.whattobuy.model.shopping.Shopping;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-04T15:32:14+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8.1 (BellSoft)"
)
@Component
public class ShoppingMapperImpl implements ShoppingMapper {

    @Override
    public ShoppingDtoResponse convertToDto(Shopping shopping) {
        if ( shopping == null ) {
            return null;
        }

        ShoppingDtoResponse shoppingDtoResponse = new ShoppingDtoResponse();

        shoppingDtoResponse.setDataCreatorShoping( shopping.getDataCreatorShoping() );
        shoppingDtoResponse.setDataExecutedShoping( shopping.getDataExecutedShoping() );
        shoppingDtoResponse.setProductId( shoppingProductId( shopping ) );
        shoppingDtoResponse.setPointShoppingId( shoppingPointShoppingId( shopping ) );
        shoppingDtoResponse.setFamilyGroupId( shoppingFamilyGroupId( shopping ) );
        shoppingDtoResponse.setUserCreatorId( shoppingUserCreatorId( shopping ) );
        shoppingDtoResponse.setUserExecutorId( shoppingUserExecutorId( shopping ) );
        if ( shopping.getShoppingStatus() != null ) {
            shoppingDtoResponse.setShoppingStatus( shopping.getShoppingStatus().name() );
        }
        shoppingDtoResponse.setId( shopping.getId() );
        shoppingDtoResponse.setVolume( shopping.getVolume() );

        return shoppingDtoResponse;
    }

    private Integer shoppingProductId(Shopping shopping) {
        if ( shopping == null ) {
            return null;
        }
        Product product = shopping.getProduct();
        if ( product == null ) {
            return null;
        }
        Integer id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Integer shoppingPointShoppingId(Shopping shopping) {
        if ( shopping == null ) {
            return null;
        }
        PointShopping pointShopping = shopping.getPointShopping();
        if ( pointShopping == null ) {
            return null;
        }
        Integer id = pointShopping.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Integer shoppingFamilyGroupId(Shopping shopping) {
        if ( shopping == null ) {
            return null;
        }
        FamilyGroup familyGroup = shopping.getFamilyGroup();
        if ( familyGroup == null ) {
            return null;
        }
        Integer id = familyGroup.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Integer shoppingUserCreatorId(Shopping shopping) {
        if ( shopping == null ) {
            return null;
        }
        User userCreator = shopping.getUserCreator();
        if ( userCreator == null ) {
            return null;
        }
        Integer id = userCreator.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Integer shoppingUserExecutorId(Shopping shopping) {
        if ( shopping == null ) {
            return null;
        }
        User userExecutor = shopping.getUserExecutor();
        if ( userExecutor == null ) {
            return null;
        }
        Integer id = userExecutor.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
