package com.pts.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertUtils {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static <T> T parseJsonStrToPojo(String json, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Cannot parse json", e);
        }
    }

    public static String parsePojoToJsonStr(Object clazz) {
        try {
            return new ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not convert object to JSON", e);
        }
    }

    public static <S, T> List<T> mapListToList(List<S> sourceList, Class<T> targetClass) {
        return sourceList.stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
