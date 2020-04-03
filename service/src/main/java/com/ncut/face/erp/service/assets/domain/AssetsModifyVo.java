package com.ncut.face.erp.service.assets.domain;

import lombok.Data;

@Data
public class AssetsModifyVo {
    private Long id;
    private String assetsName;
    private String assetsId;
    private String assetsOwner;
    private String assetsPosition;
}
