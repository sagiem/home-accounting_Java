package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.FamilyGroupDtoResponse;
import ru.sagiem.whattobuy.dto.UserDTOResponse;
import ru.sagiem.whattobuy.mapper.FamilyGroupMapper;
import ru.sagiem.whattobuy.mapper.UserMapper;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;
import ru.sagiem.whattobuy.repository.FamilyGroupRepository;
import ru.sagiem.whattobuy.utils.FamalyGroupAndUserUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyGroupService {
    private final FamilyGroupRepository familyGroupRepository;
    private final FamalyGroupAndUserUtils famalyGroupAndUserUtils;
    private final FamilyGroupMapper familyGroupMapper;
    private final UserMapper userMapper;

    public List<FamilyGroupDtoResponse> showAllMyCreated(UserDetails userDetails) {
        User user = famalyGroupAndUserUtils.getUser(userDetails);
        List<FamilyGroup> familyGroups = familyGroupRepository.findByCreatorUser(user).orElse(null);
        if (familyGroups == null)
            return null;
        return familyGroups.stream()
                .map(familyGroupMapper::convertToDto)
                .toList();
    }

    public List<FamilyGroupDtoResponse> showAllMyGroups(UserDetails userDetails) {
        User user = famalyGroupAndUserUtils.getUser(userDetails);
        List<FamilyGroup> familyGroups = user.getFamilyGroup();
        if (familyGroups == null)
            return null;
        return familyGroups.stream()
                .map(familyGroupMapper::convertToDto)
                .toList();
    }


    public List<UserDTOResponse> showAllUsersInGroup(UserDetails userDetails, Integer familyGroupId) {
        if (famalyGroupAndUserUtils.isUserInFamilyGroup(userDetails, familyGroupId)) {
            FamilyGroup familyGroup = familyGroupRepository.findById(familyGroupId).orElse(null);
            assert familyGroup != null;
            List<User> users = familyGroup.getUsers();
            return users.stream()
                    .map(userMapper::convertToDTO)
                    .toList();
        }
        return null;

    }
}
