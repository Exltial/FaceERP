package com.ncut.face.erp.service.notice.domain;

import lombok.Data;


@Data
public class NoticeModifyVo {
    private Long tenantId;
    private Long id;
    private String topic;
    private String content;
    private String pin;
}
