package com.ncut.face.erp.bootstrap.controller.api;

import com.ncut.face.erp.service.assets.AssetsService;
import com.ncut.face.erp.service.assets.domain.AssetsAddVo;
import com.ncut.face.erp.service.assets.domain.AssetsModel;
import com.ncut.face.erp.service.assets.domain.AssetsModifyVo;
import com.ncut.face.erp.service.assets.domain.AssetsOperate;
import com.ncut.face.erp.service.common.Result;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/assets")
public class AssetsController {
    @Resource
    AssetsService assetsService;

    @RequestMapping("/addAssets")
    @RequiresPermissions(value = {"ADMIN", "USER"}, logical = Logical.OR)
    @RequiresRoles(value = {"ADMIN", "USER"}, logical = Logical.OR)
    public Result addAssets(@RequestBody AssetsAddVo vo) {
        assetsService.addAssets(vo);
        return new Result<>(true);
    }

    @RequestMapping("/getAssetsList")
    @RequiresPermissions(value = {"ADMIN", "USER"}, logical = Logical.OR)
    @RequiresRoles(value = {"ADMIN", "USER"}, logical = Logical.OR)
    public Result getAssetsList() {
        List list = assetsService.getAssetsList();
        return new Result<>(list);
    }

    @RequestMapping("/modifyAssets")
    @RequiresPermissions(value = {"ADMIN", "USER"}, logical = Logical.OR)
    @RequiresRoles(value = {"ADMIN", "USER"}, logical = Logical.OR)
    public Result modifyAssets(@RequestBody AssetsModifyVo vo) {
        assetsService.modifyAssets(vo);
        return new Result<>(true);
    }

    @RequestMapping("/getAssetsById")
    @RequiresPermissions(value = {"ADMIN", "USER"}, logical = Logical.OR)
    @RequiresRoles(value = {"ADMIN", "USER"}, logical = Logical.OR)
    public Result getAssetsById(@RequestBody AssetsOperate opt) {
        AssetsModel model = assetsService.getAssetsById(opt);
        return new Result<>(model);
    }

    @RequestMapping("/deleteAssets")
    @RequiresPermissions("ADMIN")
    @RequiresRoles("ADMIN")
    public Result deleteAssets(@RequestBody AssetsOperate opt) {
        assetsService.deleteAssets(opt);
        return new Result<>(true);
    }
}
