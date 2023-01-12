package com.shop.server.validator;

public interface Validator<T> {

    ValidationResult isValid(T object);
}
