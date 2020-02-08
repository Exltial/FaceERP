package com.ncut.face.erp.service.assets.repository;

import com.ncut.face.erp.service.assets.domain.AssetsAddVo;
import com.ncut.face.erp.service.assets.domain.AssetsModel;
import com.ncut.face.erp.service.assets.domain.AssetsModifyVo;
import com.ncut.face.erp.service.assets.domain.AssetsOperate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AssetsRepository {
    void addAssets(AssetsAddVo vo);

    List getAssetsList(AssetsOperate opt);

    void modifyAssets(AssetsModifyVo vo);

    AssetsModel getAssetsById(AssetsOperate opt);

    void deleteAssets(AssetsOperate opt);
}
