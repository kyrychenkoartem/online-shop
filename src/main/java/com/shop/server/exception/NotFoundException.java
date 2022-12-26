package com.shop.server.exception;

import com.shop.server.model.type.ErrorResponseStatusType;

public class NotFoundException extends ApplicationException {

    public NotFoundException(ErrorResponseStatusType responseStatus) {
        super(responseStatus);
    }

    public NotFoundException(ErrorResponseStatusType responseStatus, Object args) {
        super(responseStatus, args);
    }
}
