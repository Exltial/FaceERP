package com.ncut.face.erp.service.user.domain;

import lombok.Data;

@Data
public class UserRegistryVo {
    private String tenantId;
    private String userName;
    private String password;
    private String faceId;
}
