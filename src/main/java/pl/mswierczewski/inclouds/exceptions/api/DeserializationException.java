package pl.mswierczewski.inclouds.exceptions.api;

import org.springframework.http.HttpStatus;

public class DeserializationException extends ApiException{

    public final static DeserializationException WRONG_DATE_FORMAT = create(
            "Wrong date format", HttpStatus.BAD_REQUEST
    );

    public DeserializationException(String msg, HttpStatus status) {
        super(msg, status);
    }

    private static DeserializationException create(String msg, HttpStatus status) {
        return new DeserializationException(msg, status);
    }
}
