package com.ncut.face.erp.service.user.impl;

import com.ncut.face.erp.service.common.enums.RoleEnum;
import com.ncut.face.erp.service.common.exception.BaseException;
import com.ncut.face.erp.service.common.utils.AssertUtil;
import com.ncut.face.erp.service.face.FaceService;
import com.ncut.face.erp.service.file.FileService;
import com.ncut.face.erp.service.file.domain.FileModel;
import com.ncut.face.erp.service.file.repository.FileRepository;
import com.ncut.face.erp.service.user.UserService;
import com.ncut.face.erp.service.user.domain.*;
import com.ncut.face.erp.service.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    UserRepository userRepository;
    @Resource
    FileRepository fileRepository;
    @Resource
    FileService fileService;
    @Resource
    FaceService faceService;

    @Value("${private.key}")
    private String privateKey;
    @Value("${face.compare.rate}")
    private Integer rate;

    @Override
    public void doRegistry(UserRegistryVo vo) {
        List tenantList = userRepository.getAllTenantIdWithoutAdmin();
        if (vo.getRole().equals(RoleEnum.ADMIN.getRoleCode()) && tenantList.contains(vo.getTenantId())) {
            throw new BaseException("用户组已存在,请注册普通用户权限");
        }
        if (!RoleEnum.isMatched(vo.getRole())) {
            throw new BaseException("用户角色枚举不存在");
        }
        String path = fileService.getPathById(vo.getFaceId());
        AssertUtil.notEmpty(path, "图片文件未找到,请重新上传");
        FileModel faceFeature = fileRepository.getFaceFeatureById(vo.getFaceId());

        List<FaceIdModel> list = userRepository.getAllFeature(null);
        if (!CollectionUtils.isEmpty(list)) {
            List<FaceIdModel> mathList = list.parallelStream().filter(item -> {
                Integer score = faceService.compareFace(faceFeature.getFaceFeature(), item.getFaceFeature());
                item.setScore(score);
                return score > rate;
            }).sorted(Comparator.comparing(FaceIdModel::getScore).reversed()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(mathList)) {
                throw new BaseException("已存在相同用户,请登录");
            }
        }
        String password = "123456";
//        try {
//            password = EncodeUtil.decryptByPrivateKey(vo.getPassword(), privateKey);
//        } catch (Exception e) {
//            throw new BaseException("密码包含非法字符");
//        }
        UserRegistryModel userRegistryModel = new UserRegistryModel();
        BeanUtils.copyProperties(vo, userRegistryModel);
        userRegistryModel.setPassword(password);
        userRegistryModel.setFaceFeature(faceFeature.getFaceFeature());
        userRegistryModel.setUserRole(vo.getRole());
        userRepository.insertUser(userRegistryModel);
    }

    @Override
    public String getFaceIdByFeature(byte[] feature) {
        List<FaceIdModel> faceList = userRepository.getAllFeature(null);
        List<FaceIdModel> list = faceList.parallelStream().filter(item -> {
            Integer score = faceService.compareFace(feature, item.getFaceFeature());
            item.setScore(score);
            return score > rate;
        }).sorted(Comparator.comparing(FaceIdModel::getScore).reversed()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(list)) {
            throw new BaseException("未查询到人脸，请重试");
        }
        return list.get(0).getFaceId();
    }

    @Override
    public UserInfoModel getInfoByFaceId(String faceId) {
        return userRepository.getInfoByFaceId(faceId);
    }

    @Override
    public String doLogin(UserLoginVo user) {
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        String password = "123456";
//        try {
//            password = EncodeUtil.decryptByPrivateKey(user.getPassword(), privateKey);
//        } catch (Exception e) {
//            log.error("decode password failed==>e:", e);
//            throw new BaseException("密码解析错误");
//        }
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getFaceId(), password);
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
            return String.valueOf(subject.getSession().getId());
        } catch (AuthenticationException e) {
            throw new BaseException("用户未注册");
        } catch (AuthorizationException e) {
            throw new BaseException("没有权限");
        }
    }

    @Override
    public List<UserInfoModel> getUserList() {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        List<UserInfoModel> userList = userRepository.getUserList(user.getTenantId());
        userList.forEach(item -> {
            String pathById = fileRepository.getPathById(item.getFaceId());
            String[] split = pathById.split("/");
            String imgUrl = "/static/" + split[split.length - 1];
            item.setImgUrl(imgUrl);
        });
        return userList;
    }
}
