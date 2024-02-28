package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.FamilyGroupDtoRequest;
import ru.sagiem.whattobuy.dto.FamilyGroupDtoResponse;
import ru.sagiem.whattobuy.dto.UserDTOResponse;
import ru.sagiem.whattobuy.exception.*;
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

import static ru.sagiem.whattobuy.utils.ResponseUtils.USER_NOT_FOUND_EXCEPTION_MESSAGE;

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
        List<FamilyGroup> familyGroups = familyGroupRepository.findByUserCreator(user).orElse(null);
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
            if (familyGroup == null)
                throw new FamilyGroupNotFoundException();
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
                .userCreator(user)
                .build();
        FamilyGroup familyGroupEntity = familyGroupRepository.save(familyGroup);
        List<FamilyGroup> userFamilyGroups = user.getFamilyGroup();
        userFamilyGroups.add(familyGroupEntity);
        user.setFamilyGroup(userFamilyGroups);
        userRepository.save(user);

        return familyGroupEntity.getId();
    }

    public FamilyGroupDtoResponse search(Integer FamilyGroupid, UserDetails userDetails) {
        if (famalyGroupAndUserUtils.isUserInFamilyGroup(userDetails, FamilyGroupid)) {
            FamilyGroup familyGroup = familyGroupRepository.findById(FamilyGroupid).orElse(null);
            if (familyGroup == null)
                throw new FamilyGroupNotFoundException();
            return familyGroupMapper.convertToDto(familyGroup);
        }
        throw new FamilyGroupNotUserException();
    }

    public void renameGroup(Integer id, UserDetails userDetails, String newName) {
        if (famalyGroupAndUserUtils.isUsercCreatedInFamilyGroup(userDetails, id)) {
            FamilyGroup familyGroup = familyGroupRepository.findById(id).orElse(null);
            if (familyGroup == null)
                throw new FamilyGroupNotFoundException();
            familyGroup.setName(newName);
            familyGroupRepository.save(familyGroup);
        }
        throw new FamilyGroupNotCreatorException();
    }

    public void sendInvitation(UserDetails userDetails, Integer FamilyGroupId, Integer userId) {
        FamilyGroup familyGroup = familyGroupRepository.findById(FamilyGroupId).orElse(null);
        if (familyGroup == null)
            throw new FamilyGroupNotFoundException();

        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            throw new UsernameNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE);

        List<FamilyGroup> userFamilyGroups = user.getFamilyGroup();
        if (userFamilyGroups.contains(familyGroup))
            throw new FamilyGroupAlreadyUserException();

        if (familyGroup.getUserCreator().equals(famalyGroupAndUserUtils.getUser(userDetails))) {
            FamilyGroupInvitations familyGroupInvitations = FamilyGroupInvitations.builder()
                    .user(user)
                    .familyGroup(familyGroup)
                    .build();
            familyGroupInvitationsRepository.save(familyGroupInvitations);
        }
        throw new FamilyGroupNotCreatorException();
    }

    public void deleteUserInGroup(UserDetails userDetails, Integer groupId, Integer userId) {
        if (famalyGroupAndUserUtils.isUserInFamilyGroup(userDetails, groupId)) {
            FamilyGroup familyGroup = familyGroupRepository.findById(groupId).orElse(null);
            assert familyGroup != null;
            User user = userRepository.findById(userId).orElse(null);
            assert user != null;
            List<FamilyGroup> userFamilyGroups = user.getFamilyGroup();
            if (userFamilyGroups.contains(familyGroup)) {
                userFamilyGroups.remove(familyGroup);
                userRepository.save(user);

            }
            throw new FamilyGroupNotUserException();
        }
        throw new FamilyGroupNotCreatorException();
    }

    public List<Integer> showAllMyCreatedInvitation(UserDetails userDetails) {
        User user = famalyGroupAndUserUtils.getUser(userDetails);
        List<FamilyGroup> familyGroups = user.getUserCreatorFamilyGroups();
        List<FamilyGroupInvitations> invitations = familyGroupInvitationsRepository.findByFamilyGroupIn(familyGroups).orElse(null);
        if (invitations != null)
            return invitations.stream().map(FamilyGroupInvitations::getId).toList();
        else return null;
    }


    public void deleteInvitation(Integer familyGroupInvitationId, UserDetails userDetails) {
        FamilyGroupInvitations familyGroupInvitations = familyGroupInvitationsRepository.findById(familyGroupInvitationId).orElse(null);
        assert familyGroupInvitations!= null;
        User user = famalyGroupAndUserUtils.getUser(userDetails);
        List<FamilyGroup> userCreatorFamilyGroups = user.getUserCreatorFamilyGroups();
        if (userCreatorFamilyGroups.contains(familyGroupInvitations.getFamilyGroup()) || user == familyGroupInvitations.getUser()) {
            familyGroupInvitationsRepository.delete(familyGroupInvitations);
        }
        else throw new FamilyGroupInvitationNotCreatorAndUserException();
    }

    public void acceptInvitation(UserDetails userDetails, Integer familyGroupInvitationId) {
        FamilyGroupInvitations familyGroupInvitations = familyGroupInvitationsRepository.findById(familyGroupInvitationId).orElse(null);
        if (familyGroupInvitations== null)
            throw new FamilyGroupInvitationNotFoundException();

        User user = famalyGroupAndUserUtils.getUser(userDetails); //todo проверить работает или нет добавление пользователя в FamilyGroup;
        if (user == familyGroupInvitations.getUser()) {
            List<FamilyGroup> userFamilyGroups = user.getFamilyGroup();
            userFamilyGroups.add(familyGroupInvitations.getFamilyGroup());
            user.setFamilyGroup(userFamilyGroups);
            userRepository.save(user);
            familyGroupInvitationsRepository.delete(familyGroupInvitations);
        }
    }
}
