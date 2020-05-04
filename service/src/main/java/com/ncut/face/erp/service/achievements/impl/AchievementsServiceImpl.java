package com.ncut.face.erp.service.achievements.impl;

import com.ncut.face.erp.service.achievements.AchievementsService;
import com.ncut.face.erp.service.achievements.domain.AchievementOpt;
import com.ncut.face.erp.service.achievements.domain.AchievementTypeEnum;
import com.ncut.face.erp.service.achievements.domain.AchievementVo;
import com.ncut.face.erp.service.achievements.domain.AchievementsModel;
import com.ncut.face.erp.service.achievements.repository.AchievementRepository;
import com.ncut.face.erp.service.common.utils.DateUtil;
import com.ncut.face.erp.service.user.domain.UserInfoModel;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AchievementsServiceImpl implements AchievementsService {
    @Resource
    AchievementRepository repository;

    @Override
    public void addAch(AchievementVo vo) {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        AchievementsModel model = new AchievementsModel();
        model.setTenantId(user.getTenantId());
        model.setAchName(vo.getAchName());
        model.setFileId(vo.getFileId());
        model.setAchType(vo.getAchType());
        model.setAchAuthor(vo.getAchAuthor());
        model.setCreator(user.getUserName());
        repository.addAch(model);
    }

    @Override
    public List<AchievementsModel> getAchList() {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        List<AchievementsModel> achList = repository.getAchList(user.getTenantId());
        achList.forEach(item -> {
            item.setAchTypeDesc(AchievementTypeEnum.of(item.getAchType()));
            item.setCreateTimeDesc(DateUtil.format(item.getCreateTime(), "yyyy-MM-dd"));
        });
        return achList;
    }

    @Override
    public AchievementsModel getAchInfo(AchievementOpt opt) {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        return repository.getAchInfo(user.getTenantId(), opt.getId());
    }

    @Override
    public void deleteAch(AchievementOpt opt) {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        repository.deleteAch(user.getTenantId(), opt.getId());
    }

    @Override
    public void modifyAch(AchievementVo vo) {
        UserInfoModel user = (UserInfoModel) SecurityUtils.getSubject().getSession().getAttribute("user");
        AchievementsModel model = new AchievementsModel();
        model.setId(vo.getId());
        model.setTenantId(user.getTenantId());
        model.setAchType(vo.getAchType());
        model.setAchName(vo.getAchName());
        model.setFileId(vo.getFileId());
        model.setAchAuthor(vo.getAchAuthor());
        model.setModifier(user.getUserName());
        repository.updateAch(model);
    }
}
