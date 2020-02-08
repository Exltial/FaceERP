package com.ncut.face.erp.service.notice.domain;

import com.ncut.face.erp.service.common.PageRequest;
import lombok.Data;

@Data
public class NoticeOperate {
    private Long tenantId;
    private Long id;
    private PageRequest pageRequest = new PageRequest();
}
