package com.ncut.face.erp.service.face.domain;

import lombok.Data;

@Data
public class FaceUserInfo {
    private String faceId;
    private String name;
    private Integer similarValue;
    private byte[] faceFeature;
}
