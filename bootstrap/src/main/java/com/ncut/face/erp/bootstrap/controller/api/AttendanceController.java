package com.ncut.face.erp.bootstrap.controller.api;

import com.ncut.face.erp.service.attendance.AttendanceService;
import com.ncut.face.erp.service.attendance.domain.AttendanceModel;
import com.ncut.face.erp.service.common.Result;
import com.ncut.face.erp.service.user.domain.UserInfoModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Comparator;
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
    @RequiresPermissions(value = {"ADMIN", "USER"}, logical = Logical.OR)
    @RequiresRoles(value = {"ADMIN", "USER"}, logical = Logical.OR)
    public Result signIn() {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        attendanceService.signIn(user);
        return new Result<>(true);
    }

    /**
     * 签退
     */
    @RequestMapping("/signOut")
    @RequiresPermissions(value = {"ADMIN", "USER"}, logical = Logical.OR)
    @RequiresRoles(value = {"ADMIN", "USER"}, logical = Logical.OR)
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
    public Result attendanceList() {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        List<AttendanceModel> list = attendanceService.getAttendanceList(user);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        list.forEach(item -> {
            if (item.getSignInTime() != null) {
                item.setSignInTimeStr(timeFormat.format(item.getSignInTime()));
            }
            if (item.getSignOutTime() != null) {
                item.setSignOutTimeStr(timeFormat.format(item.getSignOutTime()));
            }
        });
        list.sort(Comparator.comparing(AttendanceModel::getSignInTime).reversed());
        return new Result<>(list);
    }
}
