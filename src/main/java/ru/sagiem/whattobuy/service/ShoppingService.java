package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.auth.ProductDtoRequest;
import ru.sagiem.whattobuy.dto.auth.ShoppingDtoRequest;
import ru.sagiem.whattobuy.dto.auth.ShoppingSetDtoRequest;
import ru.sagiem.whattobuy.mapper.ShoppingMapper;
import ru.sagiem.whattobuy.model.shopping.Shopping;
import ru.sagiem.whattobuy.model.shopping.ShoppingStatus;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;
import ru.sagiem.whattobuy.repository.UserRepository;
import ru.sagiem.whattobuy.repository.poroduct.PointShoppingRepository;
import ru.sagiem.whattobuy.repository.poroduct.ProductRepository;
import ru.sagiem.whattobuy.repository.poroduct.ShoppingRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShoppingService {

    public final ShoppingRepository shoppingRepository;
    private final UserRepository userRepository;
    private final ShoppingMapper shoppingMapper;
    private final ProductRepository productRepository;
    private final PointShoppingRepository pointShoppingRepository;

    //пользователь приобрел товар и сам добавляет его.
    public Integer addShopping(ShoppingDtoRequest shoppingDtoRequest, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        FamilyGroup familyGroup = user.getUsersFamilyGroup();

        Shopping shopping = Shopping.builder()
                .dataCreatorShoping(LocalDateTime.now())
                .dataExecutedShoping(LocalDateTime.now())
                .product(productRepository.getReferenceById(shoppingDtoRequest.getProductId()))
                .volume(shoppingDtoRequest.getVolume())
                .pointShopping(pointShoppingRepository.getReferenceById(shoppingDtoRequest.getPointShoppingId()))
                .familyGroup(familyGroup)
                .userCreator(user)
                .userExecutor(user)
                .shoppingStatus(ShoppingStatus.ASSIGNED)
                .build();

        Shopping saveShopping = shoppingRepository.save(shopping);
        return saveShopping.getId();
    }

    // пользователь назначает другому пользователю из семьи купить товар.
    public Integer addSetShopping(ShoppingSetDtoRequest shoppingSetDtoRequest, UserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        FamilyGroup familyGroup = user.getUsersFamilyGroup();

        Shopping shopping = Shopping.builder()
                .dataCreatorShoping(LocalDateTime.now())
                .product(productRepository.getReferenceById(shoppingSetDtoRequest.getProductId()))
                .volume(shoppingSetDtoRequest.getVolume())
                .pointShopping(pointShoppingRepository.getReferenceById(shoppingSetDtoRequest.getPointShoppingId()))
                .familyGroup(familyGroup)
                .userCreator(user)
                .userExecutor(userRepository.getReferenceById(shoppingSetDtoRequest.getUserExecutorId()))
                .shoppingStatus(ShoppingStatus.ASSIGNED)
                .build();

        Shopping saveShopping = shoppingRepository.save(shopping);
        return saveShopping.getId();
    }
}
