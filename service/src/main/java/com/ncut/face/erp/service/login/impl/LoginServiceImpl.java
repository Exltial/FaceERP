package com.ncut.face.erp.service.login.impl;

import com.ncut.face.erp.service.login.LoginService;
import com.ncut.face.erp.service.login.domain.UserFaceInfo;
import com.ncut.face.erp.service.login.repository.LoginRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    LoginRepository loginRepository;

    @Override
    public List<UserFaceInfo> findUserFaceInfoList() {
        return loginRepository.findUserFaceInfoList();
    }

    @Override
    public Boolean faceLogin(String file) {
        return null;
    }
}
