package com.pts.module.catshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CatAttributeDto {
    private boolean vaccinated;
    private String behavior;
    private List<String> favoriteFoods;
}
