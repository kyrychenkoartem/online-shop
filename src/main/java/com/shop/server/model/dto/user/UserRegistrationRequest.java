package com.shop.server.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationRequest {

    private String username;
    private String email;
    private String password;
    private String birthDate;
    private String firstName;
    private String lastName;
    private String role;
    private String gender;

}


