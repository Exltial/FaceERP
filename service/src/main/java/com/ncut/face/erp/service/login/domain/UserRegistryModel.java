package com.ncut.face.erp.service.login.domain;

import lombok.Data;

@Data
public class UserRegistryModel {
    private String tenantId;
    private String userName;
    private String picId;
    private String password;
    private String userRole;
}
