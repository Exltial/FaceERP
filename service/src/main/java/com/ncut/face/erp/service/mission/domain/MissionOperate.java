package com.ncut.face.erp.service.mission.domain;

import com.ncut.face.erp.service.common.PageRequest;
import lombok.Data;

@Data
public class MissionOperate {
    private Long tenantId;
    private Long id;
    private PageRequest pageRequest = new PageRequest();
}
