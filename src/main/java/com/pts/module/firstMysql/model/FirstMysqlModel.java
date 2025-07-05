package com.pts.module.firstMysql.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FirstMysqlModel {
    private Long id;
    private String fullname;
    private String birthday;
    private String color;
}
