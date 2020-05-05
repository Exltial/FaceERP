package com.ncut.face.erp.service.monitor.repository;

import com.ncut.face.erp.service.monitor.domain.MonitorModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MonitorRepository {
    void addCam(MonitorModel monitorModel);

    List<MonitorModel> findCamList(String tenantId);

    MonitorModel getCamById(@Param("tenantId") String tenantId, @Param("id") Long id);

    void deleteCam(@Param("tenantId") String tenantId, @Param("id") Long id);
}
