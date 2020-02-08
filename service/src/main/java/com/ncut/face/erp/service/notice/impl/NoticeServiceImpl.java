package com.ncut.face.erp.service.notice.impl;

import com.ncut.face.erp.service.notice.NoticeService;
import com.ncut.face.erp.service.notice.domain.NoticeAddVo;
import com.ncut.face.erp.service.notice.domain.NoticeModel;
import com.ncut.face.erp.service.notice.domain.NoticeModifyVo;
import com.ncut.face.erp.service.notice.domain.NoticeOperate;
import com.ncut.face.erp.service.notice.repository.NoticeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Resource
    NoticeRepository noticeRepository;

    @Override
    public void addNotice(NoticeAddVo vo) {
        noticeRepository.addNotice(vo);
    }

    @Override
    public List getNoticeList(NoticeOperate opt) {
        return noticeRepository.getNoticeList(opt);
    }

    @Override
    public NoticeModel getNoticeById(NoticeOperate opt) {
        return noticeRepository.getNoticeById(opt);
    }

    @Override
    public void deleteNotice(NoticeOperate opt) {
        noticeRepository.deleteNotice(opt);
    }

    @Override
    public void modifyNotice(NoticeModifyVo vo) {
        noticeRepository.modifyNotice(vo);
    }
}
