package com.pts.module.primary.model;

import com.pts.entity.primary.UltramanDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UltramanModel {
    private Long id;
    private String fullname;
    private String birthday;
    private String rank;
    private List<String> planet;
    private UltramanDetails details;
    private Date createdAt;
    private String createdBy;
}
