package com.ncut.face.erp.service.common;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Page<T> implements Serializable {
    private long totalElements = 0;
    private int size = 10;
    private int page = 1;
    private List<T> content = new ArrayList<>();
}
