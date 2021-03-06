package com.ncut.face.erp.service.file.repository;

import com.ncut.face.erp.service.file.domain.FileModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileRepository {
    void insertPic(String id, String path, byte[] faceFeature);

    String getPathById(String picId);

    FileModel getFaceFeatureById(String faceId);

    void insertFile(String fileId, String path);
}
