package pl.mswierczewski.inclouds.exceptions.api;

import org.springframework.http.HttpStatus;

public class UserException extends ApiException {

    public final static UserException BAD_CREDENTIALS = create(
            "Bad Credentials", HttpStatus.UNPROCESSABLE_ENTITY
    );

    public final static UserException ACCOUNT_DISABLED = create(
            "Account is disabled", HttpStatus.CONFLICT
    );

    public final static UserException ACCOUNT_LOCKED = create(
            "Account is locked", HttpStatus.FORBIDDEN
    );

    public final static UserException EMAIL_IS_TAKEN = create(
            "The email address already exists in the system", HttpStatus.CONFLICT
    );

    public final static UserException USER_NOT_FOUND = create(
            "User not found", HttpStatus.NOT_FOUND
    );

    public UserException(String msg, HttpStatus status) {
        super(msg, status);
    }

    private static UserException create(String msg, HttpStatus status) {
        return new UserException(msg, status);
    }
}
