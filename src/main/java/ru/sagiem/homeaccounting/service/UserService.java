package ru.sagiem.homeaccounting.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sagiem.homeaccounting.dtos.RegistrationUserDto;
import ru.sagiem.homeaccounting.model.User;
import ru.sagiem.homeaccounting.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserRepository userRepository;

 public ResponseEntity<?> createUser(RegistrationUserDto registrationUserDto){
        User user = new User();
        user.setUsername(registrationUserDto.getUsername());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(registrationUserDto.getPassword());
        return ResponseEntity.ok(userRepository.save(user));
    }

}
