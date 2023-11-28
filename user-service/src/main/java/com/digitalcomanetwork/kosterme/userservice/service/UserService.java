package com.digitalcomanetwork.kosterme.userservice.service;

import com.digitalcomanetwork.kosterme.userservice.shared.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDetails);
}
