package com.shop.server.validator.impl;

import com.shop.server.matcher.EmailMatcher;
import com.shop.server.matcher.UsernameMatcher;
import com.shop.server.model.dto.user.UserRegistrationRequest;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.utils.LocalDateFormatter;
import com.shop.server.validator.Error;
import com.shop.server.validator.ValidationResult;
import com.shop.server.validator.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRegistrationRequestValidator implements Validator<UserRegistrationRequest> {

    private static final UserRegistrationRequestValidator INSTANCE = new UserRegistrationRequestValidator();

    @Override
    public ValidationResult isValid(UserRegistrationRequest registrationRequest) {
        var validationResult = new ValidationResult();
        if (!UsernameMatcher.isMatch(registrationRequest.getUsername())) {
            validationResult.add(Error.of(ErrorResponseStatusType.ILLEGAL_USERNAME_FORMAT_EXCEPTION, registrationRequest.getUsername()));
        }
        if (!EmailMatcher.isMatch(registrationRequest.getEmail())) {
            validationResult.add(Error.of(ErrorResponseStatusType.ILLEGAL_EMAIL_FORMAT_EXCEPTION, registrationRequest.getEmail()));
        }
        if (registrationRequest.getPassword() == null) {
            validationResult.add(Error.of(ErrorResponseStatusType.ARGUMENT_NOT_FOUND_EXCEPTION, "password can't be blank"));
        }
        if (!LocalDateFormatter.isValid(registrationRequest.getBirthDate())) {
            validationResult.add(Error.of(ErrorResponseStatusType.ILLEGAL_DATE_FORMAT_EXCEPTION, registrationRequest.getBirthDate()));
        }
        if (registrationRequest.getFirstName() == null) {
            validationResult.add(Error.of(ErrorResponseStatusType.ARGUMENT_NOT_FOUND_EXCEPTION, "firstName can't be blank"));
        }
        if (registrationRequest.getLastName() == null) {
            validationResult.add(Error.of(ErrorResponseStatusType.ARGUMENT_NOT_FOUND_EXCEPTION, "lastName can't be blank"));
        }
        if (registrationRequest.getRole() == null) {
            validationResult.add(Error.of(ErrorResponseStatusType.ARGUMENT_NOT_FOUND_EXCEPTION, "role can't be blank"));
        }
        return validationResult;
    }

    public static UserRegistrationRequestValidator getInstance() {
        return INSTANCE;
    }
}
