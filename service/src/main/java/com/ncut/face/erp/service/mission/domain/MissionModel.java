package com.ncut.face.erp.service.mission.domain;

import lombok.Data;

import java.util.Date;

@Data
public class MissionModel {
    private Long id;
    private Long tenantId;
    private String missionTopic;
    private String missionContent;
    private Date startTime;
    private Date endTime;
    private short finished;
    private Date finishedTime;
    private String modifier;
    private Date modifyTime;
    private String publisher;
    private String recipient;
}
