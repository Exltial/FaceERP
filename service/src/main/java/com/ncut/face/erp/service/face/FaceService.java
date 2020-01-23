package com.ncut.face.erp.service.face;

import com.ncut.face.erp.service.face.domain.FaceUserInfo;


public interface FaceService {
    FaceUserInfo getFaceInfo(String pic);

    void saveFacePic(String pic, String path);
}
