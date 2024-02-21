package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.FamilyGroupDtoRequest;
import ru.sagiem.whattobuy.dto.FamilyGroupDtoResponse;
import ru.sagiem.whattobuy.dto.UserDTOResponse;
import ru.sagiem.whattobuy.mapper.FamilyGroupMapper;
import ru.sagiem.whattobuy.mapper.UserMapper;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.FamilyGroupInvitations;
import ru.sagiem.whattobuy.model.user.User;
import ru.sagiem.whattobuy.repository.FamilyGroupInvitationsRepository;
import ru.sagiem.whattobuy.repository.FamilyGroupRepository;
import ru.sagiem.whattobuy.repository.UserRepository;
import ru.sagiem.whattobuy.utils.FamalyGroupAndUserUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyGroupService {
    private final FamilyGroupRepository familyGroupRepository;
    private final FamalyGroupAndUserUtils famalyGroupAndUserUtils;
    private final FamilyGroupMapper familyGroupMapper;
    private final UserMapper userMapper;
    private final FamilyGroupInvitationsRepository familyGroupInvitationsRepository;
    private final UserRepository userRepository;

    public List<FamilyGroupDtoResponse> showAllMyCreatedGroups(UserDetails userDetails) {
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

    public Integer addNewGroup(UserDetails userDetails, FamilyGroupDtoRequest request) {
        User user = famalyGroupAndUserUtils.getUser(userDetails);
        FamilyGroup familyGroup = FamilyGroup.builder()
                .name(request.getName())
                .creatorUser(user)
                .build();
        familyGroupRepository.save(familyGroup);
        return familyGroup.getId();
    }

    public FamilyGroupDtoResponse search(Integer id, UserDetails userDetails) {
        if (famalyGroupAndUserUtils.isUserInFamilyGroup(userDetails, id)) {
            FamilyGroup familyGroup = familyGroupRepository.findById(id).orElse(null);
            assert familyGroup != null;
            return familyGroupMapper.convertToDto(familyGroup);
        }
        return null;
    }

    public Integer renameGroup(Integer id, UserDetails userDetails, String newName) {
        if (famalyGroupAndUserUtils.isUserInFamilyGroup(userDetails, id)) {
            FamilyGroup familyGroup = familyGroupRepository.findById(id).orElse(null);
            assert familyGroup != null;
            familyGroup.setName(newName);
            familyGroupRepository.save(familyGroup);
            return familyGroup.getId();
        }
        return null;
    }

    public Object sendInvitation(UserDetails userDetails, Integer productId, String email) {
        FamilyGroup familyGroup = familyGroupRepository.findById(productId).orElse(null);
        assert familyGroup!= null;
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null)
            return exceptionResponse(404, "User not found");

        List<FamilyGroup> userFamilyGroups = user.getFamilyGroup();
        if (userFamilyGroups.contains(familyGroup))
            return exceptionResponse(409, "User already in group");

        if (familyGroup.getCreatorUser().equals(famalyGroupAndUserUtils.getUser(userDetails))) {
            FamilyGroupInvitations familyGroupInvitations = FamilyGroupInvitations.builder()
                    .user(user)
                    .familyGroup(familyGroup)
                    .build();
            familyGroupInvitationsRepository.save(familyGroupInvitations);
            return ResponseEntity.ok().build();

        }
        return ResponseEntity.badRequest().build(); //TODO: add error message
    }

    public Object deleteUserInGroup(UserDetails userDetails, Integer groupId, Integer userId) {
        if (famalyGroupAndUserUtils.isUserInFamilyGroup(userDetails, groupId)) {
            FamilyGroup familyGroup = familyGroupRepository.findById(groupId).orElse(null);
            assert familyGroup!= null;
            User user = userRepository.findById(userId).orElse(null);
            assert user!= null;
            List<FamilyGroup> userFamilyGroups = user.getFamilyGroup();
            if (userFamilyGroups.contains(familyGroup)) {
                userFamilyGroups.remove(familyGroup);
                userRepository.save(user);
                return ResponseEntity.ok().build();
            }
            return exceptionResponse(404, "User not in group"); //TODO: add error message
        }
    }
}
