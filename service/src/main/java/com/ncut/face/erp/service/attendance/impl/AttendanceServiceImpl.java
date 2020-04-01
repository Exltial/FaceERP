package com.ncut.face.erp.service.attendance.impl;

import com.google.common.collect.Lists;
import com.ncut.face.erp.service.attendance.AttendanceService;
import com.ncut.face.erp.service.attendance.domain.AttendanceModel;
import com.ncut.face.erp.service.attendance.domain.AttendanceQuery;
import com.ncut.face.erp.service.attendance.repository.AttendanceRepository;
import com.ncut.face.erp.service.common.enums.RoleEnum;
import com.ncut.face.erp.service.user.domain.UserInfoModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Resource
    AttendanceRepository attendanceRepository;
    @Value("${attendance.hour}")
    private Integer attendanceHour;

    @Override
    public void signIn(UserInfoModel user) {
        AttendanceModel attendanceModel = new AttendanceModel();
        attendanceModel.setTenantId(user.getTenantId());
        attendanceModel.setFaceId(user.getFaceId());
        attendanceModel.setUserName(user.getUserName());
        //1为签到
        attendanceModel.setAction(1);
        attendanceModel.setSignInTime(new Date());
        attendanceRepository.signIn(attendanceModel);
    }

    @Override
    public void signOut(UserInfoModel user) {
        AttendanceModel attendanceModel = new AttendanceModel();
        attendanceModel.setTenantId(user.getTenantId());
        attendanceModel.setFaceId(user.getFaceId());
        attendanceModel.setUserName(user.getUserName());
        //1为签到
        attendanceModel.setAction(2);
        attendanceModel.setSignOutTime(new Date());
        attendanceRepository.signOut(attendanceModel);
    }

    @Override
    public List<AttendanceModel> getAttendanceList(UserInfoModel user, String date) {
        AttendanceQuery query = new AttendanceQuery();
        query.setTenantId(user.getTenantId());
        query.setDate(date);
        if (!RoleEnum.ADMIN.getRoleCode().equals(user.getUserRole())) {
            //查用户签到信息
            query.setFaceId(user.getFaceId());
        }
        List<AttendanceModel> attendanceList = attendanceRepository.getAttendanceList(query);
        if (CollectionUtils.isEmpty(attendanceList)) {
            return Lists.newArrayList();
        }
        List<AttendanceModel> list = new ArrayList<>();
        Map<String, List<AttendanceModel>> map = attendanceList.stream().collect(Collectors.groupingBy(AttendanceModel::getFaceId));
        for (Map.Entry<String, List<AttendanceModel>> entry : map.entrySet()) {
            AttendanceModel model = new AttendanceModel();
            model.setFaceId(entry.getKey());
            model.setUserName(entry.getValue().get(0).getUserName());
            AttendanceModel attendanceModel = entry.getValue().stream().filter(item -> item.getSignInTime() != null).min(Comparator.comparing(AttendanceModel::getSignInTime)).orElse(null);
            if (attendanceModel != null) {
                model.setSignInTime(attendanceModel.getSignInTime());
            }
            AttendanceModel attendanceModel1 = entry.getValue().stream().filter(item -> item.getSignOutTime() != null).max(Comparator.comparing(AttendanceModel::getSignOutTime)).orElse(null);
            if (attendanceModel1 != null) {
                model.setSignOutTime(attendanceModel1.getSignOutTime());
            }
            list.add(model);
        }
        list.forEach(item -> {
            if (item.getSignInTime() == null || item.getSignOutTime() == null) {
                item.setAttendanceStatus("考勤异常");
            } else {
                if ((item.getSignOutTime().getTime() - item.getSignInTime().getTime()) / (1000 * 60 * 60) < attendanceHour) {
                    item.setAttendanceStatus("考勤时长不足" + attendanceHour + "小时");
                } else {
                    item.setAttendanceStatus("正常");
                }
            }
        });
        return list;
    }
}
