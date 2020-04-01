package com.ncut.face.erp.service.attendance.domain;

import lombok.Data;

@Data
public class AttendanceQuery {
    private String tenantId;
    private String faceId;
    private String date;
}
