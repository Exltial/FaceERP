package com.ncut.face.erp.bootstrap.controller.api;

import com.ncut.face.erp.service.attendance.AttendanceService;
import com.ncut.face.erp.service.attendance.domain.AttendanceModel;
import com.ncut.face.erp.service.common.Result;
import com.ncut.face.erp.service.user.domain.UserInfoModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 考勤
 */
@RequestMapping("/attendance")
@RestController
public class AttendanceController {
    @Resource
    AttendanceService attendanceService;

    /**
     * 签到
     */
    @RequestMapping("/signIn")
    @RequiresPermissions("USER")
    @RequiresRoles("USER")
    public Result signIn() {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        attendanceService.signIn(user);
        return new Result<>(true);
    }

    /**
     * 签退
     */
    @RequestMapping("/signOut")
    @RequiresPermissions("USER")
    @RequiresRoles("USER")
    public Result signOut() {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        attendanceService.signOut(user);
        return new Result<>(true);
    }

    /**
     * 签到列表
     */
    @RequestMapping("/attendanceList")
    @RequiresPermissions(value = {"ADMIN", "USER"}, logical = Logical.OR)
    @RequiresRoles(value = {"ADMIN", "USER"}, logical = Logical.OR)
    public Result attendanceList(String date) {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        if (StringUtils.isEmpty(date)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.format(new Date());
        }
        List<AttendanceModel> list = attendanceService.getAttendanceList(user, date);
        return new Result<>(list);
    }
}
