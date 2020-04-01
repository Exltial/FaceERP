package com.ncut.face.erp.service.attendance;

import com.ncut.face.erp.service.attendance.domain.AttendanceModel;
import com.ncut.face.erp.service.user.domain.UserInfoModel;

import java.util.List;

public interface AttendanceService {
    void signIn(UserInfoModel user);

    void signOut(UserInfoModel user);

    List<AttendanceModel> getAttendanceList(UserInfoModel user, String date);
}
