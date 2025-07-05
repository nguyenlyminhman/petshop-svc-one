package com.pts.module.primary.dto;

import com.pts.entity.primary.UltramanDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UltramanRequestDto {
    private String name;
    private String birthday;
    private String color;
    private UltramanDetails details;
}
