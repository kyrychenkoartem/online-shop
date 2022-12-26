package com.shop.server.model.dto.user;

import com.shop.server.model.type.Gender;
import com.shop.server.model.type.Role;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserRegistrationRequest {

    private String username;
    private String email;
    private String password;
    private LocalDate birthDate;
    private String firstName;
    private String lastName;
    private Role role;
    private Gender gender;

}


