package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.ShoppingDtoRequest;
import ru.sagiem.whattobuy.dto.ShoppingDtoResponse;
import ru.sagiem.whattobuy.dto.ShoppingSetDtoRequest;
import ru.sagiem.whattobuy.mapper.ShoppingMapper;
import ru.sagiem.whattobuy.model.shopping.Shopping;
import ru.sagiem.whattobuy.model.shopping.ShoppingStatus;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;
import ru.sagiem.whattobuy.repository.FamilyGroupRepository;
import ru.sagiem.whattobuy.repository.UserRepository;
import ru.sagiem.whattobuy.repository.poroduct.PointShoppingRepository;
import ru.sagiem.whattobuy.repository.poroduct.ProductRepository;
import ru.sagiem.whattobuy.repository.poroduct.ShoppingRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingService {

    public final ShoppingRepository shoppingRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PointShoppingRepository pointShoppingRepository;
    private final FamilyGroupRepository familyGroupRepository;
    private final ShoppingMapper shoppingMapper;


        public List<ShoppingDtoResponse> showAllMyCreatores(UserDetails userDetails) {
            User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
            List<Shopping> shoppingList = shoppingRepository.findByUserCreator(user).orElse(null);

            if (shoppingList != null)
                return shoppingList.stream().map(shoppingMapper::convertToDto).toList();

            return null;

    }

    //пользователь назначает себе товары для приобритения
    public Integer addMyShopping(ShoppingDtoRequest shoppingDtoRequest, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        Shopping shopping = Shopping.builder()
                .executorDate(LocalDateTime.now())
                .product(productRepository.getReferenceById(shoppingDtoRequest.getProductId()))
                .volume(shoppingDtoRequest.getVolume())
                .pointShopping(pointShoppingRepository.getReferenceById(shoppingDtoRequest.getPointShoppingId()))
                .userCreator(user)
                .userExecutor(user)
                .shoppingStatus(ShoppingStatus.ASSIGNED)
                .build();

        Shopping saveShopping = shoppingRepository.save(shopping);
        return saveShopping.getId();
    }

    // пользователь назначает покупку другому пользователю из семьи в которой он состоит.
    public Integer addSetUserShopping(ShoppingSetDtoRequest shoppingSetDtoRequest, UserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();

        Shopping shopping = Shopping.builder()
                .product(productRepository.getReferenceById(shoppingSetDtoRequest.getProductId()))
                .volume(shoppingSetDtoRequest.getVolume())
                .pointShopping(pointShoppingRepository.getReferenceById(shoppingSetDtoRequest.getPointShoppingId()))
                .userCreator(user)
                .userExecutor(userRepository.getReferenceById(shoppingSetDtoRequest.getUserExecutorId()))
                .shoppingStatus(ShoppingStatus.ASSIGNED)
                .build();

        Shopping saveShopping = shoppingRepository.save(shopping);
        return saveShopping.getId();
    }
    // пользователь назначает покупку в семью без привязки к конкретному пользователю
       public Integer addSetFamilyGroupShopping(ShoppingSetDtoRequest shoppingSetDtoRequest, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        FamilyGroup familyGroup = familyGroupRepository.getReferenceById(shoppingSetDtoRequest.getFamilyGroupId());

        Shopping shopping = Shopping.builder()
                .product(productRepository.getReferenceById(shoppingSetDtoRequest.getProductId()))
                .volume(shoppingSetDtoRequest.getVolume())
                .pointShopping(pointShoppingRepository.getReferenceById(shoppingSetDtoRequest.getPointShoppingId()))
                .userCreator(user)
                .familyGroup(familyGroup)
                .shoppingStatus(ShoppingStatus.ASSIGNED)
                .build();

        Shopping saveShopping = shoppingRepository.save(shopping);
        return saveShopping.getId();
    }

    public Integer executedShopping(Integer id, UserDetails userDetails) {

        Shopping shopping = shoppingRepository.getReferenceById(id);
        shopping.setExecutorDate(LocalDateTime.now());
        shopping.setShoppingStatus(ShoppingStatus.EXECUTED);
        shoppingRepository.save(shopping);
        return shopping.getId();
    }

    public Integer notExecutedShopping(Integer id, UserDetails userDetails) {

        Shopping shopping = shoppingRepository.getReferenceById(id);
        shopping.setExecutorDate(LocalDateTime.now());
        shopping.setShoppingStatus(ShoppingStatus.NOT_EXECUTED);
        shoppingRepository.save(shopping);
        return shopping.getId();
    }



}
