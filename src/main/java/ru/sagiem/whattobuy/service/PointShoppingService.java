package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.auth.PointShoppingDtoRequest;
import ru.sagiem.whattobuy.dto.auth.PointShoppingDtoResponse;
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

        if (user.getUsersFamilyGroup() != null) {
            FamilyGroup familyGroup = user.getUsersFamilyGroup();

            List<PointShopping> pointShoppings = pointShoppingRepository.findAllByFamilyGroup(familyGroup).orElse(null);
            if(pointShoppings != null) {
                return pointShoppings.stream()
                        .map(pointShoppingMapper::convertToDTO)
                        .toList();
            }
            return null;
        }

        List<PointShopping> pointShoppings = pointShoppingRepository.findAllByUserCreator(user).orElse(null);
        if(pointShoppings != null) {
            return pointShoppings.stream()
                    .map(pointShoppingMapper::convertToDTO)
                    .toList();
        }
        return null;
    }

    public void addPointShopping(PointShoppingDtoRequest pointShoppingDtoRequest,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        var user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        FamilyGroup familyGroup = user.getUsersFamilyGroup();
        PointShopping pointShopping = pointShoppingMapper.connertToModel(pointShoppingDtoRequest);
        pointShopping.setUserCreator(user);
        pointShopping.setFamilyGroup(familyGroup);
        pointShoppingRepository.save(pointShopping);
    }


    public PointShoppingDtoResponse searchId(Integer id, UserDetails userDetails) {

        var user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();

        if (user.getUsersFamilyGroup() != null) {
            FamilyGroup familyGroup = user.getUsersFamilyGroup();

            return pointShoppingMapper.convertToDTO(pointShoppingRepository.findByIdAndFamilyGroup(id, familyGroup));
        }
        return pointShoppingMapper.convertToDTO(pointShoppingRepository.findByIdAndUserCreator(id, user));
    }

    public PointShoppingDtoResponse update(Integer id, PointShoppingDtoRequest pointShoppingDtoRequest, UserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        FamilyGroup familyGroup = user.getUsersFamilyGroup();
        PointShopping pointShopping = pointShoppingRepository.getReferenceById(id);

        if (pointShopping.getUserCreator() == user || pointShopping.getFamilyGroup() == familyGroup) {
            pointShoppingRepository.save(pointShopping);
            return pointShoppingMapper.convertToDTO(pointShopping);
        }
        return null;
    }
}
