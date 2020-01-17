package com.ncut.face.erp.service.face.repository;

import com.ncut.face.erp.service.face.domain.FaceUserInfo;
import com.ncut.face.erp.service.login.domain.UserFaceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FaceRepository {
    List<FaceUserInfo> getUserFaceInfoByGroupId(Integer groupId);

    void insertSelective(UserFaceInfo userFaceInfo);
}
