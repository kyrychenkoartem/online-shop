package com.shop.server.model.dto.user;

import com.shop.server.model.type.Gender;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserUpdateRequest {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
}
