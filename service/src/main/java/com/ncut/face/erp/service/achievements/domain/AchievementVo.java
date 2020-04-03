package com.ncut.face.erp.service.achievements.domain;

import lombok.Data;


@Data
public class AchievementVo {
    private Long id;
    /**
     * 成果类型
     * {@link AchievementTypeEnum}
     */
    private Integer achType;
    private String achName;
    private String achUrl;
    private String achAuthor;
}
