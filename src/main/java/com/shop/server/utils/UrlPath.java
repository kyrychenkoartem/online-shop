package com.shop.server.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlPath {

    public static final String ALL_PATHS = "/*";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String LOGIN_ERROR_EMAIL = "/login?error&email=";
    public static final String REGISTRATION = "/registration";
    public static final String PRODUCTS = "/products";
    public static final String CART = "/cart";
    public static final String ORDER = "/order";
    public static final String PAY = "/pay";
    public static final String PROMO_CODE = "/promo-code";
}
