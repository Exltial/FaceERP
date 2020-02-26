package com.ncut.face.erp.service.user.repository;

import com.ncut.face.erp.service.login.domain.UserRegistryModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRepository {
    List getAllTenantId();

    void insertUser(UserRegistryModel userRegistryModel);
}
