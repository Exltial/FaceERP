package com.ncut.face.erp.service.user.domain;

import lombok.Data;

@Data
public class UserRegistryVo {
    private String tenantId;
    private String userName;
    /**
     * 角色，ADMIN为管理员，USER为用户
     */
    private String role;
    private String faceId;
}
