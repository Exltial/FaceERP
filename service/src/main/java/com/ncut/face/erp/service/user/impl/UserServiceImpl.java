package com.ncut.face.erp.service.user.impl;

import com.ncut.face.erp.service.common.enums.RoleEnum;
import com.ncut.face.erp.service.common.exception.BaseException;
import com.ncut.face.erp.service.common.utils.AssertUtil;
import com.ncut.face.erp.service.common.utils.EncodeUtil;
import com.ncut.face.erp.service.face.FaceService;
import com.ncut.face.erp.service.file.FileService;
import com.ncut.face.erp.service.login.domain.UserRegistryModel;
import com.ncut.face.erp.service.login.domain.UserRegistryVo;
import com.ncut.face.erp.service.user.UserService;
import com.ncut.face.erp.service.user.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserRepository userRepository;
    @Resource
    FileService fileService;
    @Resource
    FaceService faceService;
    @Value("${private.key}")
    private String privateKey;

    @Override
    public List getAllTenantId() {
        return userRepository.getAllTenantId();
    }

    @Override
    public void doRegistry(UserRegistryVo vo) {
        List tenantList = userRepository.getAllTenantId();
        if (tenantList.contains(vo.getTenantId())) {
            throw new BaseException("用户组已存在,请重新输入");
        }
        String path = fileService.getPathById(vo.getPicId());
        AssertUtil.notEmpty(path, "图片文件未找到,请重新上传");
        String password;
        try {
            byte[] bytes = EncodeUtil.decryptByPrivateKey(vo.getPassword().replace(" ", "+"), privateKey);
            password = new String(bytes);
        } catch (Exception e) {
            throw new BaseException("密码包含非法字符");
        }
        UserRegistryModel userRegistryModel = new UserRegistryModel();
        BeanUtils.copyProperties(vo, userRegistryModel);
        userRegistryModel.setPassword(password);
        userRegistryModel.setUserRole(RoleEnum.ADMIN.getRoleCode());
        userRepository.insertUser(userRegistryModel);
    }
}
