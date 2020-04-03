package com.ncut.face.erp.service.notice.repository;

import com.ncut.face.erp.service.notice.domain.NoticeModel;
import com.ncut.face.erp.service.notice.domain.NoticeOperate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeRepository {
    void addNotice(NoticeModel model);

    List getNoticeList(String tenantId);

    void deleteNotice(NoticeOperate opt);

    void modifyNotice(NoticeModel vo);

    NoticeModel getNoticeById(@Param("tenantId") String tenantId, @Param("id") Long id);
}
