package com.ncut.face.erp.service.user.domain;

import lombok.Data;

@Data
public class UserInfoModel {
    private String tenantId;
    private String userName;
    private String faceId;
    private String password;
    private String userRole;
    private String imgUrl;
    private byte[] feature;
}
