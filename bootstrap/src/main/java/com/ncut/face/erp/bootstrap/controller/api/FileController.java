package com.ncut.face.erp.bootstrap.controller.api;

import com.ncut.face.erp.service.common.Result;
import com.ncut.face.erp.service.common.exception.BaseException;
import com.ncut.face.erp.service.common.utils.AssertUtil;
import com.ncut.face.erp.service.file.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
public class FileController {
    @Resource
    FileService fileService;
    @Value("${file.path}")
    private String rootPath;
    @Value("${file.path.registry}")
    private String registryPath;

    @RequestMapping("/uploadPic")
    public Result uploadPic(MultipartFile file) {
        AssertUtil.notNull(file, "请选择上传的文件");
        // 获取文件名后缀名
        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        String suffix = fileName.substring(index + 1);
        if (index == -1 || (!suffix.equals("jpg") && !suffix.equals("png"))) {
            throw new BaseException("请上传jpg或png图片");
        }
        String fileId = fileService.save(file, rootPath + registryPath, suffix);
        return new Result<>(fileId);
    }
}