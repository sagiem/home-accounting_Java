package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.PointShoppingDtoRequest;
import ru.sagiem.whattobuy.dto.PointShoppingDtoResponse;
import ru.sagiem.whattobuy.mapper.PointShoppingMapper;
import ru.sagiem.whattobuy.model.shopping.PointShopping;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;
import ru.sagiem.whattobuy.repository.FamilyGroupRepository;
import ru.sagiem.whattobuy.repository.UserRepository;
import ru.sagiem.whattobuy.repository.poroduct.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointShoppingService {


    private final UserRepository userRepository;
    private final FamilyGroupRepository familyGroupRepository;
    private final PointShoppingMapper pointShoppingMapper;
    private PointShoppingRepository pointShoppingRepository;


    public List<PointShoppingDtoResponse> showAll(UserDetails userDetails) {
        var user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        List<FamilyGroup> familyGroup = user.getFamilyGroup();

        List<PointShopping> pointShoppings = pointShoppingRepository.findByUserCreatorOrFamilyGroupIn(user, familyGroup).orElse(null);
        if (pointShoppings != null) {
            return pointShoppings.stream()
                    .map(pointShoppingMapper::convertToDTO)
                    .toList();
        }
        return null;

    }

    public void addPointShopping(PointShoppingDtoRequest pointShoppingDtoRequest,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        var user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        PointShopping pointShopping = PointShopping.builder()
                .name(pointShoppingDtoRequest.getName())
                .address(pointShoppingDtoRequest.getAddress())
                .comment(pointShoppingDtoRequest.getComment())
                .userCreator(user)
                .familyGroup(familyGroupRepository.getReferenceById(pointShoppingDtoRequest.getFamilyGroup()))
                .build();

        pointShoppingRepository.save(pointShopping);
    }


    public PointShoppingDtoResponse searchId(Integer id, UserDetails userDetails) {

        var user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        List<FamilyGroup> familyGroups = user.getFamilyGroup();

        return pointShoppingMapper.convertToDTO(pointShoppingRepository.findByIdAndUserCreator(id, user));
    }

    public ResponseEntity<?> update(Integer id, PointShoppingDtoRequest pointShoppingDtoRequest, UserDetails userDetails) {

        PointShopping pointShopping = pointShoppingRepository.getReferenceById(id);
        pointShopping.setName(pointShoppingDtoRequest.getName());
        pointShopping.setAddress(pointShoppingDtoRequest.getAddress());
        pointShopping.setComment(pointShoppingDtoRequest.getComment());
        pointShopping.setFamilyGroup(familyGroupRepository.getReferenceById(pointShoppingDtoRequest.getFamilyGroup()));

        return ResponseEntity.ok().build();
    }
}
