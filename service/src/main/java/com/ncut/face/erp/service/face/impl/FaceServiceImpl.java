package com.ncut.face.erp.service.face.impl;

import cn.hutool.core.codec.Base64;
import com.arcsoft.face.toolkit.ImageFactory;
import com.arcsoft.face.toolkit.ImageInfo;
import com.ncut.face.erp.service.common.exception.BaseException;
import com.ncut.face.erp.service.common.utils.AssertUtil;
import com.ncut.face.erp.service.face.FaceEngineService;
import com.ncut.face.erp.service.face.FaceService;
import com.ncut.face.erp.service.face.domain.FaceUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class FaceServiceImpl implements FaceService {
    @Resource
    FaceEngineService faceEngineService;

    @Override
    public FaceUserInfo getFaceInfo(String pic) {
        //将图片转换成sdk需求对象编码数组
        byte[] decode = Base64.decode(base64Process(pic));
        BufferedImage bufImage;
        try {
            bufImage = ImageIO.read(new ByteArrayInputStream(decode));
        } catch (IOException ze) {
            throw new BaseException("IO流异常,请重试");
        }
        ImageInfo imageInfo = ImageFactory.bufferedImage2ImageInfo(bufImage);
        //人脸特征获取
        byte[] faceFeature = faceEngineService.extractFaceFeature(imageInfo);
        //人脸比对，获取比对结果
        List<FaceUserInfo> userFaceInfoList = faceEngineService.compareFaceFeature(faceFeature);
        if (CollectionUtils.isEmpty(userFaceInfoList)) {
            return null;
        }
        return userFaceInfoList.get(0);
    }

    @Override
    public void saveFacePic(String pic, String path) {
        byte[] decode = Base64.decode(base64Process(pic));
        //Base64解码
        for (int i = 0; i < decode.length; ++i) {
            if (decode[i] < 0) {//调整异常数据
                decode[i] += 256;
            }
        }
        //生成jpeg图片
        OutputStream out;
        try {
            out = new FileOutputStream(path);
            out.write(decode);
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new BaseException("IO流写入失败，请重试");
        }
    }

    private String base64Process(String base64Str) {
        AssertUtil.notEmpty(base64Str, "图像编码为空");
        String photoBase64 = base64Str.substring(0, 30).toLowerCase();
        int indexOf = photoBase64.indexOf("base64,");
        if (indexOf > 0) {
            base64Str = base64Str.substring(indexOf + 7);
        }
        return base64Str;
    }
}
