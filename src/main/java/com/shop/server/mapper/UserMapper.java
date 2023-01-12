package com.shop.server.mapper;

import com.shop.server.exception.NotFoundException;
import com.shop.server.model.dto.user.UserRegistrationRequest;
import com.shop.server.model.dto.user.UserResponse;
import com.shop.server.model.dto.user.UserUpdateRequest;
import com.shop.server.model.entity.User;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.model.type.Gender;
import com.shop.server.model.type.Role;
import com.shop.server.utils.LocalDateFormatter;
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
            throw new NotFoundException(ErrorResponseStatusType.ARGUMENT_NOT_FOUND_EXCEPTION, entity);
        }
        return UserResponse.builder()
                .id(entity.getId())
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
            throw new NotFoundException(ErrorResponseStatusType.ARGUMENTS_NOT_FOUND_EXCEPTION);
        }
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(password)
                .birthDate(LocalDateFormatter.format(request.getBirthDate()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(Role.valueOf(request.getRole()))
                .gender(Gender.valueOf(request.getGender()))
                .build();
    }

    public User toEntity(UserUpdateRequest request, User user) {
        if (ObjectUtils.isEmpty(request)) {
            throw new NotFoundException(ErrorResponseStatusType.ARGUMENTS_NOT_FOUND_EXCEPTION);
        }
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setBirthDate(request.getBirthDate());
        user.setGender(request.getGender());
        return user;
    }
}
