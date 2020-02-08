package com.ncut.face.erp.service.notice.domain;

import lombok.Data;


@Data
public class NoticeAddVo {
    private Long tenantId;
    private String topic;
    private String content;
    private String pin;
}
