package com.ncut.face.erp.bootstrap.controller.api;

import com.ncut.face.erp.service.login.LoginService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    LoginService loginService;

    @RequestMapping("/login")
    public void login(@RequestParam("file") String file) {
        //和数据库已有的人脸特征做比对
        Boolean result = loginService.faceLogin(file);
    }
}
