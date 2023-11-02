package ru.sagiem.whattobuy.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.sagiem.whattobuy.dto.auth.ShoppingDtoRequest;
import ru.sagiem.whattobuy.dto.auth.ShoppingDtoResponse;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.shopping.PointShopping;
import ru.sagiem.whattobuy.model.shopping.Shopping;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-02T12:57:58+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8.1 (BellSoft)"
)
@Component
public class ShoppingMapperImpl implements ShoppingMapper {

    @Override
    public Shopping convertToModel(ShoppingDtoRequest shoppingDtoRequest) {
        if ( shoppingDtoRequest == null ) {
            return null;
        }

        Shopping.ShoppingBuilder shopping = Shopping.builder();

        shopping.product( shoppingDtoRequestToProduct( shoppingDtoRequest ) );
        shopping.pointShopping( shoppingDtoRequestToPointShopping( shoppingDtoRequest ) );
        shopping.familyGroup( shoppingDtoRequestToFamilyGroup( shoppingDtoRequest ) );
        shopping.userExecutor( shoppingDtoRequestToUser( shoppingDtoRequest ) );
        shopping.price( shoppingDtoRequest.getPrice() );

        return shopping.build();
    }

    @Override
    public ShoppingDtoResponse convertToDto(Shopping shopping) {
        if ( shopping == null ) {
            return null;
        }

        ShoppingDtoResponse shoppingDtoResponse = new ShoppingDtoResponse();

        shoppingDtoResponse.setProductId( shoppingProductId( shopping ) );
        shoppingDtoResponse.setPointShoppingId( shoppingPointShoppingId( shopping ) );
        shoppingDtoResponse.setFamilyGroupId( shoppingFamilyGroupId( shopping ) );
        shoppingDtoResponse.setUserCreatorId( shoppingUserCreatorId( shopping ) );
        shoppingDtoResponse.setUserExecutorId( shoppingUserExecutorId( shopping ) );
        if ( shopping.getShoppingStatus() != null ) {
            shoppingDtoResponse.setShoppingStatus( shopping.getShoppingStatus().name() );
        }
        shoppingDtoResponse.setId( shopping.getId() );
        shoppingDtoResponse.setDataCreatorShoping( shopping.getDataCreatorShoping() );
        shoppingDtoResponse.setDataExecutedShoping( shopping.getDataExecutedShoping() );
        shoppingDtoResponse.setPrice( shopping.getPrice() );
        shoppingDtoResponse.setVolume( shopping.getVolume() );

        return shoppingDtoResponse;
    }

    protected Product shoppingDtoRequestToProduct(ShoppingDtoRequest shoppingDtoRequest) {
        if ( shoppingDtoRequest == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.id( shoppingDtoRequest.getProductId() );

        return product.build();
    }

    protected PointShopping shoppingDtoRequestToPointShopping(ShoppingDtoRequest shoppingDtoRequest) {
        if ( shoppingDtoRequest == null ) {
            return null;
        }

        PointShopping.PointShoppingBuilder pointShopping = PointShopping.builder();

        pointShopping.id( shoppingDtoRequest.getPointShoppingId() );

        return pointShopping.build();
    }

    protected FamilyGroup shoppingDtoRequestToFamilyGroup(ShoppingDtoRequest shoppingDtoRequest) {
        if ( shoppingDtoRequest == null ) {
            return null;
        }

        FamilyGroup.FamilyGroupBuilder familyGroup = FamilyGroup.builder();

        familyGroup.id( shoppingDtoRequest.getFamilyGroupId() );

        return familyGroup.build();
    }

    protected User shoppingDtoRequestToUser(ShoppingDtoRequest shoppingDtoRequest) {
        if ( shoppingDtoRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( shoppingDtoRequest.getUserExecutorId() );

        return user.build();
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
