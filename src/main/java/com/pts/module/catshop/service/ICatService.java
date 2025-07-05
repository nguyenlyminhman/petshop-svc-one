package com.pts.module.catshop.service;

import com.pts.entity.catshop.CatEntity;
import com.pts.module.catshop.dto.CatRequestDto;
import com.pts.module.catshop.model.CatModel;

import java.util.List;

public interface ICatService {
    List<CatEntity> findAll() throws Exception;

    List<CatEntity> findCatByColor(String color);

    CatModel saveCat(CatRequestDto catRequestDto);

    int updateCat(CatRequestDto catRequestDto) throws Exception;
}
