package com.pts.module.primary.service.impl;

import com.pts.common.AbstractService;
import com.pts.module.primary.dto.UltramanSearchDto;
import com.pts.module.primary.dto.UpdateInfoDto;
import com.pts.module.primary.dto.UpdatePlanetDto;
import com.pts.module.primary.dto.UpdateSkillDto;
import com.pts.module.primary.model.UltramanModel;
import com.pts.module.primary.repository.UltramanRepository;
import com.pts.entity.primary.UltramanEntity;
import com.pts.module.primary.service.IUltramanService;
import com.pts.module.primary.specification.UltramanSpecification;
import com.pts.util.ConvertUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UltramanServiceImpl extends AbstractService implements IUltramanService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UltramanRepository ultramanRepository;

    @Override
    public UltramanModel save(UltramanModel ultramanModel) {
        try {
            UltramanEntity entity = modelMapper.map(ultramanModel, UltramanEntity.class);
            entity.setCreatedAt(LocalDateTime.now());

            entity = ultramanRepository.save(entity);

            ultramanModel.setId(entity.getId());
            return ultramanModel;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while saving data");
        }
    }

    @Override
    public List<UltramanEntity> findAll() {
        try {
            List<UltramanEntity> rs = ultramanRepository.findAll();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while fetching data");
        }
    }

    @Override
    public List<UltramanEntity> searchBySpec(UltramanSearchDto searchDto) {
        try {
            Specification<UltramanEntity> spec = UltramanSpecification.byUltramanSearchDto(searchDto);
            return ultramanRepository.findAll(spec);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while fetching data");
        }
    }

    @Override
    public UltramanModel updateSkill(UpdateSkillDto dto) {
        try {
            String skillsSet = ConvertUtils.parsePojoToJsonStr(dto.getSkills());
            int rs = ultramanRepository.updateUltramanSkills(dto, skillsSet);
            if (rs == 0 ) throw new RuntimeException("Cannot update skill");

            UltramanEntity entity = ultramanRepository.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Ultraman not found with id: " + dto.getId()));
            UltramanModel ultramanModel = modelMapper.map(entity, UltramanModel.class);

            return ultramanModel;
        } catch (Exception e) {
            throw new RuntimeException("updateSkill has an error");
        }
    }

    @Override
    @Transactional("primaryTransactionManager")
    public UltramanModel updatePlanet(UpdatePlanetDto dto) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        try {
            sql.append("UPDATE ultraman SET ");

            if (!dto.getPlanet().isEmpty() || dto.getPlanet().size() > 0) {
                String planetSet = ConvertUtils.parsePojoToJsonStr(dto.getPlanet());
                sql.append("planet = cast(:planet as json), ");
                params.put("planet", planetSet);
            }

            // Xoá dấu , cuối cùng nếu có
            int lastComma = sql.lastIndexOf(",");
            if (lastComma != -1) {
                sql.deleteCharAt(lastComma);
            }

            sql.append(" WHERE id = :id ");
            params.put("id", dto.getId());

            Query query = primaryEntityManager.createNativeQuery(sql.toString());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

            int rs = query.executeUpdate();
            if (rs == 0 ) throw new RuntimeException("Cannot update skill");

            UltramanEntity entity = ultramanRepository.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Ultraman not found with id: " + dto.getId()));
            UltramanModel ultramanModel = modelMapper.map(entity, UltramanModel.class);

            return ultramanModel;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new RuntimeException("updatePlanet has an error " + e.getMessage());
        }
    }

    @Override
    public UltramanModel updateInfo(UpdateInfoDto dto) {

        try {
            // Cách 01:
            // Tìm item đã tồn tại rồi set lại các field cần cập nhật. Sau đó gọi save() để cập nhật lại.
            // Save(entity) trong JPA vừa thêm mới (nếu id không tồn tại ) vừa cập nhật (nếu id tồn tại)

            // findOneById(Long id) là method tự định nghĩa theo công thức findOneByXxx(). Trong đó Xxx là các properties trong Entity.
            // Khi đó Spring Data sẽ tự tạo query SELECT * FROM ultraman WHERE id = ? theo tên method.
            // Lưu ý: Kiểm tra null để tránh nullPointerException khi item không tồn tại. Hoặc có thể dùng findById()

            // UltramanEntity entity = ultramanRepository.findById(dto.getId())
            //    .orElseThrow(() -> new EntityNotFoundException("Ultraman not found with id: " + dto.getId()));
            // Begin: Cach-01
            /*
            UltramanEntity entity = ultramanRepository.findOneById(dto.getId());
            if (entity == null) return null;

            entity.setFullname(dto.getFullname());
            entity.setBirthday(dto.getBirthday());
            entity.setRank(dto.getRank());

            ultramanRepository.save(entity);
            UltramanModel ultramanModel = modelMapper.map(entity, UltramanModel.class);
            return ultramanModel;
            */
            // End: Cach-01

            // Cách 02:
            // Dùng JQPL để viết câu sql update
            // Lưu ý: Tên table là tên class của Entity, column là các props trong class entity đó
            // Begin: Cach-02
            int rs = ultramanRepository.updateUltramanInfo(dto);
            if (rs == 0 ) throw new RuntimeException("updateInfo has an error");

            UltramanEntity entity = ultramanRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Ultraman not found with id: " + dto.getId()));
            UltramanModel ultramanModel = modelMapper.map(entity, UltramanModel.class);

            return ultramanModel;
            // End: Cach-02
        } catch (Exception e) {
            throw new RuntimeException("updateInfo has an error");
        }
    }
}
