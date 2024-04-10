package ru.sagiem.whattobuy.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.FamilyGroupDtoRequest;
import ru.sagiem.whattobuy.dto.FamilyGroupDtoResponse;
import ru.sagiem.whattobuy.dto.FamilyGroupInvitationDtoRequest;
import ru.sagiem.whattobuy.dto.UserDTOResponse;
import ru.sagiem.whattobuy.exception.*;
import ru.sagiem.whattobuy.mapper.FamilyGroupInvitationMapper;
import ru.sagiem.whattobuy.mapper.FamilyGroupMapper;
import ru.sagiem.whattobuy.mapper.UserMapper;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.FamilyGroupInvitations;
import ru.sagiem.whattobuy.model.user.User;
import ru.sagiem.whattobuy.repository.FamilyGroupInvitationsRepository;
import ru.sagiem.whattobuy.repository.FamilyGroupRepository;
import ru.sagiem.whattobuy.repository.UserRepository;
import ru.sagiem.whattobuy.utils.FamilyGroupAndUserUtils;

import java.util.ArrayList;
import java.util.List;

import static ru.sagiem.whattobuy.utils.ResponseUtils.USER_NOT_FOUND_EXCEPTION_MESSAGE;

@Service
@RequiredArgsConstructor
public class FamilyGroupService {
    private final FamilyGroupRepository familyGroupRepository;
    private final FamilyGroupAndUserUtils familyGroupAndUserUtils;
    private final FamilyGroupMapper familyGroupMapper;
    private final UserMapper userMapper;
    private final FamilyGroupInvitationsRepository familyGroupInvitationsRepository;
    private final UserRepository userRepository;
    private final FamilyGroupInvitationMapper familyGroupInvitationMapper;

    public List<FamilyGroupDtoResponse> showAllMyCreatedGroups(UserDetails userDetails) {
        User user = familyGroupAndUserUtils.getUser(userDetails);
        List<FamilyGroup> familyGroups = familyGroupRepository.findByUserCreator(user).orElse(null);
        if (familyGroups == null)
            return null;
        return familyGroups.stream()
                .map(familyGroupMapper::convertToDto)
                .toList();
    }

    public List<FamilyGroupDtoResponse> showAllMyGroups(UserDetails userDetails) {
        User user = familyGroupAndUserUtils.getUser(userDetails);
        List<FamilyGroup> familyGroups = user.getFamilyGroups();
        if (familyGroups == null)
            return null;
        return familyGroups.stream()
                .map(familyGroupMapper::convertToDto)
                .toList();
    }


