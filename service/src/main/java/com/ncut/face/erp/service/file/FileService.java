package com.ncut.face.erp.service.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String save(MultipartFile file, String path, String suffix, byte[] faceFeature);

    String getPathById(String picId);
}
