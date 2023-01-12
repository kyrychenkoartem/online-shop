package com.shop.server.exception;

import com.shop.server.model.type.ErrorResponseStatusType;

public class CartNotFoundException extends ApplicationException {

    public CartNotFoundException(ErrorResponseStatusType responseStatus) {
        super(responseStatus);
    }

    public CartNotFoundException(ErrorResponseStatusType responseStatus, Object args) {
        super(responseStatus, args);
    }
}
