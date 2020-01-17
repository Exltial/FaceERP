package com.ncut.face.erp.service.login.repository;

import com.ncut.face.erp.service.login.domain.UserFaceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginRepository {
    List<UserFaceInfo> findUserFaceInfoList();
}
