package com.ncut.face.erp.service.assets.impl;

import com.ncut.face.erp.service.assets.AssetsService;
import com.ncut.face.erp.service.assets.domain.AssetsAddVo;
import com.ncut.face.erp.service.assets.domain.AssetsModel;
import com.ncut.face.erp.service.assets.domain.AssetsModifyVo;
import com.ncut.face.erp.service.assets.domain.AssetsOperate;
import com.ncut.face.erp.service.assets.repository.AssetsRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AssetsServiceImpl implements AssetsService {
    @Resource
    AssetsRepository assetsRepository;

    @Override
    public void addAssets(AssetsAddVo vo) {
        assetsRepository.addAssets(vo);
    }

    @Override
    public List getAssetsList(AssetsOperate opt) {
        return assetsRepository.getAssetsList(opt);
    }

    @Override
    public void modifyAssets(AssetsModifyVo vo) {
        assetsRepository.modifyAssets(vo);
    }

    @Override
    public AssetsModel getAssetsById(AssetsOperate opt) {
        return assetsRepository.getAssetsById(opt);
    }

    @Override
    public void deleteAssets(AssetsOperate opt) {
        assetsRepository.deleteAssets(opt);
    }
}
