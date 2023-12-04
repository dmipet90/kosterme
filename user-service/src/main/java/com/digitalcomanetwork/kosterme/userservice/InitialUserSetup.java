package com.digitalcomanetwork.kosterme.userservice;

import com.digitalcomanetwork.kosterme.userservice.data.AuthorityEntity;
import com.digitalcomanetwork.kosterme.userservice.data.AuthorityRepository;
import com.digitalcomanetwork.kosterme.userservice.data.RoleEntity;
import com.digitalcomanetwork.kosterme.userservice.data.RoleRepository;
import com.digitalcomanetwork.kosterme.userservice.data.UserEntity;
import com.digitalcomanetwork.kosterme.userservice.data.UserRepository;
import com.digitalcomanetwork.kosterme.userservice.ui.constant.Authority;
import com.digitalcomanetwork.kosterme.userservice.ui.constant.Role;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InitialUserSetup {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    @Transactional
    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("From Application Ready Event");
        AuthorityEntity readAuthority = createAuthority(Authority.READ.name());
        AuthorityEntity writeAuthority = createAuthority(Authority.WRITE.name());
        AuthorityEntity deleteAuthority = createAuthority(Authority.DELETE.name());

        createRole(Role.ROLE_USER.name(), Arrays.asList(readAuthority, writeAuthority));
        RoleEntity roleAdmin = createRole(Role.ROLE_ADMIN.name(), Arrays.asList(readAuthority, writeAuthority, deleteAuthority));
        UserEntity adminUser = new UserEntity();
        adminUser.setFirstName("Dmitri");
        adminUser.setLastName("Petrussevits");
        adminUser.setEmail("admin@digital-coma.net");
        adminUser.setUserId(UUID.randomUUID().toString());
        adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("Gnusmasc49@"));
        adminUser.setRoles(List.of(roleAdmin));
        UserEntity storedAdminUser = userRepository.findByEmail(adminUser.getEmail());
        if (storedAdminUser == null) {
            userRepository.save(adminUser);
        }
    }

    @Transactional
    private AuthorityEntity createAuthority(String name) {
        AuthorityEntity authority = authorityRepository.findByName(name);
        if (authority == null) {
            authority = new AuthorityEntity(name);
            authorityRepository.save(authority);
        }
        return authority;
    }

    @Transactional
    private RoleEntity createRole(String name, Collection<AuthorityEntity> authorities) {
        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name, authorities);
            roleRepository.save(role);
        }
        return role;
    }
}
