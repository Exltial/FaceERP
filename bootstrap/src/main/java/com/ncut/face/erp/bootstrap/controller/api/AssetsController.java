package com.ncut.face.erp.bootstrap.controller.api;

import com.ncut.face.erp.service.assets.AssetsService;
import com.ncut.face.erp.service.assets.domain.AssetsAddVo;
import com.ncut.face.erp.service.assets.domain.AssetsModel;
import com.ncut.face.erp.service.assets.domain.AssetsModifyVo;
import com.ncut.face.erp.service.assets.domain.AssetsOperate;
import com.ncut.face.erp.service.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
public class AssetsController {
    @Resource
    AssetsService assetsService;

    @RequestMapping("/addAssets")
    public Result addAssets(AssetsAddVo vo) {
        assetsService.addAssets(vo);
        return new Result<>(true);
    }

    @RequestMapping("/getAssetsList")
    public Result getAssetsList(AssetsOperate opt) {
        List list = assetsService.getAssetsList(opt);
        return new Result<>(list);
    }

    @RequestMapping("/modifyAssets")
    public Result modifyAssets(AssetsModifyVo vo) {
        assetsService.modifyAssets(vo);
        return new Result<>(true);
    }

    @RequestMapping("/getAssetsById")
    public Result getAssetsById(AssetsOperate opt) {
        AssetsModel model = assetsService.getAssetsById(opt);
        return new Result<>(model);
    }

    @RequestMapping("/deleteAssets")
    public Result deleteAssets(AssetsOperate opt) {
        assetsService.deleteAssets(opt);
        return new Result<>(true);
    }
}
