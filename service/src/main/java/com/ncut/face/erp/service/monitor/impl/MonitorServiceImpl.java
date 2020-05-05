package com.ncut.face.erp.service.monitor.impl;

import com.ncut.face.erp.service.monitor.MonitorService;
import com.ncut.face.erp.service.monitor.domain.MonitorModel;
import com.ncut.face.erp.service.monitor.domain.MonitorVo;
import com.ncut.face.erp.service.monitor.repository.MonitorRepository;
import com.ncut.face.erp.service.user.domain.UserInfoModel;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MonitorServiceImpl implements MonitorService {
    @Resource
    MonitorRepository monitorRepository;

    @Override
    public void addCam(MonitorVo vo) {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        MonitorModel monitorModel = new MonitorModel();
        monitorModel.setTenantId(user.getTenantId());
        monitorModel.setCamName(vo.getCamName());
        monitorModel.setCamUrl(vo.getCamUrl());
        monitorModel.setCreator(user.getUserName());
        monitorRepository.addCam(monitorModel);
    }

    @Override
    public List<MonitorModel> getCamList() {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        return monitorRepository.findCamList(user.getTenantId());
    }

    @Override
    public MonitorModel getCamById(MonitorVo opt) {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        return monitorRepository.getCamById(user.getTenantId(), opt.getId());
    }

    @Override
    public void deleteCam(MonitorVo opt) {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        monitorRepository.deleteCam(user.getTenantId(), opt.getId());
    }
}
