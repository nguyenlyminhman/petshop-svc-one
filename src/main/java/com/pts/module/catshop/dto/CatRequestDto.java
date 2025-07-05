package com.pts.module.catshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CatRequestDto {
    private long id;
    private String fullname;
    private String birthday;
    private String color;
    private CatAttributeDto attributes;
}
