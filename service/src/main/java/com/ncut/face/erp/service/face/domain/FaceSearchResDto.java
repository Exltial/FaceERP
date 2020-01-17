package com.ncut.face.erp.service.face.domain;

import lombok.Data;

@Data
public class FaceSearchResDto {
    private String faceId;
    private String name;
    private Integer similarValue;
    private Integer age;
    private String gender;
    private String image;
}
