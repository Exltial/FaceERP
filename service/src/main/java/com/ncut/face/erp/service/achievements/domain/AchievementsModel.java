package com.ncut.face.erp.service.achievements.domain;

import lombok.Data;

import java.util.Date;

@Data
public class AchievementsModel {
    private Long id;
    private String tenantId;
    private AchievementTypeEnum achTypeEnum;
    private Integer achType;
    private String achTypeDesc;
    private String achName;
    private String fileId;
    private String achAuthor;
    private String creator;
    private String modifier;
    private Date createTime;
    private Date modifyTime;
    private String createTimeDesc;
    private String modifyTimeDesc;
}
