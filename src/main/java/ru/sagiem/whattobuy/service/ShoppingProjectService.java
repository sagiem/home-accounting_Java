package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.ShoppingProjectDtoRequest;
import ru.sagiem.whattobuy.dto.ShoppingProjectDtoResponse;
import ru.sagiem.whattobuy.mapper.ShoppingProjectMapper;
import ru.sagiem.whattobuy.model.shopping.ShoppingProject;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;
import ru.sagiem.whattobuy.repository.FamilyGroupRepository;
import ru.sagiem.whattobuy.repository.UserRepository;
import ru.sagiem.whattobuy.repository.poroduct.ShoppingProjectRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingProjectService {

    private final ShoppingProjectRepository shoppingProjectRepository;
    private final UserRepository userRepository;
    private final ShoppingProjectMapper shoppingProjectMapper;
    private final FamilyGroupRepository familyGroupRepository;

    public List<ShoppingProjectDtoResponse> showAllByFamilyGroupAll(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        assert user != null;
        List<FamilyGroup> familyGroups = user.getFamilyGroup();
        List<ShoppingProject> shoppingProjects = shoppingProjectRepository.findByFamilyGroupIn(familyGroups).orElse(null);

        if (shoppingProjects != null) {
            return shoppingProjects.stream()
                    .map(shoppingProjectMapper::convertToDto)
                    .toList();
        }

        return null;

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
}
