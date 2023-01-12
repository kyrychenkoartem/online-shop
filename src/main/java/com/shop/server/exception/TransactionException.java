package com.shop.server.exception;

import com.shop.server.model.type.ErrorResponseStatusType;

public class TransactionException extends ApplicationException {

    public TransactionException(ErrorResponseStatusType statusType) {
        super(statusType);
    }

    public TransactionException(ErrorResponseStatusType statusType, Object args) {
        super(statusType, args);
    }
}
