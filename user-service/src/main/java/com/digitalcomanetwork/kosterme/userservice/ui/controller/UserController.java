package com.digitalcomanetwork.kosterme.userservice.ui.controller;

import com.digitalcomanetwork.kosterme.userservice.mapper.UserMapper;
import com.digitalcomanetwork.kosterme.userservice.service.UserService;
import com.digitalcomanetwork.kosterme.userservice.shared.UserDto;
import com.digitalcomanetwork.kosterme.userservice.ui.model.CreateUserRequestModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private Environment env;
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/status/check")
    public String getStatus() {
        return "Working on port " + env.getProperty("local.server.port");
    }

    @PostMapping
    public String createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
        UserDto userDto = userMapper.toUserDto(userDetails);
        userService.createUser(userDto);
        return "Create user method is called";
    }
}
