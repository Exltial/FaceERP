package com.ncut.face.erp.service.user.repository;

import com.ncut.face.erp.service.user.domain.FaceIdModel;
import com.ncut.face.erp.service.user.domain.UserInfoModel;
import com.ncut.face.erp.service.user.domain.UserRegistryModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRepository {
    List getAllTenantId();

    void insertUser(UserRegistryModel userRegistryModel);

    UserInfoModel getInfoByPin(String pin);

    List<FaceIdModel> getAllFeature(String tenantId);

    UserInfoModel getInfoByFaceId(String faceId);
}
