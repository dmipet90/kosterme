package com.digitalcomanetwork.kosterme.userservice.shared;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter @Setter
public class UserDto implements Serializable {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String userId;
    private String encryptedPassword;
}
