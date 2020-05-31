package com.ncut.face.erp.service.mission;

import com.ncut.face.erp.service.mission.domain.MissionAddVo;
import com.ncut.face.erp.service.mission.domain.MissionModel;
import com.ncut.face.erp.service.mission.domain.MissionModifyVo;
import com.ncut.face.erp.service.mission.domain.MissionOperate;

import java.util.List;

public interface MissionService {

    void addMission(MissionAddVo vo);

    void deleteMission(MissionOperate opt);

    void modifyMission(MissionModifyVo vo);

    List getMissionList(MissionOperate opt);

    MissionModel getMissionById(MissionOperate opt);
}
