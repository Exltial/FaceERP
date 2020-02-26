package com.ncut.face.erp.service.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    ADMIN("管理员", "ADMIN"),
    USER("用户", "USER");
    private String roleName;
    private String roleCode;
}
