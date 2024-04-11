package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.PointShoppingDtoRequest;
import ru.sagiem.whattobuy.dto.PointShoppingDtoResponse;
import ru.sagiem.whattobuy.exception.FamilyGroupNotFoundException;
import ru.sagiem.whattobuy.exception.FamilyGroupNotUserException;
import ru.sagiem.whattobuy.mapper.PointShoppingMapper;
import ru.sagiem.whattobuy.model.shopping.PointShopping;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;
import ru.sagiem.whattobuy.repository.FamilyGroupRepository;
import ru.sagiem.whattobuy.repository.UserRepository;
import ru.sagiem.whattobuy.repository.poroduct.*;
import ru.sagiem.whattobuy.utils.FamilyGroupAndUserUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointShoppingService {
    private final PointShoppingMapper pointShoppingMapper;
    private final FamilyGroupAndUserUtils familyGroupAndUserUtils;
    private final PointShoppingRepository pointShoppingRepository;
    private final FamilyGroupRepository familyGroupRepository;
    private final UserRepository userRepository;


    public List<PointShoppingDtoResponse> showAllForGroup(Integer id, UserDetails userDetails) {
        FamilyGroup familyGroup = familyGroupRepository.findById(id).orElse(null);
        if (familyGroup == null)
            throw new FamilyGroupNotFoundException();

        if (!familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, id))
            throw new FamilyGroupNotUserException();

        List<PointShopping> pointShoppings = pointShoppingRepository.findAllByFamilyGroup(familyGroup).orElse(null);
        if (pointShoppings == null)
            return null;
        else {
            return pointShoppings.stream()
                    .map(pointShoppingMapper::convertToDTO)
                    .toList();
        }
    }

    public List<PointShoppingDtoResponse> showAllMyCreated(UserDetails userDetails) {
        User user = familyGroupAndUserUtils.getUser(userDetails);
        List<PointShopping> pointShoppings = pointShoppingRepository.findAllByUserCreator(user).orElse(null);
        if (pointShoppings == null)
            return null;
        else {
            return pointShoppings.stream()
                    .map(pointShoppingMapper::convertToDTO)
                    .toList();
        }
    }


    public Integer add(PointShoppingDtoRequest request, UserDetails userDetails) {
        FamilyGroup familyGroup = familyGroupRepository.findById(request.getFamilyGroupId()).orElse(null);
        if (familyGroup == null)
            throw new FamilyGroupNotFoundException();

        if (familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, familyGroup.getId())) {
            PointShopping pointShopping = PointShopping.builder()
                    .name(request.getName())
                    .address(request.getAddress())
                    .comment(request.getComment())
                    .familyGroup(familyGroup)
                    .build();
            PointShopping pointShoppingEntity = pointShoppingRepository.save(pointShopping);
            return pointShoppingEntity.getId();
        } else
            throw new FamilyGroupNotUserException();
    }


    public PointShoppingDtoResponse searchId(Integer id, UserDetails userDetails) {


        if (familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, id))
            return pointShoppingMapper.convertToDTO(pointShoppingRepository.findById(id).orElse(null));
        else
            throw new FamilyGroupNotUserException();
    }

    public String update(Integer id, PointShoppingDtoRequest request, UserDetails userDetails) {
        User user = familyGroupAndUserUtils.getUser(userDetails);
        PointShopping pointShopping = pointShoppingRepository.findById(id).orElse(null);
        assert pointShopping != null;//TODO сделать исключение
        if (familyGroupAndUserUtils.isUserCreatedInFamilyGroup(userDetails, pointShopping.getFamilyGroup().getId()) ||
                pointShopping.getUserCreator() == user) {
            pointShopping.setName(request.getName());
            pointShopping.setAddress(request.getAddress());
            pointShopping.setComment(request.getComment());
            pointShopping.setFamilyGroup(familyGroupRepository.getReferenceById(request.getFamilyGroupId()));
            pointShoppingRepository.save(pointShopping);
            return pointShopping.getName();
        } else
            throw new FamilyGroupNotUserException();

    }

    public String delete(Integer id, UserDetails userDetails) {
        PointShopping pointShopping = pointShoppingRepository.findById(id).orElse(null);
        assert pointShopping!= null;//TODO сделать исключение
        User user = familyGroupAndUserUtils.getUser(userDetails);
        if (familyGroupAndUserUtils.isUserCreatedInFamilyGroup(userDetails, pointShopping.getFamilyGroup().getId()) ||
                pointShopping.getUserCreator() == user) {
            pointShoppingRepository.delete(pointShopping);
            return pointShopping.getName();
        } else
            throw new FamilyGroupNotUserException();
    }
}
