package com.ncut.face.erp.service.attendance.repository;

import com.ncut.face.erp.service.attendance.domain.AttendanceModel;
import com.ncut.face.erp.service.attendance.domain.AttendanceQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface AttendanceRepository {
    void signIn(AttendanceModel attendanceModel);

    void signOut(AttendanceModel attendanceModel);

    List<AttendanceModel> getAttendanceList(AttendanceQuery query);

    Date getSignInTime(AttendanceQuery user);

    Date getSignOutTime(AttendanceQuery query);

    void deleteSignOutRecord(AttendanceQuery query);
}
