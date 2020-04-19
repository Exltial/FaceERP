package com.ncut.face.erp.service.file.impl;

import com.ncut.face.erp.service.common.exception.BaseException;
import com.ncut.face.erp.service.common.utils.UUIDUtil;
import com.ncut.face.erp.service.file.FileService;
import com.ncut.face.erp.service.file.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Resource
    FileRepository fileRepository;

    @Override
    @Transactional
    public String savePic(MultipartFile file, String path, String suffix, byte[] faceFeature) {
        String uniPicId = UUIDUtil.getShortUUID();
        String fileName = uniPicId + "." + suffix;
        //创建文件路径
        File dest = new File(path + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            //假如文件不存在即重新创建新的文件已防止异常发生
            dest.getParentFile().mkdirs();
        }
        try {
            fileRepository.insertPic(uniPicId, path + fileName, faceFeature);
            file.transferTo(dest);
            return uniPicId;
        } catch (IOException e) {
            log.error("file upload failed==>e:", e);
            throw new BaseException("图片上传异常,请重试");
        }
    }

    @Override
    public String getPathById(String picId) {
        return fileRepository.getPathById(picId);
    }

    @Override
    public String saveFile(MultipartFile file, String path, String suffix) {
        String uniPicId = UUIDUtil.getShortUUID();
        String fileName = uniPicId + "." + suffix;
        //创建文件路径
        File dest = new File(path + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            //假如文件不存在即重新创建新的文件已防止异常发生
            dest.getParentFile().mkdirs();
        }
        try {
            fileRepository.insertFile(uniPicId, path + fileName);
            file.transferTo(dest);
            return uniPicId;
        } catch (IOException e) {
            log.error("file upload failed==>e:", e);
            throw new BaseException("图片上传异常,请重试");
        }
    }
}
