package com.pts.module.firstMysql.service;

import com.pts.module.firstMysql.dto.FirstMysqlRequestDto;
import com.pts.module.firstMysql.model.FirstMysqlModel;

public interface IFirstMysqlService {
    FirstMysqlModel save(FirstMysqlRequestDto dto);
}
