package com.shop.server.exception;

import com.shop.server.model.type.ErrorResponseStatusType;

public class ConnectionException extends ApplicationException {

    public ConnectionException(ErrorResponseStatusType statusType) {
        super(statusType);
    }

    public ConnectionException(ErrorResponseStatusType statusType, Object args) {
        super(statusType, args);
    }
}
