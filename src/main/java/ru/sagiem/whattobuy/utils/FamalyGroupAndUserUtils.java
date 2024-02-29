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
public class FamalyGroupAndUserUtils {
    private final UserRepository userRepository;
    private final FamilyGroupRepository familyGroupRepository;

    public Boolean isUserInFamilyGroup(UserDetails userDetails, FamilyGroup familyGroup) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        assert user != null;
        List<FamilyGroup> familyGroupList = user.getFamilyGroups();
        return familyGroupList.contains(familyGroup);
    }

        public Boolean isUserInFamilyGroup(UserDetails userDetails, Integer familyGroupId) {
        FamilyGroup familyGroup = familyGroupRepository.findById(familyGroupId).orElse(null);
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        assert user != null;
        List<FamilyGroup> familyGroupList = user.getFamilyGroups();
        return familyGroupList.contains(familyGroup);

    }

    public Boolean isUserCreatedInFamilyGroup(UserDetails userDetails, Integer familyGroupId) {
        FamilyGroup familyGroup = familyGroupRepository.findById(familyGroupId).orElse(null);
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        assert user!= null;
        List<FamilyGroup> familyGroupCreatorList = user.getUserCreatorFamilyGroups();
        return familyGroupCreatorList.contains(familyGroup);
    }

    public User getUser(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername()).orElse(null);
    }

    public List<FamilyGroup> getFamilyGroup(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        assert user != null;
        return user.getFamilyGroups();

    }
}
