package com.brandyshop.exception;

import org.springframework.http.HttpStatus;

public class ResourceConflictException extends ProjectRuntimeException {

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }

    public ResourceConflictException(String message) {
        super(message);
    }


    public static ResourceConflictException getInstance() {
        return new ResourceConflictException("اطلاعات تکراری!");

    }

    public static ResourceConflictException getInstance(String msg) {
        return new ResourceConflictException(msg);

    }
}
