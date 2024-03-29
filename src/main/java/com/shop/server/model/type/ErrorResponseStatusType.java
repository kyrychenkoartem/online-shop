package com.shop.server.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorResponseStatusType {

    BLANK_AUTHORIZATION_HEADER_EXCEPTION("Authorization header cannot be blank"),
    MALFORMED_AUTHORIZATION_HEADER_EXCEPTION("Invalid authorization header"),
    MALFORMED_JWT_EXCEPTION("Invalid JWT token"),
    EXPIRED_JWT_EXCEPTION("JWT token expired"),
    INVALID_CREDENTIALS_EXCEPTION("Invalid credentials"),
    VALIDATION_EXCEPTION("Validation exception"),
    ARGUMENT_NOT_FOUND_EXCEPTION("Argument [%s] not found"),
    ARGUMENTS_NOT_FOUND_EXCEPTION("Arguments not found"),
    USER_ALREADY_EXISTS_EXCEPTION("User [%s] already exists"),
    USER_NOT_FOUND_EXCEPTION("User [%s] not found"),
    USERS_NOT_FOUND_EXCEPTION("Users not found"),
    PRODUCT_ALREADY_EXISTS_EXCEPTION("Product [%s] already exists"),
    PRODUCT_NOT_FOUND_EXCEPTION("Product [%s] not found"),
    PRODUCTS_NOT_FOUND_EXCEPTION("Products not found"),
    PRODUCT_NOT_ENOUGH_EXCEPTION("Product [%s] not enough"),
    PROMO_CODE_NOT_FOUND_EXCEPTION("Promo code [%s] not found"),
    PROMO_CODES_NOT_FOUND_EXCEPTION("Promo codes not found"),
    CART_NOT_FOUND_EXCEPTION("Cart [%s] not found"),
    CARTS_NOT_FOUND_EXCEPTION("Carts not found"),
    PRODUCT_ITEM_NOT_FOUND_EXCEPTION("Product Item [%s] not found"),
    ORDER_NOT_FOUND_EXCEPTION("Order [%s] not found"),
    ILLEGAL_DATE_FORMAT_EXCEPTION("Illegal date format"),
    ILLEGAL_EMAIL_FORMAT_EXCEPTION("Illegal email format"),
    ILLEGAL_USERNAME_FORMAT_EXCEPTION("Illegal username format"),
    ILLEGAL_CARD_NUMBER_FORMAT_EXCEPTION("Illegal card number format"),
    ILLEGAL_CARD_EXPIRY_DATE_FORMAT_EXCEPTION("Illegal card expiry date format"),
    ILLEGAL_CARD_CVV_FORMAT_EXCEPTION("Illegal card cvv format"),
    INTERNAL_SERVER_EXCEPTION("%s"),
    DAO_EXCEPTION("%s"),
    CONNECTION_EXCEPTION("%s"),
    TRANSACTION_EXCEPTION("Transaction exception: %s");


    private final String message;

    public String getMessage(Object args) {
        return String.format(this.getMessage(), args);
    }
}
