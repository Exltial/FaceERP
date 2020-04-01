package com.ncut.face.erp.service.file.domain;

import lombok.Data;

@Data
public class FileModel {
    byte[] faceFeature;
    String fileId;
    String path;
}
