package com.shop.server.model.entity;

import com.shop.server.model.type.Gender;
import com.shop.server.model.type.Role;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User {

    private Long id;
    private String username;
    private String email;
    private String password;
    private LocalDate birthDate;
    private String firstName;
    private String lastName;
    private Role role;
    private Gender gender;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
