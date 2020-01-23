package com.ncut.face.erp.service.face.impl;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.toolkit.ImageInfo;
import com.ncut.face.erp.service.common.exception.BaseException;
import com.ncut.face.erp.service.common.utils.AssertUtil;
import com.ncut.face.erp.service.face.FaceEngineService;
import com.ncut.face.erp.service.face.domain.FaceUserInfo;
import com.ncut.face.erp.service.face.repository.FaceRepository;
import com.ncut.face.erp.service.login.domain.UserFaceInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FaceEngineServiceImpl implements FaceEngineService {
    @Value("${config.arcface-sdk.app-id}")
    public String appId;

    @Value("${config.arcface-sdk.sdk-key}")
    public String sdkKey;

    @Value("${config.arcface-sdk.thread-pool-size}")
    public Integer threadPoolSize;
    @Resource
    FaceRepository faceRepository;

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
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);
        String sdkLibPath = ResourceUtils.getURL("classpath:lib/").getPath();
        faceEngineObjectPool = new GenericObjectPool(new FaceEngineFactory(sdkLibPath, appId, sdkKey, engineConfiguration), poolConfig);//底层库算法对象池
    }

    @Override
    public byte[] extractFaceFeature(ImageInfo imageInfo) {
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
    public List<FaceUserInfo> compareFaceFeature(byte[] faceFeature) {
        FaceFeature targetFaceFeature = new FaceFeature();
        targetFaceFeature.setFeatureData(faceFeature);
        //从数据库中取出人脸库
        List<FaceUserInfo> faceInfoList = faceRepository.getUserFaceInfoByGroupId();
        //识别到的人脸列表
        //从大到小排序
        return faceInfoList.parallelStream().map(item -> this.executeCompare(item, targetFaceFeature)).filter(Objects::nonNull).sorted((h1, h2) -> h2.getSimilarValue().compareTo(h1.getSimilarValue())).collect(Collectors.toList());
    }

    private FaceUserInfo executeCompare(FaceUserInfo faceUserInfo, FaceFeature targetFaceFeature) {
        FaceEngine faceEngine;
        try {
            faceEngine = faceEngineObjectPool.borrowObject();
        } catch (Exception e) {
            throw new BaseException("人脸识别引擎异常");
        }
        FaceFeature sourceFaceFeature = new FaceFeature();
        sourceFaceFeature.setFeatureData(faceUserInfo.getFaceFeature());
        FaceSimilar faceSimilar = new FaceSimilar();
        faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
        //获取相似值
        Integer similarValue = BigDecimal.valueOf(faceSimilar.getScore()).multiply(BigDecimal.valueOf(100f)).intValue();
        FaceUserInfo info = new FaceUserInfo();
        //相似值大于配置预期，加入到识别到人脸的列表
        if (similarValue < passRate) {
            return null;
        }
        info.setName(faceUserInfo.getName());
        info.setFaceId(faceUserInfo.getFaceId());
        info.setSimilarValue(similarValue);
        faceEngineObjectPool.returnObject(faceEngine);
        return info;
    }

    @Override
    public void insertSelective(UserFaceInfo userFaceInfo) {
        faceRepository.insertSelective(userFaceInfo);
    }
}
