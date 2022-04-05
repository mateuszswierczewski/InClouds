package pl.mswierczewski.inclouds.dtos.error;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class ApiValidationResponse extends ApiErrorResponse{

    private Map<String, String> fieldsErrors;

    public ApiValidationResponse(String error, String message, HttpStatus httpStatus, Map<String, String> fieldsErrors) {
        super(error, message, httpStatus);
        this.fieldsErrors = fieldsErrors;
    }

    public Map<String, String> getFieldsErrors() {
        return fieldsErrors;
    }

    public void setFieldsErrors(Map<String, String> fieldsErrors) {
        this.fieldsErrors = fieldsErrors;
    }
}
