package com.shop.server.model.dto.user;

import com.shop.server.model.type.Role;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private LocalDate birthDate;
    private String firstName;
    private String lastName;
    private Role role;
}
