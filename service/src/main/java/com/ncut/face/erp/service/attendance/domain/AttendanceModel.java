package com.ncut.face.erp.service.attendance.domain;

import lombok.Data;

import java.util.Date;

@Data
public class AttendanceModel {
    private String tenantId;
    private String faceId;
    private String userName;
    private Integer action;
    private Date signInTime;
    private Date signOutTime;
    private String attendanceStatus;
}
