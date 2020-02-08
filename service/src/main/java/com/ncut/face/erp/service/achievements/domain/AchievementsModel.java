package com.ncut.face.erp.service.achievements.domain;

import lombok.Data;

import java.util.Date;

@Data
public class AchievementsModel {
    private Long tenantId;
    private Long id;
    private Integer achieveCategory;
    private String achieveCateName;
    private String creator;
    private String modifier;
    private Date createTime;
    private Date modifyTime;
}
