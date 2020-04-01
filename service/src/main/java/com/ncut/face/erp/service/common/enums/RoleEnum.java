package com.ncut.face.erp.service.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    ADMIN("管理员", "ADMIN"),
    USER("用户", "USER");
    private String roleName;
    private String roleCode;

    public static boolean isMatched(String code) {
        if (StringUtils.isEmpty(code)) {
            return false;
        }
        RoleEnum[] values = RoleEnum.values();
        for (RoleEnum value : values) {
            if (code.equals(value.getRoleCode())) {
                return true;
            }
        }
        return false;
    }
}
