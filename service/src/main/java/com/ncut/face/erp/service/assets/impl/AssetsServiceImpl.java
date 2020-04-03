package com.ncut.face.erp.service.assets.impl;

import com.ncut.face.erp.service.assets.AssetsService;
import com.ncut.face.erp.service.assets.domain.AssetsAddVo;
import com.ncut.face.erp.service.assets.domain.AssetsModel;
import com.ncut.face.erp.service.assets.domain.AssetsModifyVo;
import com.ncut.face.erp.service.assets.domain.AssetsOperate;
import com.ncut.face.erp.service.assets.repository.AssetsRepository;
import com.ncut.face.erp.service.user.domain.UserInfoModel;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AssetsServiceImpl implements AssetsService {
    @Resource
    AssetsRepository assetsRepository;

    @Override
    public void addAssets(AssetsAddVo vo) {
        AssetsModel model = new AssetsModel();
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        model.setTenantId(user.getTenantId());
        model.setAssetsName(vo.getAssetsName());
        model.setAssetsId(vo.getAssetsId());
        model.setAssetsOwner(vo.getAssetsOwner());
        model.setAssetsPosition(vo.getAssetsPosition());
        model.setCreator(user.getUserName());

        assetsRepository.addAssets(model);
    }

    @Override
    public List getAssetsList() {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        return assetsRepository.getAssetsList(user.getTenantId());
    }

    @Override
    public void modifyAssets(AssetsModifyVo vo) {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        AssetsModel model = new AssetsModel();
        model.setId(vo.getId());
        model.setTenantId(user.getTenantId());
        model.setAssetsName(vo.getAssetsName());
        model.setAssetsId(vo.getAssetsId());
        model.setAssetsOwner(vo.getAssetsOwner());
        model.setAssetsPosition(vo.getAssetsPosition());
        model.setModifier(user.getUserName());
        assetsRepository.modifyAssets(model);
    }

    @Override
    public AssetsModel getAssetsById(AssetsOperate opt) {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        return assetsRepository.getAssetsById(user.getTenantId(), opt.getId());
    }

    @Override
    public void deleteAssets(AssetsOperate opt) {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        assetsRepository.deleteAssets(user.getTenantId(), opt.getId());
    }
}
