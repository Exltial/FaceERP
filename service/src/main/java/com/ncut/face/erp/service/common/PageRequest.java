package com.ncut.face.erp.service.common;

import lombok.Data;

@Data
public class PageRequest {
    private int page = 1;
    private int pageSize = 10;

    public int getOffSet() {
        return (page - 1) * pageSize;
    }
}
