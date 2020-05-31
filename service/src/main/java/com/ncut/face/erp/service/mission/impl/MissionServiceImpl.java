package com.ncut.face.erp.service.mission.impl;

import com.ncut.face.erp.service.mission.MissionService;
import com.ncut.face.erp.service.mission.domain.MissionAddVo;
import com.ncut.face.erp.service.mission.domain.MissionModel;
import com.ncut.face.erp.service.mission.domain.MissionModifyVo;
import com.ncut.face.erp.service.mission.domain.MissionOperate;
import com.ncut.face.erp.service.mission.repository.MissionRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MissionServiceImpl implements MissionService {
    @Resource
    MissionRepository missionRepository;

    @Override
    public void addMission(MissionAddVo vo) {
        missionRepository.addMission(vo);
    }

    @Override
    public void deleteMission(MissionOperate opt) {
        missionRepository.deleteMission(opt);
    }

    @Override
    public void modifyMission(MissionModifyVo vo) {
        missionRepository.modifyMission(vo);
    }

    @Override
    public List getMissionList(MissionOperate opt) {
        return missionRepository.getMissionList(opt);
    }

    @Override
    public MissionModel getMissionById(MissionOperate opt) {
        return missionRepository.getMissionById(opt);
    }
}
