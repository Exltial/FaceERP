package com.ncut.face.erp.service.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String savePic(MultipartFile file, String path, String suffix, byte[] faceFeature);

    String getPathById(String picId);

    String saveFile(MultipartFile file, String s, String suffix);
}
