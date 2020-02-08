package com.ncut.face.erp.service.assets.domain;

import lombok.Data;

@Data
public class AssetsModifyVo {
    private Long id;
    private Long tenantId;
    private String assetsName;
    private String assetsId;
    private String assetsOwner;
    private String assetsPosition;
    private String pin;
}
