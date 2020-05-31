package com.ncut.face.erp.service.user;

import com.ncut.face.erp.service.user.domain.UserInfoModel;
import com.ncut.face.erp.service.user.domain.UserLoginVo;
import com.ncut.face.erp.service.user.domain.UserRegistryVo;

import java.util.List;

public interface UserService {
    void doRegistry(UserRegistryVo vo);

    String getFaceIdByFeature(byte[] feature);

    UserInfoModel getInfoByFaceId(String faceId);

    String doLogin(UserLoginVo user);

    List<UserInfoModel> getUserList();
}
