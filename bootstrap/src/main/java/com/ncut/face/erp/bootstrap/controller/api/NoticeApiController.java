package com.ncut.face.erp.bootstrap.controller.api;

import com.ncut.face.erp.service.common.Result;
import com.ncut.face.erp.service.common.utils.DateUtil;
import com.ncut.face.erp.service.notice.NoticeService;
import com.ncut.face.erp.service.notice.domain.NoticeModel;
import com.ncut.face.erp.service.notice.domain.NoticeOperate;
import com.ncut.face.erp.service.notice.domain.NoticeVo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeApiController {
    @Resource
    NoticeService noticeService;

    @RequestMapping("/addNotice")
    @RequiresPermissions("ADMIN")
    @RequiresRoles("ADMIN")
    public Result addNotice(@RequestBody NoticeVo vo) {
        noticeService.addNotice(vo);
        return new Result<>(true);
    }

    @RequestMapping("/getNoticeList")
    @RequiresPermissions(value = {"ADMIN", "USER"}, logical = Logical.OR)
    @RequiresRoles(value = {"ADMIN", "USER"}, logical = Logical.OR)
    public Result getNoticeList() {
        List<NoticeModel> list = noticeService.getNoticeList();
        list.forEach(item -> item.setCreateTimeDesc(DateUtil.format(item.getCreateTime(), "yyyy年MM月dd日 HH:mm")));
        return new Result<>(list);
    }

    @RequestMapping("/getNotice")
    @RequiresPermissions(value = {"ADMIN", "USER"}, logical = Logical.OR)
    @RequiresRoles(value = {"ADMIN", "USER"}, logical = Logical.OR)
    public Result getNotice(@RequestBody NoticeOperate opt) {
        NoticeModel model = noticeService.getNoticeById(opt);
        return new Result<>(model);
    }

    @RequestMapping("/deleteNotice")
    @RequiresPermissions(value = "ADMIN")
    @RequiresRoles(value = "ADMIN")
    public Result deleteNotice(@RequestBody NoticeOperate opt) {
        noticeService.deleteNotice(opt);
        return new Result<>(true);
    }

    @RequestMapping("/modifyNotice")
    @RequiresPermissions(value = "ADMIN")
    @RequiresRoles(value = "ADMIN")
    public Result modifyNotice(@RequestBody NoticeVo vo) {
        noticeService.modifyNotice(vo);
        return new Result<>(true);
    }
}
