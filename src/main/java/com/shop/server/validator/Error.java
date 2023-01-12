package com.shop.server.validator;

import com.shop.server.model.type.ErrorResponseStatusType;
import lombok.Value;

@Value(staticConstructor = "of")
public class Error {

    ErrorResponseStatusType code;
    String message;
}
