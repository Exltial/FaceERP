package com.ncut.face.erp.service.login;

import com.ncut.face.erp.service.login.domain.UserFaceInfo;

import java.util.List;

public interface LoginService {
    List<UserFaceInfo> findUserFaceInfoList();
}
