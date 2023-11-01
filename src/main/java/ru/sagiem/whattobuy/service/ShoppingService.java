package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.auth.ProductDtoRequest;
import ru.sagiem.whattobuy.dto.auth.ShoppingDtoRequest;
import ru.sagiem.whattobuy.mapper.ShoppingMapper;
import ru.sagiem.whattobuy.model.shopping.Shopping;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.repository.UserRepository;
import ru.sagiem.whattobuy.repository.poroduct.ShoppingRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShoppingService {

    public final ShoppingRepository shoppingRepository;
    private final UserRepository userRepository;
    private final ShoppingMapper shoppingMapper;

    public Integer addShopping(ShoppingDtoRequest shoppingDtoRequest, UserDetails userDetails) {
        var user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        FamilyGroup familyGroup = user.getUsersFamilyGroup();
        Shopping shopping = shoppingMapper.convertToModel(shoppingDtoRequest);
        shopping.setFamilyGroup(familyGroup);
        shopping.setDataCreatorShoping(LocalDateTime.now());
        Shopping saveShopping = shoppingRepository.save(shopping);

        return saveShopping.getId();
    }
}
