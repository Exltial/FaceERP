package com.ncut.face.erp.service.face.impl;

import com.arcsoft.face.EngineConfiguration;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class FaceEngineFactory extends BasePooledObjectFactory<FaceEngine> {

    private String appId;
    private String sdkKey;
    private String sdkLibPath;
    private EngineConfiguration engineConfiguration;
    private Integer detectFaceMaxNum = 10;
    private Integer detectFaceScaleVal = 16;
    private DetectMode detectMode = DetectMode.ASF_DETECT_MODE_IMAGE;
    private DetectOrient detectFaceOrientPriority = DetectOrient.ASF_OP_0_ONLY;


    public FaceEngineFactory(String sdkLibPath, String appId, String sdkKey, EngineConfiguration engineConfiguration) {
        this.sdkLibPath = sdkLibPath;
        this.appId = appId;
        this.sdkKey = sdkKey;
        this.engineConfiguration = engineConfiguration;

    }

    @Override
    public FaceEngine create() {
        FaceEngine faceEngine = new FaceEngine(sdkLibPath);
        faceEngine.activeOnline(appId, sdkKey);
        faceEngine.init(engineConfiguration);
        return faceEngine;
    }

    @Override
    public PooledObject<FaceEngine> wrap(FaceEngine faceEngine) {
        return new DefaultPooledObject<>(faceEngine);
    }

    @Override
    public void destroyObject(PooledObject<FaceEngine> p) throws Exception {
        FaceEngine faceEngine = p.getObject();
        faceEngine.unInit();
        super.destroyObject(p);
    }
}

