package com.ncut.face.erp.service.user;

import com.ncut.face.erp.service.login.domain.UserRegistryVo;

import java.util.List;

public interface UserService {
    List getAllTenantId();

    void doRegistry(UserRegistryVo vo);
}
