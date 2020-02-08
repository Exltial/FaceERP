package com.ncut.face.erp.bootstrap.controller.api;

import com.ncut.face.erp.service.common.Result;
import com.ncut.face.erp.service.mission.MissionService;
import com.ncut.face.erp.service.mission.domain.MissionAddVo;
import com.ncut.face.erp.service.mission.domain.MissionModel;
import com.ncut.face.erp.service.mission.domain.MissionModifyVo;
import com.ncut.face.erp.service.mission.domain.MissionOperate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class MissionController {
    @Resource
    MissionService missionService;

    @RequestMapping("/addMission")
    public Result addMission(MissionAddVo vo) {
        missionService.addMission(vo);
        return new Result<>(true);
    }

    @RequestMapping("/deleteMission")
    public Result deleteMission(MissionOperate opt) {
        missionService.deleteMission(opt);
        return new Result<>(true);
    }

    @RequestMapping("/modifyMission")
    public Result modifyMission(MissionModifyVo vo) {
        missionService.modifyMission(vo);
        return new Result<>(true);
    }

    @RequestMapping("/getMissionList")
    public Result getMissionList(MissionOperate opt) {
        List list = missionService.getMissionList(opt);
        return new Result<>(list);
    }

    @RequestMapping("/getMissionById")
    public Result getMissionById(MissionOperate opt) {
        MissionModel model = missionService.getMissionById(opt);
        return new Result<>(model);
    }
}
