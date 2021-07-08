package com.orderfood.webservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonResult<T> {
    /**
     * Code: 0 normal, 1 error
     */
    private Integer code;

    /**
     * Thông điệp
     */
    private String msg;

    /**
     * Đối tượng trả về cụ thể
     */
    private T data;

    public JsonResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResult() {
    }

    public JsonResult<T> error() {
        return new JsonResult<T>(1, "System error", null);
    }

    public JsonResult<T> error(String msg) {
        return new JsonResult<T>(1, msg, null);
    }

    public JsonResult<T> success() {
        return new JsonResult<T>(0, "Thành công", null);
    }

    public JsonResult<T> success(T data) { return new JsonResult<T>(0, "Thành công", data);}

    public JsonResult<T> success(String msg, T data) {
        return new JsonResult<T>(0, msg, data);
    }

}
