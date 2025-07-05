package com.pts.module.primary.repository;

import com.pts.entity.primary.UltramanEntity;
import com.pts.module.primary.dto.UpdateInfoDto;
import com.pts.module.primary.dto.UpdatePlanetDto;
import com.pts.module.primary.dto.UpdateSkillDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UltramanRepository extends JpaRepository<UltramanEntity, Long>, JpaSpecificationExecutor<UltramanEntity> {
    UltramanEntity findOneById(long id);

    // Tên table là tên class của Entity, column là các props trong class entity đó
    @Modifying
    @Transactional
    @Query(value = "UPDATE UltramanEntity u SET " +
            "u.fullname = :#{#dto.fullname}, " +
            "u.birthday = :#{#dto.birthday}, " +
            "u.rank = :#{#dto.rank} " +
            "WHERE u.id = :#{#dto.id}")
    int updateUltramanInfo(@Param("dto") UpdateInfoDto dto);


    // Không thể dùng Entity Class để viết câu Update có liên quan đến các hàm build-in của Postgres
    // Ví du: @Query(value = "UPDATE UltramanEntity u SET u.attributes = jsonb_set(u.attributes, '{skills}', CAST(:#{#dto.skills} as jsonb )) WHERE u.id = :#{#dto.id}")
    // Khi dùng JPQL (HQL) để gọi hàm jsonb_set(...), CAST(...) =>  Hibernate không hiểu hàm build-in PostgreSQL

    // Đổi tên EntityClass thành Tên TABLE trong db, các props là các column.
    // PostgreSQL không cho alias sau bảng ở phần UPDATE
    @Modifying
    @Transactional
    @Query(value = "UPDATE public.ultraman SET " +
            "details = jsonb_set(details, '{skills}', CAST(:skillsSet as jsonb )) " +
            "WHERE id = :#{#dto.id}", nativeQuery = true)
    int updateUltramanSkills(@Param("dto") UpdateSkillDto dto, @Param("skillsSet") String skillsSet);
}