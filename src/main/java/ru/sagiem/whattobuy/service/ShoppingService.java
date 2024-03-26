package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.ShoppingDtoRequest;
import ru.sagiem.whattobuy.dto.ShoppingDtoResponse;
import ru.sagiem.whattobuy.dto.ShoppingSetDtoRequest;
import ru.sagiem.whattobuy.exception.FamilyGroupNotUserException;
import ru.sagiem.whattobuy.exception.ShoppingProjectNotFoundException;
import ru.sagiem.whattobuy.mapper.ShoppingMapper;
import ru.sagiem.whattobuy.model.shopping.Shopping;
import ru.sagiem.whattobuy.model.shopping.ShoppingProject;
import ru.sagiem.whattobuy.model.shopping.ShoppingStatus;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;
import ru.sagiem.whattobuy.repository.FamilyGroupRepository;
import ru.sagiem.whattobuy.repository.UserRepository;
import ru.sagiem.whattobuy.repository.poroduct.PointShoppingRepository;
import ru.sagiem.whattobuy.repository.poroduct.ProductRepository;
import ru.sagiem.whattobuy.repository.poroduct.ShoppingProjectRepository;
import ru.sagiem.whattobuy.repository.poroduct.ShoppingRepository;
import ru.sagiem.whattobuy.utils.FamalyGroupAndUserUtils;

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
    private final FamalyGroupAndUserUtils famalyGroupAndUserUtils;
    private final ShoppingProjectRepository shoppingProjectRepository;


    // получить все покупки пользователя из всех групп и всех проектов
        public List<ShoppingDtoResponse> showAllMyExecutor(UserDetails userDetails) {
            User user = famalyGroupAndUserUtils.getUser(userDetails);
            List<Shopping> shoppings = shoppingRepository.findByUserCreator(user).orElse(null);
            if (shoppings != null) {
                return shoppings.stream()
                        .map(shoppingMapper::convertToDto)
                        .toList();
            }
            else
                return null;
    }

    // получить все покупки из проекта покупок для пользователя
    public List<ShoppingDtoResponse> showMyExecutorinShoppingProgect(Integer shoppingProjectId, UserDetails userDetails) {
            User user = famalyGroupAndUserUtils.getUser(userDetails);
        ShoppingProject shoppingProject = shoppingProjectRepository.findById(shoppingProjectId).orElse(null);
        if (shoppingProject == null)
            throw new ShoppingProjectNotFoundException();

        List<Shopping> shoppings = shoppingRepository.findAllByShoppingProjectAndUserExecutor(shoppingProject, user).orElse(null);
        if (shoppings != null) {
            return shoppings.stream()
                    .map(shoppingMapper::convertToDto)
                    .toList();
        }
        else
            return null;
    }

    public List<ShoppingDtoResponse> showAllInShoppingProgect(Integer shoppingProgectId, UserDetails userDetails) {
            User user = famalyGroupAndUserUtils.getUser(userDetails);
            ShoppingProject shoppingProject = shoppingProjectRepository.findById(shoppingProgectId).orElse(null);
            if (shoppingProject == null)
                throw new ShoppingProjectNotFoundException();

            if(!famalyGroupAndUserUtils.isUserInFamilyGroup(userDetails, shoppingProject.getFamilyGroup().getId())
                throw new FamilyGroupNotUserException();

            List<Shopping> shoppings = shoppingRepository.findAllByShoppingProjectAndUserExecutor(shoppingProject, user).orElse(null);
            if (shoppings!= null) {
                return shoppings.stream()
                        .map(shoppingMapper::convertToDto)
                        .toList();
            }
            else
                return null;
    }

    // пользователь назначает покупку другому пользователю в рамках проекта.
    public Integer addSetUserShopping(ShoppingDtoResponse ShoppingDtoResponse, UserDetails userDetails) {

        User userExecutor = userRepository.findById(ShoppingDtoResponse.getUserExecutorId()).orElse(null);
        if (userExecutor == null)
            throw new UsernameNotFoundException("Пользователь исполнитель не найден");

        ShoppingProject shoppingProject = shoppingProjectRepository.findById(ShoppingDtoResponse.getShoppingProjectId()).orElse(null);
        if (shoppingProject == null)
            throw new ShoppingProjectNotFoundException();


        if(famalyGroupAndUserUtils.isUserInFamilyGroup(userDetails, shoppingProject.getFamilyGroup().getId())||
                userExecutor.getFamilyGroups().equals((shoppingProject.getFamilyGroup()))){

            Shopping shopping = Shopping.builder()
                    .product(productRepository.getReferenceById(ShoppingDtoResponse.getProductId()))
                    .volume(ShoppingDtoResponse.getVolume())
                    .shoppingProject(shoppingProject)
                    .pointShopping(pointShoppingRepository.getReferenceById(ShoppingDtoResponse.getPointShoppingId()))
                    .userExecutor(userRepository.getReferenceById(ShoppingDtoResponse.getUserExecutorId()))
                    .shoppingStatus(ShoppingStatus.ASSIGNED)
                    .build();

            Shopping saveShopping = shoppingRepository.save(shopping);
            return saveShopping.getId();
        }
        else
            throw new FamilyGroupNotUserException();
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
                //.familyGroup(familyGroup)
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
