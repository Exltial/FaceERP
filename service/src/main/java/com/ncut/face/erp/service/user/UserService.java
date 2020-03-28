package com.ncut.face.erp.service.user;

import com.ncut.face.erp.service.user.domain.UserInfoModel;
import com.ncut.face.erp.service.user.domain.UserLoginVo;
import com.ncut.face.erp.service.user.domain.UserRegistryVo;

import java.util.List;

public interface UserService {
    List getAllTenantId();

    void doRegistry(UserRegistryVo vo);

    UserInfoModel getInfoByPin(String pin);

    String getFaceIdByFeature(byte[] feature);

    UserInfoModel getInfoByFaceId(String faceId);

    void doLogin(UserLoginVo user);

    String getFaceId(byte[] faceFeature);
}
