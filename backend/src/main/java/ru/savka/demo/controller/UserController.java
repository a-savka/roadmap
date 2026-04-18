package ru.savka.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.savka.demo.dto.UserDto;
import ru.savka.demo.entity.User;
import ru.savka.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User makeNewUser(@RequestBody UserDto userDto) {
        return userService.makeNewUser(userDto);
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody UserDto userDto) {
        return userService.loginUser(userDto);
    }
}
