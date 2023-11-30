package com.digitalcomanetwork.kosterme.userservice.service;

import com.digitalcomanetwork.kosterme.userservice.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDetails);
    UserDto getUserDetailsByEmail(String email);
}
