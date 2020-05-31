package com.ncut.face.erp.service.notice.impl;

import com.ncut.face.erp.service.common.utils.DateUtil;
import com.ncut.face.erp.service.notice.NoticeService;
import com.ncut.face.erp.service.notice.domain.NoticeModel;
import com.ncut.face.erp.service.notice.domain.NoticeOperate;
import com.ncut.face.erp.service.notice.domain.NoticeVo;
import com.ncut.face.erp.service.notice.repository.NoticeRepository;
import com.ncut.face.erp.service.user.domain.UserInfoModel;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Resource
    NoticeRepository noticeRepository;

    @Override
    public void addNotice(NoticeVo vo) {
        NoticeModel model = new NoticeModel();
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        model.setTenantId(user.getTenantId());
        model.setTopic(vo.getTopic());
        model.setContent(vo.getContent());
        model.setCreator(user.getUserName());
        noticeRepository.addNotice(model);
    }

    @Override
    public List<NoticeModel> getNoticeList() {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        return noticeRepository.getNoticeList(user.getTenantId());
    }

    @Override
    public NoticeModel getNoticeById(NoticeOperate opt) {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        return noticeRepository.getNoticeById(user.getTenantId(), opt.getId());
    }

    @Override
    public void deleteNotice(NoticeOperate opt) {
        noticeRepository.deleteNotice(opt);
    }

    @Override
    public void modifyNotice(NoticeVo vo) {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        NoticeModel model = new NoticeModel();
        model.setId(vo.getId());
        model.setTenantId(user.getTenantId());
        model.setTopic(vo.getTopic());
        model.setContent(vo.getContent());
        model.setModifier(user.getUserName());
        noticeRepository.modifyNotice(model);
    }

    @Override
    public Integer getNoticeCount() {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        return noticeRepository.getNoticeCount(user.getTenantId(), DateUtil.format(new Date(), "yyyy-MM-dd"));
    }
}
