package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.ShoppingProjectDtoRequest;
import ru.sagiem.whattobuy.dto.ShoppingProjectDtoResponse;
import ru.sagiem.whattobuy.dto.ShoppingProjectDtoWorkFinish;
import ru.sagiem.whattobuy.exception.FamilyGroupNotFoundException;
import ru.sagiem.whattobuy.exception.FamilyGroupNotUserException;
import ru.sagiem.whattobuy.mapper.ShoppingProjectMapper;
import ru.sagiem.whattobuy.model.shopping.Shopping;
import ru.sagiem.whattobuy.model.shopping.ShoppingProject;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;
import ru.sagiem.whattobuy.repository.FamilyGroupRepository;
import ru.sagiem.whattobuy.repository.UserRepository;
import ru.sagiem.whattobuy.repository.poroduct.ShoppingProjectRepository;
import ru.sagiem.whattobuy.repository.poroduct.ShoppingRepository;
import ru.sagiem.whattobuy.utils.FamilyGroupAndUserUtils;

import java.util.List;

import static ru.sagiem.whattobuy.model.shopping.ShoppingStatus.*;

@Service
@RequiredArgsConstructor
public class ShoppingProjectService {

    private final ShoppingProjectRepository shoppingProjectRepository;
    private final UserRepository userRepository;
    private final ShoppingProjectMapper shoppingProjectMapper;
    private final FamilyGroupRepository familyGroupRepository;
    private final ShoppingRepository shoppingRepository;
    private final FamilyGroupAndUserUtils familyGroupAndUserUtils;

    public List<ShoppingProjectDtoResponse> showAllUserCreatorProjects(UserDetails userDetails) {
        List<FamilyGroup> familyGroups = familyGroupAndUserUtils.getFamilyGroup(userDetails);
        User user = familyGroupAndUserUtils.getUser(userDetails);
        List<ShoppingProject> shoppingProjects = shoppingProjectRepository.findByUserCreatorOrFamilyGroupIn(user, familyGroups).orElse(null);
        if (shoppingProjects != null) {
            return shoppingProjects.stream()
                    .map(shoppingProjectMapper::convertToDto)
                    .toList();
        }
        return null;
    }

        public List<ShoppingProjectDtoResponse> showAllGroupProjects(Integer familyGroupId, UserDetails userDetails) {
        FamilyGroup familyGroup = familyGroupRepository.findById(familyGroupId).orElse(null);
        if (familyGroup!= null)
            throw new FamilyGroupNotFoundException();
        if (familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, familyGroup)) {
            List<ShoppingProject> shoppingProjects = shoppingProjectRepository.findByFamilyGroup(familyGroup).orElse(null);
            if (shoppingProjects != null) {
                return shoppingProjects.stream()
                        .map(shoppingProjectMapper::convertToDto)
                        .toList();
            }
            else
                return null;
        }
        else
            throw new FamilyGroupNotUserException();
    }








    public Integer add(ShoppingProjectDtoRequest request, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        assert user != null;

        ShoppingProject shoppingProject = ShoppingProject.builder()
                .name(request.getName())
                .finishDate(request.getFinishDate())
                .active(true)
                .comment(request.getComment())
                .userCreator(user)
                .familyGroup(familyGroupRepository.getReferenceById(request.getFamilyGroupId()))
                .build();

        ShoppingProject shoppingProjectSave = shoppingProjectRepository.save(shoppingProject);
        return shoppingProjectSave.getId();
    }

    public ShoppingProjectDtoResponse searchId(Integer id, UserDetails userDetails) {
        return shoppingProjectMapper.convertToDto(shoppingProjectRepository.getReferenceById(id));
    }

    public Integer update(Integer id, ShoppingProjectDtoRequest request, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        ShoppingProject shoppingProject = shoppingProjectRepository.getReferenceById(id);

        if (user == shoppingProject.getUserCreator()) {
            shoppingProject.setName(request.getName());
            shoppingProject.setFinishDate(request.getFinishDate());
            shoppingProject.setComment(request.getComment());
            shoppingProject.setFamilyGroup(familyGroupRepository.getReferenceById(request.getFamilyGroupId()));

            shoppingProjectRepository.save(shoppingProject);
        }

        return null;
    }

    public Boolean delete(Integer id, UserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        ShoppingProject shoppingProject = shoppingProjectRepository.getReferenceById(id);

        if (user == shoppingProject.getUserCreator() && shoppingProject.getShoppings() == null) {
            shoppingProjectRepository.delete(shoppingProject);
        }

        return false;
    }

    public ShoppingProjectDtoWorkFinish workFinishShoppingInProgect(Integer id) {
        Integer inWork = 0;
        Integer finish = 0;
        Integer notWork = 0;

        List<Shopping> shoppings = shoppingRepository.findByShoppingProject(shoppingProjectRepository.getReferenceById(id)).orElse(null);
        assert shoppings != null;
        for (Shopping shopping : shoppings) {
            if (shopping.getShoppingStatus() == ASSIGNED)
                inWork++;
            if (shopping.getShoppingStatus() == EXECUTED)
                finish++;
            if (shopping.getShoppingStatus() == NOT_EXECUTED)
                notWork++;
        }

        return ShoppingProjectDtoWorkFinish.builder()
                .inWork(inWork)
                .finish(finish)
                .notWork(notWork)
                .build();
    }


    public List<Shopping> workShoppingInProgect(Integer id) {
        return shoppingRepository.findByShoppingProjectAndShoppingStatus(shoppingProjectRepository.getReferenceById(id),
                ASSIGNED).orElse(null);
    }

    public List<Shopping> FinishShoppingInProgect(Integer id) {
        return shoppingRepository.findByShoppingProjectAndShoppingStatus(shoppingProjectRepository.getReferenceById(id),
                EXECUTED).orElse(null);
    }

    public List<Shopping> notWorkShoppingInProgect(Integer id) {
        return shoppingRepository.findByShoppingProjectAndShoppingStatus(shoppingProjectRepository.getReferenceById(id),
                NOT_EXECUTED).orElse(null);

    }


}
