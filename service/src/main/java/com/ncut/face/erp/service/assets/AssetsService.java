package com.ncut.face.erp.service.assets;

import com.ncut.face.erp.service.assets.domain.AssetsAddVo;
import com.ncut.face.erp.service.assets.domain.AssetsModel;
import com.ncut.face.erp.service.assets.domain.AssetsModifyVo;
import com.ncut.face.erp.service.assets.domain.AssetsOperate;

import java.util.List;

public interface AssetsService {
    void addAssets(AssetsAddVo vo);

    List getAssetsList();

    void modifyAssets(AssetsModifyVo vo);

    AssetsModel getAssetsById(AssetsOperate opt);

    void deleteAssets(AssetsOperate opt);
}
