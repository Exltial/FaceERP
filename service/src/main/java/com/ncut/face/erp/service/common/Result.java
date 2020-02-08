package com.ncut.face.erp.service.common;

import lombok.Data;

@Data
public class Result<T> {

    private Integer code;
    private boolean success = false;
    private String message;
    private T data;

    private Result() {

    }

    public Result(T data) {
        this.success = true;
        this.code = 0;
        this.data = data;
    }

    public static Result buildFailedRes(Integer code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}