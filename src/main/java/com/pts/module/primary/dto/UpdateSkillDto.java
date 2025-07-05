package com.pts.module.primary.dto;

import com.pts.entity.primary.UltramanDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSkillDto {
    private long id;
    List<String> skills;
}
