package com.ncut.face.erp.service.mission.domain;

import lombok.Data;

import java.util.Date;

@Data
public class MissionModifyVo {
    private Long id;
    private String missionTopic;
    private String missionContent;
    private Date startTime;
    private Date endTime;
    private short finished;
    private String pin;
    private String recipient;
}
