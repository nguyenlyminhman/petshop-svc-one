package com.pts.module.primary.service;

import com.pts.entity.primary.UltramanEntity;
import com.pts.module.primary.dto.UltramanSearchDto;
import com.pts.module.primary.dto.UpdateInfoDto;
import com.pts.module.primary.dto.UpdatePlanetDto;
import com.pts.module.primary.dto.UpdateSkillDto;
import com.pts.module.primary.model.UltramanModel;

import java.util.List;

public interface IUltramanService {

    UltramanModel save(UltramanModel ultramanModel);

    List<UltramanEntity> findAll();

    List<UltramanEntity> searchBySpec(UltramanSearchDto searchDto);

    UltramanModel updateSkill(UpdateSkillDto updateSkillDto);

    UltramanModel updatePlanet(UpdatePlanetDto updatePlanetDto);

    UltramanModel updateInfo(UpdateInfoDto updateInfoDto);
}
