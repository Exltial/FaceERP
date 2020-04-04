package com.ncut.face.erp.service.face.impl;

import cn.hutool.core.codec.Base64;
import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.toolkit.ImageFactory;
import com.arcsoft.face.toolkit.ImageInfo;
import com.ncut.face.erp.service.common.exception.BaseException;
import com.ncut.face.erp.service.common.utils.AssertUtil;
import com.ncut.face.erp.service.face.FaceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import sun.misc.BASE64Encoder;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FaceServiceImpl implements FaceService {
    @Value("${config.arcface-sdk.app-id}")
    public String appId;

    @Value("${config.arcface-sdk.sdk-key}")
    public String sdkKey;

    @Value("${config.arcface-sdk.thread-pool-size}")
    public Integer threadPoolSize;

    @Value("${face.compare.rate}")
    private Integer passRate;
    private GenericObjectPool<FaceEngine> faceEngineObjectPool;

    @PostConstruct
    public void init() throws FileNotFoundException {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(threadPoolSize);
        poolConfig.setMaxTotal(threadPoolSize);
        poolConfig.setMinIdle(threadPoolSize);
        poolConfig.setLifo(false);

        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_0_ONLY);

        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);
        String sdkLibPath = ResourceUtils.getURL("classpath:lib/").getPath();
        faceEngineObjectPool = new GenericObjectPool(new FaceEngineFactory(sdkLibPath, appId, sdkKey, engineConfiguration), poolConfig);//底层库算法对象池
    }

    @Override
    public byte[] getFaceFeature(File file) {
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new BaseException("图片路径错误");
        }
        return this.getFaceFeature(bytes);
    }

    @Override
    public byte[] getFaceFeature(byte[] pic) {
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(pic);
        //将图片转换成sdk需求对象编码数组
        byte[] decode = Base64.decode(base64Process(encode));
        BufferedImage bufImage;
        try {
            bufImage = ImageIO.read(new ByteArrayInputStream(decode));
        } catch (IOException ze) {
            throw new BaseException("IO流异常,请重试");
        }
        ImageInfo imageInfo = ImageFactory.bufferedImage2ImageInfo(bufImage);
        //获取引擎对象
        FaceEngine faceEngine;
        try {
            faceEngine = faceEngineObjectPool.borrowObject();
        } catch (Exception e) {
            log.error("extractFaceFeature throws an exception==>e:", e);
            throw new BaseException(e.getMessage());
        }
        //人脸检测得到人脸列表
        List<FaceInfo> faceInfoList = new ArrayList<>();
        //人脸检测
        faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        AssertUtil.notEmpty(faceInfoList, "未识别到人脸");
        FaceFeature faceFeature = new FaceFeature();
        //提取人脸特征
        faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        //释放引擎对象
        faceEngineObjectPool.returnObject(faceEngine);
        return faceFeature.getFeatureData();
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

    @Override
    public Integer compareFace(byte[] faceFeature, byte[] targetFeature) {
        FaceFeature targetFaceFeature = new FaceFeature();
        targetFaceFeature.setFeatureData(targetFeature);

        FaceEngine faceEngine;
        try {
            faceEngine = faceEngineObjectPool.borrowObject();
        } catch (Exception e) {
            throw new BaseException("人脸识别引擎异常");
        }
        FaceFeature sourceFaceFeature = new FaceFeature();
        sourceFaceFeature.setFeatureData(faceFeature);
        FaceSimilar faceSimilar = new FaceSimilar();
        faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
        faceEngineObjectPool.returnObject(faceEngine);
        //获取相似值
        return BigDecimal.valueOf(faceSimilar.getScore()).multiply(BigDecimal.valueOf(100f)).intValue();
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
