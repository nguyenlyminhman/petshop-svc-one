package com.pts.module.firstMysql.repository;

import com.pts.entity.firstMysql.FirstMySqlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FirstMysqlRepository extends JpaRepository<FirstMySqlEntity, Long>, JpaSpecificationExecutor<FirstMySqlEntity> {
}
