package com.ncut.face.erp.bootstrap.controller.api;

import com.ncut.face.erp.service.common.Result;
import com.ncut.face.erp.service.monitor.MonitorService;
import com.ncut.face.erp.service.monitor.domain.MonitorModel;
import com.ncut.face.erp.service.monitor.domain.MonitorVo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/monitor")
public class MonitorController {
    @Resource
    MonitorService monitorService;

    @RequestMapping("/addCam")
    @RequiresPermissions("ADMIN")
    @RequiresRoles("ADMIN")
    public Result addCam(@RequestBody MonitorVo vo) {
        monitorService.addCam(vo);
        return new Result<>(true);
    }

    @RequestMapping("/findCamList")
    @RequiresPermissions(value = {"ADMIN"}, logical = Logical.OR)
    @RequiresRoles(value = {"ADMIN"}, logical = Logical.OR)
    public Result getCamList() {
        List<MonitorModel> list = monitorService.getCamList();
        return new Result<>(list);
    }

    @RequestMapping("/getCamInfo")
    @RequiresPermissions(value = {"ADMIN"}, logical = Logical.OR)
    @RequiresRoles(value = {"ADMIN"}, logical = Logical.OR)
    public Result getCamInfo(@RequestBody MonitorVo opt) {
        MonitorModel model = monitorService.getCamById(opt);
        return new Result<>(model);
    }

    @RequestMapping("/deleteCam")
    @RequiresPermissions(value = "ADMIN")
    @RequiresRoles(value = "ADMIN")
    public Result deleteCam(@RequestBody MonitorVo opt) {
        monitorService.deleteCam(opt);
        return new Result<>(true);
    }
}
