package com.pts.module.firstMysql.service.impl;

import com.pts.common.AbstractService;
import com.pts.common.ResponseObject;
import com.pts.entity.firstMysql.FirstMySqlEntity;
import com.pts.module.catshop.service.impl.CatServiceImpl;
import com.pts.module.firstMysql.dto.FirstMysqlRequestDto;
import com.pts.module.firstMysql.model.FirstMysqlModel;
import com.pts.module.firstMysql.repository.FirstMysqlRepository;
import com.pts.module.firstMysql.service.IFirstMysqlService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirstMysqlServiceImpl extends AbstractService implements IFirstMysqlService {

    private static final Logger log = LoggerFactory.getLogger(FirstMysqlServiceImpl.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FirstMysqlRepository firstMysqlRepository;

    @Override
    public FirstMysqlModel save(FirstMysqlRequestDto dto) {
        FirstMysqlModel mysqlModel = new FirstMysqlModel();
        try {
            FirstMySqlEntity entity = modelMapper.map(dto, FirstMySqlEntity.class);
            entity = firstMysqlRepository.save(entity);
            mysqlModel = modelMapper.map(entity, FirstMysqlModel.class);

            return mysqlModel;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("findCatByColor has an error {}", e.getMessage());
            return mysqlModel;
        }
    }
}
