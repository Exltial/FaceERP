package com.ncut.face.erp.service.face;

import com.arcsoft.face.toolkit.ImageInfo;
import com.ncut.face.erp.service.face.domain.FaceUserInfo;
import com.ncut.face.erp.service.login.domain.UserFaceInfo;

import java.util.List;

public interface FaceEngineService {
    /**
     * 人脸特征
     *
     * @param imageInfo
     * @return
     */
    byte[] extractFaceFeature(ImageInfo imageInfo);

    /**
     * 人脸比对
     *
     * @param faceFeature
     * @return
     */
    List<FaceUserInfo> compareFaceFeature(byte[] faceFeature);

    void insertSelective(UserFaceInfo userFaceInfo);
}
