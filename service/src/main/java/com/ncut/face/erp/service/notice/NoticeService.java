package com.ncut.face.erp.service.notice;

import com.ncut.face.erp.service.notice.domain.NoticeAddVo;
import com.ncut.face.erp.service.notice.domain.NoticeModel;
import com.ncut.face.erp.service.notice.domain.NoticeModifyVo;
import com.ncut.face.erp.service.notice.domain.NoticeOperate;

import java.util.List;

public interface NoticeService {
    void addNotice(NoticeAddVo vo);

    List getNoticeList(NoticeOperate opt);

    NoticeModel getNoticeById(NoticeOperate opt);

    void deleteNotice(NoticeOperate opt);

    void modifyNotice(NoticeModifyVo vo);
}
