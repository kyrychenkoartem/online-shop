package com.shop.server.exception;

import com.shop.server.model.type.ErrorResponseStatusType;

public class DaoException extends ApplicationException {

    public DaoException(ErrorResponseStatusType statusType) {
        super(statusType);
    }

    public DaoException(ErrorResponseStatusType statusType, Object args) {
        super(statusType, args);
    }
}
