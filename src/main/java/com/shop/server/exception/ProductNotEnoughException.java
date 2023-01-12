package com.shop.server.exception;

import com.shop.server.model.type.ErrorResponseStatusType;

public class ProductNotEnoughException extends ApplicationException {

    public ProductNotEnoughException(ErrorResponseStatusType responseStatus) {
        super(responseStatus);
    }

    public ProductNotEnoughException(ErrorResponseStatusType responseStatus, Object args) {
        super(responseStatus, args);
    }
}
