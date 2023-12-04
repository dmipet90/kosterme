package com.digitalcomanetwork.kosterme.userservice.service;

import com.digitalcomanetwork.kosterme.userservice.data.AuthorityEntity;
import com.digitalcomanetwork.kosterme.userservice.data.RoleEntity;
import com.digitalcomanetwork.kosterme.userservice.data.UserEntity;
import com.digitalcomanetwork.kosterme.userservice.data.UserRepository;
import com.digitalcomanetwork.kosterme.userservice.mapper.UserMapper;
import com.digitalcomanetwork.kosterme.userservice.shared.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity == null) throw new UsernameNotFoundException(username);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Collection<RoleEntity> roles = userEntity.getRoles();
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            Collection<AuthorityEntity> authorityEntities = role.getAuthorities();
            authorityEntities.forEach(authority -> authorities
                    .add(new SimpleGrantedAuthority(authority.getName())));
        });
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
                true, true, true, true,
                authorities);
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);
        return userMapper.userEntityToUserDto(userEntity);
    }
}
