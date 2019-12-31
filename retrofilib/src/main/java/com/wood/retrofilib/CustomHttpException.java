package com.wood.retrofilib;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomHttpException extends Exception {
    private int errorCode;
    private String message;

    public CustomHttpException() {
    }

    public CustomHttpException(int errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }
}
