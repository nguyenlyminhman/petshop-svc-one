package com.pts.module.catshop.service.impl;

import com.pts.common.AbstractService;
import com.pts.module.catshop.repository.CatRepository;
import com.pts.entity.catshop.CatEntity;
import com.pts.module.catshop.dto.CatRequestDto;
import com.pts.module.catshop.model.CatModel;
import com.pts.module.catshop.service.ICatService;
import com.pts.util.*;
import jakarta.persistence.Query;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CatServiceImpl extends AbstractService implements ICatService {

    private static final Logger log = LoggerFactory.getLogger(CatServiceImpl.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CatRepository catRepository;

    @Override
    public List<CatEntity> findAll() throws Exception {
        List<CatEntity> rs = null;
        try {
            rs = catRepository.findAll();
        } catch (Exception e) {
            log.error("findAll has an error {}", e.getMessage());
            throw new Exception("Error occur: " + e.getMessage());
        }

        return rs;
    }

    @Override
    public List<CatEntity> findCatByColor(String color) {
        List<CatEntity> cats = null;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        try {
            sql.append("SELECT ");
            sql.append("c.id, ");
            sql.append("c.color, ");
            sql.append("CAST(c.attributes AS TEXT), "); // c.attributes: có kểu dữ liệu là JSON nên Cast về Text
            sql.append("c.created_at createdAt, ");
            sql.append("c.created_by createdBy ");
            sql.append("FROM PUBLIC.CAT c WHERE 1 = 1 ");

            // Cách 01: kiểm tra & set từng param
            // => Dùng cho một vài param

            // Begin: Cach-01
            // if (!color.isEmpty() || !"".equals(color) ) {
            //    sql.append("AND c.color = :color) ");
            // }
            // Query query = catEntityManager.createNativeQuery(sql.toString());
            // if (!color.isEmpty() || !"".equals(color) ) {
            //    query.setParameter("color", color);
            // }
            // End: Cach-01


            //  Cách 02: push param vào hashmap tại thời điêm check null or empty
            //  => Dùng cho ít/nhiều params
            //  => Cách này khái quát hơn cách 01
            // Begin: Cach-02
            if (!color.isEmpty() || !"".equals(color)) {
                sql.append("AND c.color = :color ");
                params.put("color", color);
            }
            Query query = catEntityManager.createNativeQuery(sql.toString());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            // End: Cach-02

            List<Object[]> resultList = query.getResultList();

            String[] columns = {"id", "color", "attributes", "createdAt", "createdBy"};
            List<CatModel> cat = ResultSetUtils.mapListToPojoList(resultList, columns, CatModel.class);

            cats = ConvertUtils.mapListToList(cat, CatEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("findCatByColor has an error {}", e.getMessage());

            return cats;
        }

        return cats;
    }

    @Override
    public CatModel saveCat(CatRequestDto catRequestDto) {
        CatEntity catEntity = new CatEntity();
        CatModel catModel = new CatModel();
        try {
            catEntity = modelMapper.map(catRequestDto, CatEntity.class);
            catEntity = catRepository.save(catEntity);

            catModel = modelMapper.map(catEntity, CatModel.class);
        } catch (Exception e) {
            System.out.println("saveCat has an error: " + e.getMessage());
        }
        return catModel;
    }

    @Override
//    @Transactional("catTransactionManager")
    public int updateCat(CatRequestDto catInfo) throws Exception {
        int rs = 0;
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        try {
            sql.append("UPDATE PUBLIC.CAT SET ");

            if (!catInfo.getFullname().isEmpty() || !"".equals(catInfo.getFullname())) {
                sql.append("fullname = :fullname, ");
                params.put("fullname", catInfo.getFullname());
            }

            if (!catInfo.getBirthday().isEmpty() || !"".equals(catInfo.getBirthday())) {
                sql.append("birthday = :birthday, ");
                params.put("birthday", catInfo.getBirthday());
            }

            if (!catInfo.getColor().isEmpty() || !"".equals(catInfo.getColor())) {
                sql.append("color = :color, ");
                params.put("color", catInfo.getColor());
            }

            // attributes: có kiểu dữ liệu là JSON trong DB
            if (catInfo.getAttributes() != null) {
                sql.append("attributes = cast(:attributes as json), ");
                params.put("attributes", ConvertUtils.parsePojoToJsonStr(catInfo.getAttributes()));
            }

            // Xoá dấu , cuối cùng nếu có
            int lastComma = sql.lastIndexOf(",");
            if (lastComma != -1) {
                sql.deleteCharAt(lastComma);
            }

            sql.append(" WHERE id = :id ");
            params.put("id", catInfo.getId());

            Query query = primaryEntityManager.createNativeQuery(sql.toString());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

            rs = query.executeUpdate();
        } catch (Exception e) {
            log.error("updateCat has an error {}", e.getMessage());
            throw new Exception("updateCat has an error " + e.getMessage());
        }

        return rs;
    }

}
