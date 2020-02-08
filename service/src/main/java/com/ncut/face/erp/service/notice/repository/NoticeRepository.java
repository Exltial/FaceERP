package com.ncut.face.erp.service.notice.repository;

import com.ncut.face.erp.service.notice.domain.NoticeAddVo;
import com.ncut.face.erp.service.notice.domain.NoticeModel;
import com.ncut.face.erp.service.notice.domain.NoticeModifyVo;
import com.ncut.face.erp.service.notice.domain.NoticeOperate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeRepository {
    void addNotice(NoticeAddVo vo);

    List getNoticeList(NoticeOperate opt);

    NoticeModel getNoticeById(NoticeOperate opt);

    void deleteNotice(NoticeOperate opt);

    void modifyNotice(NoticeModifyVo vo);
}
