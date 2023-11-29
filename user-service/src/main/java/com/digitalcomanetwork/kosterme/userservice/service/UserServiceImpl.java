package com.digitalcomanetwork.kosterme.userservice.service;

import com.digitalcomanetwork.kosterme.userservice.data.UserEntity;
import com.digitalcomanetwork.kosterme.userservice.data.UserRepository;
import com.digitalcomanetwork.kosterme.userservice.mapper.UserMapper;
import com.digitalcomanetwork.kosterme.userservice.shared.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        UserEntity userEntity = userMapper.userDtoToUserEntity(userDetails);
        userRepository.save(userEntity);
        return userMapper.userEntityToUserDto(userEntity);
    }
}
