package com.ncut.face.erp.bootstrap;

import com.ncut.face.erp.service.file.domain.FileModel;
import com.ncut.face.erp.service.file.repository.FileRepository;
import org.junit.Test;

import javax.annotation.Resource;

public class RepositoryTest extends BaseTest {
    @Resource
    FileRepository fileRepository;

    @Test
    public void getFaceFeatureById() {
        FileModel faceFeatureById = fileRepository.getFaceFeatureById("2dFCrWMD");
    }

}
