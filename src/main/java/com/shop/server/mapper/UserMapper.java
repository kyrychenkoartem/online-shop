package com.shop.server.mapper;

import com.shop.server.model.dto.user.UserRegistrationRequest;
import com.shop.server.model.dto.user.UserResponse;
import com.shop.server.model.dto.user.UserUpdateRequest;
import com.shop.server.model.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    private static final UserMapper INSTANCE = new UserMapper();

    public static UserMapper getInstance() {
        return INSTANCE;
    }

    public UserResponse toResponse(User entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return null;
        }
        return UserResponse.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .birthDate(entity.getBirthDate())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .role(entity.getRole())
                .build();
    }

    public User toEntity(UserRegistrationRequest request, String password) {
        if (ObjectUtils.isEmpty(request)) {
            return null;
        }
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(password)
                .birthDate(request.getBirthDate())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(request.getRole())
                .gender(request.getGender())
                .build();
    }

    public User toEntity(UserUpdateRequest request, User user) {
        if (ObjectUtils.isEmpty(request)) {
            return null;
        }
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setBirthDate(request.getBirthDate());
        user.setGender(request.getGender());
        return user;
    }
}
