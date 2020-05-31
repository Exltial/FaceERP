package com.ncut.face.erp.service.mission.repository;

import com.ncut.face.erp.service.mission.domain.MissionAddVo;
import com.ncut.face.erp.service.mission.domain.MissionModel;
import com.ncut.face.erp.service.mission.domain.MissionModifyVo;
import com.ncut.face.erp.service.mission.domain.MissionOperate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MissionRepository {
    void addMission(MissionAddVo vo);

    void deleteMission(MissionOperate opt);

    void modifyMission(MissionModifyVo vo);

    List getMissionList(MissionOperate opt);

    MissionModel getMissionById(MissionOperate opt);
}
