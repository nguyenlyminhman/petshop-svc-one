package com.pts.adm.config;

import com.pts.entity.catshop.CatAttributes;
import com.pts.module.catshop.dto.CatAttributeDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

//        modelMapper.typeMap(CatAttributeDto.class, CatAttributes.class);
//        modelMapper.typeMap(CatAttributes.class, CatAttributeDto.class);


        return modelMapper;
    }
}
