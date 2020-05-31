package com.ncut.face.erp.service.monitor;

import com.ncut.face.erp.service.monitor.domain.MonitorModel;
import com.ncut.face.erp.service.monitor.domain.MonitorVo;

import java.util.List;

public interface MonitorService {
    void addCam(MonitorVo vo);

    List<MonitorModel> getCamList();

    MonitorModel getCamById(MonitorVo opt);

    void deleteCam(MonitorVo opt);
}
