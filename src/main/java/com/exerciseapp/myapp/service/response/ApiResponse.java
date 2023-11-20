package com.exerciseapp.myapp.service.response;

import com.exerciseapp.myapp.common.constants.ApiStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ "status", "message", "data" })
public class ApiResponse<T> {

    @JsonProperty("status")
    private String status;

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    public ApiResponse() {}

    public static <T> ApiResponse<T> ok(T data) {
        ApiResponse<T> res = new ApiResponse<>();
        res.status = ApiStatus.OK;
        res.data = data;
        return res;
    }

    public static <T> ApiResponse<T> ok() {
        ApiResponse<T> res = new ApiResponse<>();
        res.status = ApiStatus.OK;
        return res;
    }

    public static <T> ApiResponse<T> failed(String code, String message) {
        ApiResponse<T> res = new ApiResponse<>();
        res.status = ApiStatus.FAILED;
        res.code = code;
        res.message = message;
        return res;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
