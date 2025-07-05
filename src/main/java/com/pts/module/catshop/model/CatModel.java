package com.pts.module.catshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CatModel {
    private Long id;
    private String fullname;
    private String birthday;
    private String color;
    private CatAttributeModel attributes;
    private Date createdAt;
    private String createdBy;
}
