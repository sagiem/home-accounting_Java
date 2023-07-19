package ru.sagiem.whattobuy.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.RegistrationUserDto;
import ru.sagiem.whattobuy.model.User;
import ru.sagiem.whattobuy.repository.UserRepository;

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
