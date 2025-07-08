package com.pts.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseObject<T> implements Serializable{

    private boolean success;  // thành công true hoặc false
    private int code;         // mã trạng thái (200, 400, 500)
    private String message;   // thông điệp đi kèm
    private T data;           // dữ liệu trả về (nếu có)

    public ResponseObject() {}

    public ResponseObject(T data) {
        this.data = data;
    }

    public ResponseObject(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ResponseObject(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseObject(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // SUCCESS FACTORY METHODS
    public static <T> ResponseObject<T> ok() {
        return new ResponseObject<>(true, 200, "Success", null);
    }

    public static <T> ResponseObject<T> ok(T data) {
        return new ResponseObject<>(true, 200, "Success", data);
    }

    public static <T> ResponseObject<T> ok(String message) {
        return new ResponseObject<>(true, 200, message, null);
    }

    public static <T> ResponseObject<T> ok(String message, T data) {
        return new ResponseObject<>(true, 200, message, data);
    }

    // ERROR FACTORY METHODS
    public static <T> ResponseObject<T> error(int code) {
        return new ResponseObject<>(false, code, "Failure", null);
    }

    public static <T> ResponseObject<T> error(int code, String message) {
        return new ResponseObject<>(false, code, message, null);
    }

    public static <T> ResponseObject<T> error(int code, String message, T data) {
        return new ResponseObject<>(false, code, message, data);
    }

    public static <T> ResponseObject<T> error(String message, T data) {
        return new ResponseObject<>(false, 500, message, data);
    }
}
