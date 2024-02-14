package ru.sagiem.whattobuy.utils;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;
import ru.sagiem.whattobuy.repository.FamilyGroupRepository;
import ru.sagiem.whattobuy.repository.UserRepository;

import java.util.List;

@Component
@AllArgsConstructor
public class CheckFamalyGroup {
    private final UserRepository userRepository;
    private final FamilyGroupRepository familyGroupRepository;

    public Boolean check(UserDetails userDetails, FamilyGroup familyGroup) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        assert user != null;
        List<FamilyGroup> familyGroupList = user.getFamilyGroup();
        return familyGroupList.contains(familyGroup);
    }
}
