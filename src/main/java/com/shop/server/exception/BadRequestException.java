package com.shop.server.exception;

import com.shop.server.model.type.ErrorResponseStatusType;

public class BadRequestException extends ApplicationException {

    public BadRequestException(ErrorResponseStatusType statusType) {
        super(statusType);
    }

    public BadRequestException(ErrorResponseStatusType statusType, Object args) {
        super(statusType, args);
    }
}
