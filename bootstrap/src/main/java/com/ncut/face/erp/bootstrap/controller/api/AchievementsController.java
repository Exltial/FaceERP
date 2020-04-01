package com.ncut.face.erp.bootstrap.controller.api;

import com.alibaba.fastjson.JSON;
import com.ncut.face.erp.service.common.Result;
import com.ncut.face.erp.service.user.domain.UserInfoModel;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AchievementsController {
    @RequestMapping("/addAchievement")
    public Result addAchievement() {
        UserInfoModel principal = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        return new Result<>(JSON.toJSONString(principal));
    }
}
