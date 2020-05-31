package com.ncut.face.erp.bootstrap.controller.api;

import com.ncut.face.erp.service.common.Result;
import com.ncut.face.erp.service.common.exception.BaseException;
import com.ncut.face.erp.service.common.utils.AssertUtil;
import com.ncut.face.erp.service.face.FaceService;
import com.ncut.face.erp.service.file.FileService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    FileService fileService;
    @Value("${file.path}")
    private String rootPath;
    @Value("${file.path.registry}")
    private String registryPath;
    @Resource
    FaceService faceService;

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
        byte[] faceFeature;
        try {
            faceFeature = faceService.getFaceFeature(file.getBytes());
        } catch (IOException e) {
            throw new BaseException("上传图片有误,请检查后上传");
        }
        String faceId = fileService.savePic(file, rootPath + registryPath, suffix, faceFeature);
        return new Result<>(faceId);
    }

    @RequestMapping("/uploadFile")
    @RequiresPermissions(value = {"ADMIN", "USER"}, logical = Logical.OR)
    @RequiresRoles(value = {"ADMIN", "USER"}, logical = Logical.OR)
    public Result uploadFile(MultipartFile file) {
        AssertUtil.notNull(file, "请选择上传的文件");
        // 获取文件名后缀名
        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        String suffix = fileName.substring(index + 1);
        String fileId = fileService.saveFile(file, rootPath + registryPath, suffix);
        return new Result<>(fileId);
    }

    @RequestMapping("/getPath/{id}")
    @RequiresPermissions(value = {"ADMIN", "USER"}, logical = Logical.OR)
    @RequiresRoles(value = {"ADMIN", "USER"}, logical = Logical.OR)
    public Result getPathByFileId(@PathVariable("id") String id) {
        String pathById = fileService.getPathById(id);
        String[] split = pathById.split("/");
        String path = split[split.length - 1];
        return new Result<>(path);
    }

    @RequestMapping("/downLoadFile")
    @RequiresPermissions(value = {"ADMIN", "USER"}, logical = Logical.OR)
    @RequiresRoles(value = {"ADMIN", "USER"}, logical = Logical.OR)
    public void downLoadFile(String fileId, HttpServletResponse response) throws IOException {
        String path = fileService.getPathById(fileId);
        File file = new File(path);
        if (StringUtils.isEmpty(path) || !file.exists()) {
            throw new BaseException("未查找到文件,请先上传");
        }
        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Disposition", "attachment;fileName=" + file.getName());// 设置文件名
        byte[] buffer = new byte[1024];
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        OutputStream os = response.getOutputStream();
        int i = bis.read(buffer);
        while (i != -1) {
            os.write(buffer, 0, i);
            i = bis.read(buffer);
        }
        bis.close();
    }
}
