package com.ncut.face.erp.service.monitor.domain;

import lombok.Data;

@Data
public class MonitorModel {
    private Long id;
    private String tenantId;
    private String camName;
    private String camUrl;
    private String creator;
}
