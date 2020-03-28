package com.ncut.face.erp.service.notice.domain;

import com.ncut.face.erp.service.common.PageRequest;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nonnull;

@Data
public class NoticeOperate {
    @NonNull
    private Long tenantId;
    @Nonnull
    private Long id;
    @Nonnull
    private PageRequest pageRequest = new PageRequest();
}
