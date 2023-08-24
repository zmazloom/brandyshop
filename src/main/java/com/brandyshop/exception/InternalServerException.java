package com.brandyshop.exception;

import org.springframework.http.HttpStatus;

public class InternalServerException extends ProjectRuntimeException {

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public static InternalServerException getInstance() {
        return new InternalServerException("خطای داخلی سرور");

    }

    public static InternalServerException getInstance(String msg) {
        return new InternalServerException(msg);

    }

    public static InternalServerException getInstance(String msg, Throwable cause) {
        return new InternalServerException(msg, cause);

    }
}