package com.pts.module.catshop.controller;

import com.pts.common.ResponseObject;
import com.pts.module.catshop.model.CatModel;
import com.pts.module.catshop.service.ICatService;
import com.pts.entity.catshop.CatEntity;
import com.pts.module.catshop.dto.CatRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.v1}/cat")
public class CatController {

    @Autowired
    private ICatService iCatService;

    @PostMapping(value = "/save", produces = "application/json;charset=utf-8")
    public ResponseObject<CatModel> save(@RequestBody CatRequestDto catRequestDto) {
        try {
            CatModel catModel = iCatService.saveCat(catRequestDto);
            return ResponseObject.ok(catModel);
        } catch (Exception e) {
            return ResponseObject.error(400, "Error while saving cat");
        }
    }

    @RequestMapping(value = "/all", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public ResponseObject<List<CatEntity>> findAll() {
         try {
            List<CatEntity> catEntities = iCatService.findAll();
            return ResponseObject.ok(catEntities);
        } catch (Exception e) {
             return ResponseObject.error(400, "Error while fetching cat");
        }
    }


    @RequestMapping(value = "/find-by/{color}", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public ResponseObject<List<CatEntity>> findCatByColor_Native(@PathVariable("color") String color) {
        try {
            List<CatEntity> catEntities = iCatService.findCatByColor(color);
            return ResponseObject.ok(catEntities);
        } catch (Exception e) {
            return ResponseObject.error(400, "Error while fetching cat");
        }
    }

    @RequestMapping(value = "/update-cat", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public ResponseObject<Object> updateCatNative(@RequestBody CatRequestDto catRequestDto) {
        try {
            int rs = iCatService.updateCat(catRequestDto);
            return ResponseObject.ok(rs);
        } catch (Exception e) {
            return ResponseObject.error(400, "Error while updating cat");
        }
    }
}
