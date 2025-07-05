package com.pts.module.catshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CatAttributeModel {
    private boolean vaccinated;
    private String behavior;
    private List<String> favoriteFoods;
}
