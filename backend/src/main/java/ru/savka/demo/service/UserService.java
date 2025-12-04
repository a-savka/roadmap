package ru.savka.demo.service;

import org.springframework.stereotype.Service;
import ru.savka.demo.dto.UserDto;
import ru.savka.demo.entity.User;
import ru.savka.demo.exception.UserNotFoundException;
import ru.savka.demo.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User makeNewUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return userRepository.save(user);
    }

    public User loginUser(UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(userDto.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(userDto.getPassword())) {
                return user;
            }
        }
        throw new UserNotFoundException("Invalid username or password");
    }
}
