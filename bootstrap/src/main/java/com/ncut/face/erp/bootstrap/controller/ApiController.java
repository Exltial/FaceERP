package com.ncut.face.erp.bootstrap.controller;

import com.ncut.face.erp.service.face.FaceService;
import com.ncut.face.erp.service.login.LoginService;
import com.ncut.face.erp.service.login.domain.UserFaceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

@RestController
@Slf4j
public class ApiController {
    @Resource
    LoginService loginService;
    @Resource
    FaceService faceService;

    @RequestMapping("/userInfo")
    public List<UserFaceInfo> getUserInfo() {
        return loginService.findUserFaceInfoList();
    }


    @RequestMapping("/detectFaces")
    private String base64Process(String base64Str) {
        if (!StringUtils.isEmpty(base64Str)) {
            String photoBase64 = base64Str.substring(0, 30).toLowerCase();
            int indexOf = photoBase64.indexOf("base64,");
            if (indexOf > 0) {
                base64Str = base64Str.substring(indexOf + 7);
            }

            return base64Str;
        } else {
            return "";
        }
    }

    private boolean generateImage(byte[] b) {  //对字节数组字符串进行Base64解码并生成图片
        try {
            //Base64解码
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            String imgFilePath = "D:\\new.jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
