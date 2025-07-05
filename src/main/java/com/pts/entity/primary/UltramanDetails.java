package com.pts.entity.primary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UltramanDetails {
    private List<String> skills;
    private List<String> weapons;
    private String transformationTime;
    private boolean canFly;
    private String motto;
}
