package com.ncut.face.erp.bootstrap.controller;

import cn.hutool.core.util.RandomUtil;
import com.arcsoft.face.toolkit.ImageFactory;
import com.arcsoft.face.toolkit.ImageInfo;
import com.ncut.face.erp.service.common.Result;
import com.ncut.face.erp.service.face.FaceEngineService;
import com.ncut.face.erp.service.face.FaceService;
import com.ncut.face.erp.service.face.domain.FaceSearchResDto;
import com.ncut.face.erp.service.face.domain.FaceUserInfo;
import com.ncut.face.erp.service.login.LoginService;
import com.ncut.face.erp.service.login.domain.UserFaceInfo;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
@Slf4j
public class ApiController {
    @Resource
    LoginService loginService;
    @Resource
    FaceEngineService faceEngineService;
    @Resource
    FaceService faceService;

    @RequestMapping("/userInfo")
    public List<UserFaceInfo> getUserInfo() {
        return loginService.findUserFaceInfoList();
    }

    @RequestMapping("/faceAdd")
    public Result<Object> faceAdd(@RequestParam("file") String file, @RequestParam("groupId") Integer groupId, @RequestParam("name") String name) {
        try {
            byte[] decode = Base64.decode(base64Process(file));
            ImageInfo imageInfo = ImageFactory.getRGBData(decode);

            //人脸特征获取
            byte[] bytes = faceEngineService.extractFaceFeature(imageInfo);

            UserFaceInfo userFaceInfo = new UserFaceInfo();
            userFaceInfo.setName(name);
            userFaceInfo.setGroupId(groupId);
            userFaceInfo.setFaceFeature(bytes);
            userFaceInfo.setFaceId(RandomUtil.randomString(10));

            //人脸特征插入到数据库
            faceEngineService.insertSelective(userFaceInfo);

            log.info("faceAdd:" + name);
        } catch (Exception e) {
            log.error("", e);
        }
        return new Result<>("success");
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

    @RequestMapping("/face")
    public String face(MultipartFile file) throws IOException {
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(file.getBytes());
        faceService.getFaceInfo(encode);
        return encode;
    }
    @RequestMapping("/faceSearch")
    public Result<FaceSearchResDto> faceSearch(String file, Integer groupId) throws Exception {
        FaceUserInfo faceInfo = faceService.getFaceInfo(file);
        byte[] decode = cn.hutool.core.codec.Base64.decode(base64Process(file));
        generateImage(decode);
        FaceSearchResDto faceSearchResDto = new FaceSearchResDto();
        BeanUtils.copyProperties(faceInfo, faceSearchResDto);
        return new Result<>(faceSearchResDto);
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
