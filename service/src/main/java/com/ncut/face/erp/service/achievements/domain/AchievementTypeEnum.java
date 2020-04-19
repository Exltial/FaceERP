package com.ncut.face.erp.service.achievements.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AchievementTypeEnum {
    PATENT(0, "专利"),
    PAPER(1, "论文");
    private Integer code;
    private String desc;

    public static String of(Integer code) {
        for (AchievementTypeEnum value : AchievementTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.getDesc();
            }
        }
        return "未知类型";
    }
}

