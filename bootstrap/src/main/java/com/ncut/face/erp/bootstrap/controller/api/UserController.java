package com.ncut.face.erp.bootstrap.controller.api;

import com.ncut.face.erp.service.common.Result;
import com.ncut.face.erp.service.common.utils.AssertUtil;
import com.ncut.face.erp.service.face.FaceService;
import com.ncut.face.erp.service.user.UserService;
import com.ncut.face.erp.service.user.domain.UserInfoModel;
import com.ncut.face.erp.service.user.domain.UserLoginVo;
import com.ncut.face.erp.service.user.domain.UserRegistryVo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;
    @Resource
    FaceService faceService;

    @RequestMapping("/getFaceId")
    public Result getFaceId(MultipartFile file) throws IOException {
        byte[] faceFeature = faceService.getFaceFeature(file.getBytes());
        return new Result<>(userService.getFaceIdByFeature(faceFeature));
    }

    @RequestMapping("/login")
    public Result login(@RequestBody UserLoginVo user) {
        AssertUtil.notEmpty(user.getFaceId(), "未获取到人脸信息");
        String token = userService.doLogin(user);
        return new Result<>(token);
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

    @RequestMapping("/userList")
    @RequiresPermissions(value = {"ADMIN", "USER"}, logical = Logical.OR)
    @RequiresRoles(value = {"ADMIN", "USER"}, logical = Logical.OR)
    public Result userList() {
        List<UserInfoModel> list = userService.getUserList();
        return new Result<>(list);
    }
}
