package com.pts.module.primary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UltramanDetailModel {
    private List<String> skills;
    private List<String> weapons;
    private String transformationTime; // thời gian biến hình tối đa
    private boolean canFly;
    private String motto; // câu nói hoặc triết lý
}
