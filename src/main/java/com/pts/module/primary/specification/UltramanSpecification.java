package com.pts.module.primary.specification;

import com.pts.entity.primary.UltramanEntity;
import com.pts.module.primary.dto.UltramanSearchDto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UltramanSpecification {
    public static Specification<UltramanEntity> byUltramanSearchDto(UltramanSearchDto request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getFullname() != null && !"".equals(request.getFullname())) {
                predicates.add(cb.like(cb.lower(root.get("fullname")), "%" + request.getFullname().toLowerCase() + "%"));
            }

            if (request.getBirthday() != null && !"".equals(request.getBirthday())) {
                predicates.add(cb.equal(root.get("birthday"), request.getBirthday()));
            }

            if (request.getRank() != null && !"".equals(request.getRank())) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("rank"), request.getRank()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