    public List<UserDTOResponse> showAllUsersInGroup(UserDetails userDetails, Integer familyGroupId) {
        FamilyGroup familyGroup = familyGroupRepository.findById(familyGroupId).orElse(null);
        if (familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, familyGroupId) && familyGroup!= null) {
                List<User> users = familyGroup.getUsers();
                return users.stream()
                        .map(userMapper::convertToDTO)
                        .toList();
        }
        throw new FamilyGroupNotFoundException();

    }

    public Integer addNewGroup(UserDetails userDetails, FamilyGroupDtoRequest request) {
        User user = familyGroupAndUserUtils.getUser(userDetails);
        List<User> users = new ArrayList<>();
        users.add(user);
        FamilyGroup familyGroup = FamilyGroup.builder()
                .name(request.getName())
                .userCreator(user)
                .users(users)
                .build();
        FamilyGroup familyGroupEntity = familyGroupRepository.save(familyGroup);
//        List<FamilyGroup> userFamilyGroups = user.getFamilyGroups();
//        userFamilyGroups.add(familyGroupEntity);
//        user.setFamilyGroups(userFamilyGroups);
//        userRepository.save(user);

        return familyGroupEntity.getId();
    }

    public FamilyGroupDtoResponse search(Integer FamilyGroupid, UserDetails userDetails) {
        if (familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, FamilyGroupid)) {
            FamilyGroup familyGroup = familyGroupRepository.findById(FamilyGroupid).orElse(null);
            if (familyGroup == null)
                throw new FamilyGroupNotFoundException();
            return familyGroupMapper.convertToDto(familyGroup);
        }
        throw new FamilyGroupNotUserException();
    }

    @Transactional
    public void renameGroup(UserDetails userDetails, Integer id, String newName) {
        FamilyGroup familyGroup = familyGroupRepository.findById(id).orElse(null);
        if (familyGroup == null)
            throw new FamilyGroupNotFoundException();

        if (familyGroupAndUserUtils.isUserCreatedInFamilyGroup(userDetails, id)) {
            familyGroup.setName(newName);
            familyGroupRepository.save(familyGroup);
        }
        else
            throw new FamilyGroupNotCreatorException();
    }

    public void sendInvitation(UserDetails userDetails, Integer FamilyGroupId, Integer userId) {
        FamilyGroup familyGroup = familyGroupRepository.findById(FamilyGroupId).orElse(null);
        if (familyGroup == null)
            throw new FamilyGroupNotFoundException();

        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            throw new UsernameNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE);

        List<FamilyGroup> userFamilyGroups = user.getFamilyGroups();
        if (userFamilyGroups.contains(familyGroup))
            throw new FamilyGroupAlreadyUserException();

        if (familyGroup.getUserCreator().equals(familyGroupAndUserUtils.getUser(userDetails))) {
            FamilyGroupInvitations familyGroupInvitations = FamilyGroupInvitations.builder()
                    .user(user)
                    .familyGroup(familyGroup)
                    .build();
            familyGroupInvitationsRepository.save(familyGroupInvitations);
        }
        else
            throw new FamilyGroupNotCreatorException();
    }

    public String deleteUserInGroup(UserDetails userDetails, Integer groupId, Integer userId) {
        FamilyGroup familyGroup = familyGroupRepository.findById(groupId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if (familyGroup == null)
            throw new FamilyGroupNotFoundException();
        if (user == null)
            throw new UsernameNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE);

        if (familyGroupAndUserUtils.isUserCreatedInFamilyGroup(userDetails, groupId)) {
            if(familyGroup.getUserCreator() == user)
                throw new FamilyGroupNotDeleteCreateUserException();

            List<FamilyGroup> userFamilyGroups = user.getFamilyGroups();
            if (userFamilyGroups.contains(familyGroup)) {
                familyGroup.removeUser(user);
                familyGroupRepository.save(familyGroup);
                return user.getUsername();

            }
            else
             throw new FamilyGroupNotUserException();
        }
        else
            throw new FamilyGroupNotCreatorException();
    }

    public List<FamilyGroupInvitationDtoRequest> showAllMyCreatedInvitation(UserDetails userDetails) {
        User user = familyGroupAndUserUtils.getUser(userDetails);
        List<FamilyGroup> familyGroups = user.getUserCreatorFamilyGroups();
        List<FamilyGroupInvitations> invitations = familyGroupInvitationsRepository.findByFamilyGroupIn(familyGroups).orElse(null);
        if (invitations != null)
            return invitations.stream().map(familyGroupInvitationMapper::convertToDto).toList();
        else return null;

    }


    public void deleteInvitation(Integer familyGroupInvitationId, UserDetails userDetails) {
        FamilyGroupInvitations familyGroupInvitations = familyGroupInvitationsRepository.findById(familyGroupInvitationId).orElse(null);
        assert familyGroupInvitations!= null;
        User user = familyGroupAndUserUtils.getUser(userDetails);
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

        User user = familyGroupAndUserUtils.getUser(userDetails); //todo проверить работает или нет добавление пользователя в FamilyGroup;
        if (user == familyGroupInvitations.getUser()) {
            FamilyGroup familyGroup = familyGroupInvitations.getFamilyGroup();
            familyGroup.addUser(user);
            familyGroupRepository.save(familyGroup);
            familyGroupInvitationsRepository.delete(familyGroupInvitations);
        }
        else
            throw new FamilyGroupInvitationNotUserException();
    }

    public List<FamilyGroupInvitationDtoRequest> showAllInboxInvitation(UserDetails userDetails) {
        User user = familyGroupAndUserUtils.getUser(userDetails);
        List<FamilyGroupInvitations> invitations = familyGroupInvitationsRepository.findByUser(user).orElse(null);
        if (invitations!= null)
            return invitations.stream().map(familyGroupInvitationMapper::convertToDto).toList();
        else
            return null;
    }

    public String deleteGroup(Integer groupId, UserDetails userDetails) {
        if (familyGroupAndUserUtils.isUserCreatedInFamilyGroup(userDetails, groupId)) {
            FamilyGroup familyGroup = familyGroupRepository.findById(groupId).orElse(null);
            if (familyGroup != null) {
                String familyGroupName = familyGroup.getName();
                familyGroupRepository.delete(familyGroup);
                return familyGroupName;
            }
            else
                throw new FamilyGroupNotFoundException();
        }
        else throw new FamilyGroupNotCreatorException();


    }
}
