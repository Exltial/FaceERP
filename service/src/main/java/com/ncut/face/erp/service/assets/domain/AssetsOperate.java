package com.ncut.face.erp.service.assets.domain;

import com.ncut.face.erp.service.common.PageRequest;
import lombok.Data;

@Data
public class AssetsOperate {
    private Long id;
    private Long tenantId;
    private PageRequest pageRequest = new PageRequest();
}
