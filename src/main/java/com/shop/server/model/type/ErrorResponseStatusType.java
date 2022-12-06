package com.shop.server.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorResponseStatusType {

    BLANK_AUTHORIZATION_HEADER_EXCEPTION("Authorization header cannot be blank"),
    MALFORMED_AUTHORIZATION_HEADER_EXCEPTION("Invalid authorization header"),
    MALFORMED_JWT_EXCEPTION("Invalid JWT token"),
    EXPIRED_JWT_EXCEPTION("JWT token expired"),
    INVALID_CREDENTIALS_EXCEPTION("Invalid credentials"),
    VALIDATION_EXCEPTION("Validation exception"),
    USER_ALREADY_EXISTS_EXCEPTION("User [%s] already exists"),
    USER_NOT_FOUND_EXCEPTION("User [%s] not found"),
    ILLEGAL_DATE_FORMAT_EXCEPTION("Illegal date format"),
    INTERNAL_SERVER_EXCEPTION("%s"),
    DAO_EXCEPTION("%s"),
    CONNECTION_EXCEPTION("%s");


    private final String message;

    public String getMessage(Object args) {
        return String.format(this.getMessage(), args);
    }
}
