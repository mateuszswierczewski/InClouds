package pl.mswierczewski.inclouds.exceptions.api;

import org.springframework.http.HttpStatus;

public class JWTException extends ApiException{

    public final static JWTException EXPIRED_JWT = create(
            "JWT is expired", HttpStatus.FORBIDDEN
    );

    public final static JWTException MALFORMED_JWT = create(
            "JWT is malformed", HttpStatus.FORBIDDEN
    );

    public final static JWTException SIGNATURE_ERROR = create(
            "JWT signature is incorrect", HttpStatus.FORBIDDEN
    );

    public final static JWTException JWT_NOT_FOUND = create(
            "JWT not found", HttpStatus.FORBIDDEN
    );

    public JWTException(String msg, HttpStatus status) {
        super(msg, status);
    }

    private static JWTException create(String msg, HttpStatus status) {
        return new JWTException(msg, status);
    }
}
