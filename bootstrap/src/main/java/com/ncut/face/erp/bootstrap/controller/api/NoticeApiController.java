package com.ncut.face.erp.bootstrap.controller.api;

import com.ncut.face.erp.service.common.Result;
import com.ncut.face.erp.service.notice.NoticeService;
import com.ncut.face.erp.service.notice.domain.NoticeAddVo;
import com.ncut.face.erp.service.notice.domain.NoticeModel;
import com.ncut.face.erp.service.notice.domain.NoticeModifyVo;
import com.ncut.face.erp.service.notice.domain.NoticeOperate;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class NoticeApiController {
    @Resource
    NoticeService noticeService;

    @RequestMapping("/addNotice")
    public Result addNotice(NoticeAddVo vo) {
        noticeService.addNotice(vo);
        return new Result<>(true);
    }

    @RequestMapping("/getNoticeList")
    @RequiresPermissions("ADMIN")
    @RequiresRoles("ADMIN")
    public Result getNoticeList(NoticeOperate opt) {
        List list = noticeService.getNoticeList(opt);
        return new Result<>(list);
    }

    @RequestMapping("/getNotice")
    public Result getNotice(NoticeOperate opt) {
        NoticeModel model = noticeService.getNoticeById(opt);
        return new Result<>(model);
    }

    @RequestMapping("/deleteNotice")
    public Result deleteNotice(NoticeOperate opt) {
        noticeService.deleteNotice(opt);
        return new Result<>(true);
    }

    @RequestMapping("/modifyNotice")
    public Result modifyNotice(NoticeModifyVo vo) {
        noticeService.modifyNotice(vo);
        return new Result<>(true);
    }
}
