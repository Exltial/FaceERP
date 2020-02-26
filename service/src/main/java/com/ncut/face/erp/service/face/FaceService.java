package com.ncut.face.erp.service.face;

import java.io.File;


public interface FaceService {
    byte[] getFaceFeature(File file);

    byte[] getFaceFeature(byte[] pic);

    void saveFacePic(String pic, String path);

    Integer compareFace(byte[] faceFeature, byte[] targetFeature);
}
