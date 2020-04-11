package com.ncut.face.erp.bootstrap.controller.api;

import com.ncut.face.erp.service.common.Result;
import com.ncut.face.erp.service.common.exception.BaseException;
import com.ncut.face.erp.service.common.utils.AssertUtil;
import com.ncut.face.erp.service.face.FaceService;
import com.ncut.face.erp.service.user.UserService;
import com.ncut.face.erp.service.user.domain.UserLoginVo;
import com.ncut.face.erp.service.user.domain.UserRegistryVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;
    @Resource
    FaceService faceService;

    @RequestMapping("/getFaceId")
    public Result getFaceId(MultipartFile file) throws IOException {
        if (file == null) {
            throw new BaseException("未检测到人脸");
        }
        byte[] faceFeature = faceService.getFaceFeature(file.getBytes());
        return new Result<>(userService.getFaceIdByFeature(faceFeature));
    }

    @RequestMapping("/login")
    public Result login(MultipartFile file) throws IOException {
        if (file == null) {
            throw new BaseException("未检测到人脸");
        }
        byte[] faceFeature = faceService.getFaceFeature(file.getBytes());
        String faceId = userService.getFaceIdByFeature(faceFeature);
        UserLoginVo user = new UserLoginVo();
        user.setFaceId(faceId);
        userService.doLogin(user);
        return new Result<>(true);
    }

    @RequestMapping("/registry")
    public Result registry(@RequestBody UserRegistryVo vo) {
        AssertUtil.notEmpty(vo.getTenantId(), "用户组名称不能为空");
        AssertUtil.notEmpty(vo.getUserName(), "用户姓名不能为空");
        AssertUtil.notEmpty(vo.getFaceId(), "请先上传照片");
        AssertUtil.notEmpty(vo.getRole(), "用户角色不能为空");
        userService.doRegistry(vo);
        return new Result<>(true);
    }
}
