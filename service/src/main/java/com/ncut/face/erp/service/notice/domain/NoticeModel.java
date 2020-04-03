package com.ncut.face.erp.service.notice.domain;

import lombok.Data;

import java.util.Date;

@Data
public class NoticeModel {
    private Long id;
    private String tenantId;
    private String topic;
    private String content;
    private String creator;
    private String modifier;
    private Date createTime;
    private Date modifyTime;
}
