package com.ncut.face.erp.service.assets.repository;

import com.ncut.face.erp.service.assets.domain.AssetsModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AssetsRepository {
    void addAssets(AssetsModel vo);

    List getAssetsList(@Param("tenantId") String tenantId);

    void modifyAssets(AssetsModel vo);

    AssetsModel getAssetsById(@Param("tenantId") String tenantId, @Param("id") Long id);

    void deleteAssets(@Param("tenantId") String tenantId, @Param("id") Long id);
}
