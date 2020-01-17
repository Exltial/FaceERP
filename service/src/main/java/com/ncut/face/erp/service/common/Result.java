package com.ncut.face.erp.service.common;

import lombok.Data;

@Data
public class Result<T> {

    private Integer code;
    private boolean success = false;
    private String message;
    private T data;

    public Result(T data) {
        this.success = true;
        this.code = 0;
        this.data = data;
    }
}