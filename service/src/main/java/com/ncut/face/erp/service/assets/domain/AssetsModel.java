package com.ncut.face.erp.service.assets.domain;

import lombok.Data;

import java.util.Date;

@Data
public class AssetsModel {
    private Long id;
    private Long tenantId;
    private String assetsName;
    private String assetsId;
    private String assetsOwner;
    private String assetsPosition;
    private String creator;
    private String modifier;
    private Date createTime;
    private Date modifyTime;
}
