package com.ncut.face.erp.service.mission.domain;

import lombok.Data;

import java.util.Date;

@Data
public class MissionAddVo {
    private Long tenantId;
    private String missionTopic;
    private String missionContent;
    private Date startTime;
    private Date endTime;
    private String pin;
    private String recipient;
}
