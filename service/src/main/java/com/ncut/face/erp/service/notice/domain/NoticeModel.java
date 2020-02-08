package com.ncut.face.erp.service.notice.domain;

import lombok.Data;

import java.util.Date;

@Data
public class NoticeModel {
    private Long tenantId;
    private Long id;
    private String topic;
    private String content;
    private Date createTime;
    private Date modifyTime;
    private String pin;
    private String creator;
    private String modifier;
}
