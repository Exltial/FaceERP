package com.ncut.face.erp.bootstrap.controller.api;

import com.ncut.face.erp.service.common.Result;
import com.ncut.face.erp.service.common.utils.AssertUtil;
import com.ncut.face.erp.service.login.LoginService;
import com.ncut.face.erp.service.login.domain.UserRegistryVo;
import com.ncut.face.erp.service.user.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    LoginService loginService;
    @Resource
    UserService userService;

    @RequestMapping("/login")
    public void login(@RequestParam("file") String file) {
        //和数据库已有的人脸特征做比对
        Boolean result = loginService.faceLogin(file);
    }

    @RequestMapping("/registry")
    public Result registry(UserRegistryVo vo) {
        AssertUtil.notEmpty(vo.getTenantId(), "用户组名称不能为空");
        AssertUtil.notEmpty(vo.getUserName(), "用户姓名不能为空");
        AssertUtil.notEmpty(vo.getPicId(), "请先上传照片");
        userService.doRegistry(vo);
        return new Result<>(true);
    }
}
