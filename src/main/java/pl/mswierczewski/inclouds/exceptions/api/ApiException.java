package pl.mswierczewski.inclouds.exceptions.api;

import org.springframework.http.HttpStatus;

public abstract class ApiException extends RuntimeException {

    private final HttpStatus status;

    protected ApiException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }

    public ApiException(String msg, Throwable cause, HttpStatus status) {
        super(msg, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
