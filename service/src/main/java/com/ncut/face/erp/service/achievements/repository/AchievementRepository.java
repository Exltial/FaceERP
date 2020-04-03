package com.ncut.face.erp.service.achievements.repository;

import com.ncut.face.erp.service.achievements.domain.AchievementsModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AchievementRepository {
    void addAch(AchievementsModel model);

    List<AchievementsModel> getAchList(String tenantId);

    AchievementsModel getAchInfo(@Param("tenantId") String tenantId, @Param("id") Long id);

    void deleteAch(@Param("tenantId") String tenantId, @Param("id") Long id);

    void updateAch(AchievementsModel model);
}
