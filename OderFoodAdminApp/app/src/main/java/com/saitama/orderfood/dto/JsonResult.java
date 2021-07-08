package com.saitama.orderfood.dto;

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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
