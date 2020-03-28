package com.ncut.face.erp.service.user.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FaceIdModel {
    byte[] faceFeature;
    String faceId;
    Integer score;
}
