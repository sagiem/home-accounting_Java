package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.model.shopping.ShoppingProject;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;
import ru.sagiem.whattobuy.repository.UserRepository;
import ru.sagiem.whattobuy.repository.poroduct.ShoppingProjectRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingProjectService {

    private final ShoppingProjectRepository shoppingProjectRepository;
    private final UserRepository userRepository;

    public List<ShoppingProject> showAll(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        assert user != null;
        List<FamilyGroup> familyGroups = user.getFamilyGroup();

        return shoppingProjectRepository.findByFamilyGroupIn(familyGroups).orElse(null);

    }
}
