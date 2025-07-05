package com.pts.module.catshop.repository;

import com.pts.entity.catshop.CatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface CatRepository extends JpaRepository<CatEntity, Long>, JpaSpecificationExecutor<CatEntity> {
}

/*
    JpaRepository<CatEntity, Long> : Cung cấp các method CRUD (findAll, findById, save, delete...) cho entity UltramanEntity, với kiểu ID là Long
    JpaSpecificationExecutor<UltramanEntity> : Cho phép viết truy vấn động (dynamic queries) bằng Specification API – tức là có thể build truy vấn phức tạp dựa trên điều kiện đầu vào.
 */



