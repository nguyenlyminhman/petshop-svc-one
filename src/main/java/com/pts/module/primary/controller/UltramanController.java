package com.pts.module.primary.controller;

import com.pts.common.ResponseObject;
import com.pts.module.primary.dto.UltramanSearchDto;
import com.pts.module.primary.dto.UpdateInfoDto;
import com.pts.module.primary.dto.UpdatePlanetDto;
import com.pts.module.primary.dto.UpdateSkillDto;
import com.pts.module.primary.model.UltramanModel;
import com.pts.module.primary.service.IUltramanService;
import com.pts.entity.primary.UltramanEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.v1}/ultraman")
public class UltramanController {
    @Autowired
    private IUltramanService iUltramanService;

    @GetMapping("/all")
    public ResponseObject<List<UltramanEntity>> findAll() {
        try {
            List<UltramanEntity> rs = iUltramanService.findAll();
            return ResponseObject.ok(rs) ;
        } catch (Exception e) {
            return ResponseObject.error(400, "Error while fetching data");
        }
    }

    @RequestMapping(value = "/search", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public ResponseObject<List<UltramanEntity>> findAllSpecs(@RequestBody UltramanSearchDto searchDto) {
        try {
            List<UltramanEntity> rs = iUltramanService.searchBySpec(searchDto);
            return ResponseObject.ok(rs) ;
        } catch (Exception e) {
            return ResponseObject.error(400, "Error while fetching data");
        }
    }

    @RequestMapping(value = "/save", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public ResponseObject<UltramanModel> save(@RequestBody UltramanModel ultramanModel) {
        try {
            UltramanModel rs = iUltramanService.save(ultramanModel);
            return ResponseObject.ok(rs) ;
        } catch (Exception e) {
            return ResponseObject.error(400, "Error while fetching data");
        }
    }

    @RequestMapping(value = "/update-skill", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public ResponseObject<UltramanModel> updateSkill(@RequestBody UpdateSkillDto updateSkillDto) {
        try {
            UltramanModel rs = iUltramanService.updateSkill(updateSkillDto);
            return ResponseObject.ok(rs) ;
        } catch (Exception e) {
            return ResponseObject.error(400, "Error while updating data");
        }
    }

    @RequestMapping(value = "/update-planet", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public ResponseObject<UltramanModel> updatePlanet(@RequestBody UpdatePlanetDto updatePlanetDto) {
        try {
            UltramanModel rs = iUltramanService.updatePlanet(updatePlanetDto);
            return ResponseObject.ok(rs) ;
        } catch (Exception e) {
            return ResponseObject.error(400, "Error while updating data");
        }
    }

    @RequestMapping(value = "/update-info", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public ResponseObject<UltramanModel> updateInfo(@RequestBody UpdateInfoDto updateInfoDto) {
        try {
            UltramanModel rs = iUltramanService.updateInfo(updateInfoDto);
            return ResponseObject.ok(rs) ;
        } catch (Exception e) {
            return ResponseObject.error(400, "Error while updating data");
        }
    }
}
