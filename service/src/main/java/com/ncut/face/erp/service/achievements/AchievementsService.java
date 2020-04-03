package com.ncut.face.erp.service.achievements;

import com.ncut.face.erp.service.achievements.domain.AchievementOpt;
import com.ncut.face.erp.service.achievements.domain.AchievementVo;
import com.ncut.face.erp.service.achievements.domain.AchievementsModel;

import java.util.List;

public interface AchievementsService {
    void addAch(AchievementVo vo);

    List<AchievementsModel> getAchList();

    AchievementsModel getAchInfo(AchievementOpt opt);

    void deleteAch(AchievementOpt opt);

    void modifyAch(AchievementVo opt);
}
