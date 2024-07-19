package ru.sagiem.whattobuy.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;
import ru.sagiem.whattobuy.repository.FamilyGroupRepository;
import ru.sagiem.whattobuy.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FamilyGroupAndUserUtilsTest {

    @Autowired
    private FamilyGroupAndUserUtils familyGroupAndUserUtils;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private FamilyGroupRepository familyGroupRepository;

    @Test
    public void testIsUserInFamilyGroup() {
        // Arrange
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("user", "password", new ArrayList<>());
        FamilyGroup familyGroup = new FamilyGroup();
        when(userRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.of(new User()));
        when(familyGroupRepository.findById(1)).thenReturn(Optional.of(familyGroup));

        // Act
        Boolean result = familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, 1);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    public void testIsUserInFamilyGroup_UserNotFound() {
        // Arrange
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("user", "password", new ArrayList<>());
        when(userRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.empty());

        // Act
        Boolean result = familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, 1);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    public void testIsUserInFamilyGroup_FamilyGroupNotFound() {
        // Arrange
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("user", "password", new ArrayList<>());
        when(userRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.of(new User()));
        when(familyGroupRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Boolean result = familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, 1);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    public void testIsUserCreatedInFamilyGroup() {
        // Arrange
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("user", "password", new ArrayList<>());
        FamilyGroup familyGroup = new FamilyGroup();
        when(userRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.of(new User()));
        when(familyGroupRepository.findById(1)).thenReturn(Optional.of(familyGroup));

        // Act
        Boolean result = familyGroupAndUserUtils.isUserCreatedInFamilyGroup(userDetails, 1);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    public void testIsUserCreatedInFamilyGroup_UserNotFound() {
        // Arrange
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("user", "password", new ArrayList<>());
        when(userRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.empty());

        // Act
        Boolean result = familyGroupAndUserUtils.isUserCreatedInFamilyGroup(userDetails, 1);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    public void testIsUserCreatedInFamilyGroup_FamilyGroupNotFound() {
        // Arrange
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("user", "password", new ArrayList<>());
        when(userRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.of(new User()));
        when(familyGroupRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Boolean result = familyGroupAndUserUtils.isUserCreatedInFamilyGroup(userDetails, 1);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    public void testGetUser() {
        // Arrange
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("user", "password", new ArrayList<>());
        when(userRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.of(new User()));

        // Act
        User user = familyGroupAndUserUtils.getUser(userDetails);

        // Assert
        assertThat(user).isNotNull();
    }

    @Test
    public void testGetUser_NotFound() {
        // Arrange
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("user", "password", new ArrayList<>());
        when(userRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.empty());

        // Act
        User user = familyGroupAndUserUtils.getUser(userDetails);

        // Assert
        assertThat(user).isNull();
    }

    @Test
    public void testGetFamilyGroup() {
        // Arrange
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("user", "password", new ArrayList<>());
        when(userRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.of(new User()));

        // Act
        List<FamilyGroup> familyGroups = familyGroupAndUserUtils.getFamilyGroup(userDetails);

        // Assert
        assertThat(familyGroups).isNotNull();
    }

    @Test
    public void testGetFamilyGroup_NotFound() {
        // Arrange
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("user", "password", new ArrayList<>());
        when(userRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.empty());

        // Act
        List<FamilyGroup> familyGroups = familyGroupAndUserUtils.getFamilyGroup(userDetails);

        // Assert
        assertThat(familyGroups).isNull();
    }
}
