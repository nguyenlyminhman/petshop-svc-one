package com.pts.module.firstMysql.controller;

import com.pts.common.ResponseObject;
import com.pts.module.catshop.dto.CatRequestDto;
import com.pts.module.firstMysql.dto.FirstMysqlRequestDto;
import com.pts.module.firstMysql.model.FirstMysqlModel;
import com.pts.module.firstMysql.service.IFirstMysqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.v1}/first-mysql")
public class FirstMysqlController {

    @Autowired
    private IFirstMysqlService firstMysqlService;

    @PostMapping(value = "/save", produces = "application/json;charset=utf-8")
    public ResponseObject<FirstMysqlModel> save(@RequestBody FirstMysqlRequestDto dto) {
        try {
            FirstMysqlModel mysqlModel = firstMysqlService.save(dto);
            return ResponseObject.ok(mysqlModel);
        } catch (Exception e) {
            return ResponseObject.error(400, "Error while inserting into mysql");
        }
    }
}
