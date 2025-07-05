package com.pts.module.primary.dto;

import com.pts.entity.primary.UltramanDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInfoDto {
    private long id;
    private String fullname;
    private String birthday;
    private String rank;
}
