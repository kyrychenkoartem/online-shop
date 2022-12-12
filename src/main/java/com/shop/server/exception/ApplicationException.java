package com.shop.server.exception;

import com.shop.server.model.type.ErrorResponseStatusType;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationException extends RuntimeException {

    private ErrorResponseStatusType responseStatus;

    private Object details;

    public ApplicationException(ErrorResponseStatusType responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }

    public ApplicationException(ErrorResponseStatusType responseStatus, Object args) {
        super(responseStatus.getMessage(args));
        this.responseStatus = responseStatus;
    }

    public ApplicationException(ErrorResponseStatusType responseStatus, List<?> details) {
        super(responseStatus.getMessage());
        this.details = details;
        this.responseStatus = responseStatus;
    }
}
