package com.shop.server.exception;

import com.shop.server.model.type.ErrorResponseStatusType;

public class ProductItemNotFoundException extends ApplicationException {

    public ProductItemNotFoundException(ErrorResponseStatusType responseStatus) {
        super(responseStatus);
    }

    public ProductItemNotFoundException(ErrorResponseStatusType responseStatus, Object args) {
        super(responseStatus, args);
    }
}
