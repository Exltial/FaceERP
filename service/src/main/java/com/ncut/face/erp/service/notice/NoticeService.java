package com.ncut.face.erp.service.notice;

import com.ncut.face.erp.service.notice.domain.NoticeModel;
import com.ncut.face.erp.service.notice.domain.NoticeOperate;
import com.ncut.face.erp.service.notice.domain.NoticeVo;

import java.util.List;

public interface NoticeService {
    void addNotice(NoticeVo vo);

    List<NoticeModel> getNoticeList();

    NoticeModel getNoticeById(NoticeOperate opt);

    void deleteNotice(NoticeOperate opt);

    void modifyNotice(NoticeVo vo);
}
