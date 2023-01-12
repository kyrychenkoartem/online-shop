package com.shop.server.exception;

import com.shop.server.validator.Error;
import java.util.List;
import lombok.Getter;

public class ValidationException extends ApplicationException {

    @Getter
    private final List<Error> errors;

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
}