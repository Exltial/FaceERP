package com.ncut.face.erp.bootstrap.controller.api;

import com.ncut.face.erp.service.achievements.AchievementsService;
import com.ncut.face.erp.service.achievements.domain.AchievementOpt;
import com.ncut.face.erp.service.achievements.domain.AchievementVo;
import com.ncut.face.erp.service.achievements.domain.AchievementsModel;
import com.ncut.face.erp.service.common.Result;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/achievement")
public class AchievementsController {
    @Resource
    AchievementsService achievementsService;

    @RequestMapping("/addAchievement")
    @RequiresPermissions(value = {"ADMIN", "USER"}, logical = Logical.OR)
    @RequiresRoles(value = {"ADMIN", "USER"}, logical = Logical.OR)
    public Result addAchievement(@RequestBody AchievementVo vo) {
        achievementsService.addAch(vo);
        return new Result<>(true);
    }

    @RequestMapping("/getAchievementList")
    @RequiresPermissions(value = {"ADMIN", "USER"}, logical = Logical.OR)
    @RequiresRoles(value = {"ADMIN", "USER"}, logical = Logical.OR)
    public Result getAchievementList() {
        List<AchievementsModel> list = achievementsService.getAchList();
        return new Result<>(list);
    }

    @RequestMapping("/deleteAchievement")
    @RequiresPermissions(value = "ADMIN")
    @RequiresRoles(value = "ADMIN")
    public Result deleteAchievement(@RequestBody AchievementOpt opt) {
        achievementsService.deleteAch(opt);
        return new Result<>(true);
    }

    @RequestMapping("/modifyAchievement")
    @RequiresPermissions(value = {"ADMIN", "USER"}, logical = Logical.OR)
    @RequiresRoles(value = {"ADMIN", "USER"}, logical = Logical.OR)
    public Result modifyAchievement(@RequestBody AchievementVo opt) {
        achievementsService.modifyAch(opt);
        return new Result<>(true);
    }
}
