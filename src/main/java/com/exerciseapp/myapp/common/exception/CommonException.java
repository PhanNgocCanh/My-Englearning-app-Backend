package com.exerciseapp.myapp.common.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class CommonException extends RuntimeException {

    private HttpStatus httpStatus;
    private String code;
    private String message;
    private List<Map<String, String>> errors = new ArrayList<>();

    public CommonException() {}

    public static CommonException create(HttpStatus httpStatus) {
        CommonException commonException = new CommonException();
        commonException.setHttpStatus(httpStatus);
        return commonException;
    }

    public CommonException code(String code) {
        this.code = code;
        return this;
    }

    public CommonException message(String message) {
        this.message = message;
        return this;
    }

    public CommonException errors(List<Map<String, String>> errors) {
        this.setErrors(errors);
        return this;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Map<String, String>> getErrors() {
        return errors;
    }

    public void setErrors(List<Map<String, String>> errors) {
        this.errors = errors;
    }
}
